package com.bpaas.doc.framework.web.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bpaas.doc.framework.base.command.NotProguard;
import com.bpaas.doc.framework.base.common.BaseConstant;
import com.bpaas.doc.framework.base.util.CheckEmptyUtil;
import com.bpaas.doc.framework.base.util.StringUtil;
import com.bpaas.doc.framework.web.pojo.Page;
import com.bpaas.doc.framework.web.util.PageUtil;

/**
 * 产生mybatis generator相匹配的查询条件
 * 
 * @author huyang
 */
@NotProguard
public class MybatisExample {

	private static final Logger logger = LoggerFactory
			.getLogger(MybatisExample.class);

	private volatile static MybatisExample instance;

	private MybatisExample() {
	}

	public static MybatisExample getInstance() {
		if (instance == null) {
			synchronized (MybatisExample.class) {
				if (instance == null) {
					instance = new MybatisExample();
				}
			}
		}
		return instance;
	}

    /**
     * 构建example集合
     * 
     * @param conditions
     * @param example
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private Map<String, Object> getExampleMap(List<Condition> conditions, Object example)
            throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Map<String, Object> exampleMap = new HashMap<String, Object>();
        if (!CheckEmptyUtil.isEmpty(conditions)) {
            for (Condition condition : conditions) {
                if (!CheckEmptyUtil.isEmpty(condition.getExample())
                        && !exampleMap.containsKey(condition.getExample())) {
                    Method getExampleMethod = example.getClass().getMethod(
                            "get" + condition.getExample());
                    Object chrildExample = getExampleMethod.invoke(example);
                    exampleMap.put(condition.getExample(), chrildExample);
                }
            }
        }
        return exampleMap;
    }

    private void buildCondition(Object example, List<Condition> conditions,
            Map<String, Object> exampleMap) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, SecurityException,
            IllegalArgumentException, InvocationTargetException {
        if (!CheckEmptyUtil.isEmpty(exampleMap) && !CheckEmptyUtil.isEmpty(conditions)) {
            // 对查询条件，按照example进行分组
            Map<String, List<Condition>> childMap = new HashMap<String, List<Condition>>();
            for (String key : exampleMap.keySet()) {
                List<Condition> childConditions = new ArrayList<Condition>();
                for (Condition condition : conditions) {
                    if (key.equals(condition.getExample())) {
                        childConditions.add(condition);
                    }
                }
                if (!CheckEmptyUtil.isEmpty(childConditions)) {
                    childMap.put(key, childConditions);
                }
            }
            //
            if (!CheckEmptyUtil.isEmpty(childMap)) {
                for (String key : childMap.keySet()) {
                    List<Condition> childList = childMap.get(key);
                    buildCondition(exampleMap.get(key), childList);
                }
            }
        }
    }

    private void buildCondition(Object example, List<Condition> conditions)
            throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        // 单个example查询条件
        Method createCriteria = example.getClass().getMethod("createCriteria");
        Object criteria = createCriteria.invoke(example);
        for (Condition condition : conditions) {
            buildCondition(condition, criteria);
        }
    }

	public Object buildExampleByCondition(List<Condition> conditions,
			String exampleName) {
		Object example = null;
		try {
			Class<?> exampleClass = Class.forName(exampleName);
			example = exampleClass.newInstance();
			if (!CheckEmptyUtil.isEmpty(conditions)) {
                Map<String, Object> exampleMap = getExampleMap(conditions, example);
                //
                if (CheckEmptyUtil.isEmpty(exampleMap)) {
                    // 单个example查询条件
                    buildCondition(example, conditions);
                } else {
                    // 多个example查询条件
                    buildCondition(example, conditions, exampleMap);
                }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return example;
	}

    public Object buildExampleByCondition(List<Condition> conditions,PageUtil page,
            String exampleName) {
        Object example = null;
        if (conditions == null || page == null) {
            return null;
        }
        try {
            example = buildExampleByCondition(conditions, exampleName);
            if (CheckEmptyUtil.isNotEmpty(page.getSort())
                    && CheckEmptyUtil.isNotEmpty(page.getOrder())) {
                // 构建排序条件
                StringBuilder orderByClause = new StringBuilder("");

                String sort = null;
                if(page.getSort().startsWith("CONVERT")){
                	sort = page.getSort().substring(page.getSort().indexOf('(')+1, page.getSort().indexOf(" USING gbk"));
                }else{
                	sort = page.getSort() ;
                }
                int periodsIndex = sort.indexOf(BaseConstant.Separate.PERIODS) ;
                if (periodsIndex>= 0) {
                    String childExampleStr = sort.substring(0, periodsIndex);
                    //orderByClause.append(childExampleStr);
                    //String sortStr = new String(sort.substring(periodsIndex, sort.length()));
                    //orderByClause.append(StringUtil.camelToUnderline(sortStr));
                    Method method = example.getClass().getMethod("get"+StringUtil.toUpperCaseFirstOne(childExampleStr)+"Example");
                    Object childExample = method.invoke(example);

                    orderByClause.append(page.getSort()).append(" ").append(page.getOrder());
                    Method method2 = childExample.getClass().getMethod("setOrderByClause", String.class);
                    method2.invoke(childExample, orderByClause.toString());
                } else {
                    orderByClause.append(page.getSort()).append(" ").append(page.getOrder());
                    Method method = example.getClass().getMethod("setOrderByClause", String.class);
                    method.invoke(example, orderByClause.toString());
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return example;
    }

	public Object buildExampleByCondition(List<Condition> conditions,
			DataTableQuery dtq, String exampleName) {
		Object example = null;
		if (conditions == null || dtq == null) {
			return null;
		}
		try {
			example = buildExampleByCondition(conditions, exampleName);
			if (CheckEmptyUtil.isNotEmpty(dtq.getOrderBy())
					&& CheckEmptyUtil.isNotEmpty(dtq.getOrderParam())) {
				// 构建排序条件
				StringBuilder orderByClause = new StringBuilder("");
				orderByClause.append(dtq.getOrderParam()).append(" ")
						.append(dtq.getOrderBy());

				Method method = example.getClass().getMethod(
						"setOrderByClause", String.class);
				method.invoke(example, orderByClause.toString());
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return example;
	}

	private void buildCondition(Condition condition, Object criteria)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Assert.hasText(condition.getPropertyName(), "propertyName不能为空");
		StringBuilder methodName = new StringBuilder("");
		Method method = null;
		Class<?> propertyClass = condition.getPropertyClass();
		switch (condition.getMatchType()) {
		case NEQ:
			methodName
					.append("and")
					.append(StringUtil.toUpperCaseFirstOne(condition
							.getPropertyName())).append("NotEqualTo");
			method = criteria.getClass().getMethod(methodName.toString(),
					propertyClass);
			method.invoke(criteria, condition.getMatchValue());
			break;
		case EQ:
			methodName
					.append("and")
					.append(StringUtil.toUpperCaseFirstOne(condition
							.getPropertyName())).append("EqualTo");
			method = criteria.getClass().getMethod(methodName.toString(),
					propertyClass);
			method.invoke(criteria, condition.getMatchValue());
			break;
		case LIKE:
			methodName
					.append("and")
					.append(StringUtil.toUpperCaseFirstOne(condition
							.getPropertyName())).append("Like");
			method = criteria.getClass().getMethod(methodName.toString(),
					propertyClass);
			//在like是matchValue必须是string
			StringBuilder matchValue = new StringBuilder("");
			matchValue.append("%").append(condition.getMatchValue())
					.append("%");
			method.invoke(criteria, matchValue.toString());
			break;
		case LE:
			methodName
					.append("and")
					.append(StringUtil.toUpperCaseFirstOne(condition
							.getPropertyName())).append("LessThanOrEqualTo");
			method = criteria.getClass().getMethod(methodName.toString(),
					propertyClass);
			method.invoke(criteria, condition.getMatchValue());
			break;
		case LT:
			methodName
					.append("and")
					.append(StringUtil.toUpperCaseFirstOne(condition
							.getPropertyName())).append("LessThan");
			method = criteria.getClass().getMethod(methodName.toString(),
					propertyClass);
			method.invoke(criteria, condition.getMatchValue());
			break;
		case GE:
			methodName
					.append("and")
					.append(StringUtil.toUpperCaseFirstOne(condition
							.getPropertyName())).append("GreaterThanOrEqualTo");
			method = criteria.getClass().getMethod(methodName.toString(),
					propertyClass);
			method.invoke(criteria, condition.getMatchValue());
			break;
		case GT:
			methodName
					.append("and")
					.append(StringUtil.toUpperCaseFirstOne(condition
							.getPropertyName())).append("GreaterThan");
			method = criteria.getClass().getMethod(methodName.toString(),
					propertyClass);
			method.invoke(criteria, condition.getMatchValue());
			break;
		default:
			logger.error("unsupport match type:{}", condition.getMatchType());
			break;
		}
	}

	/**
	 *  bootstrap模版使用
	 * @param conditions
	 * @param page
	 * @param exampleName
	 * @return
	 */
	public Object buildExampleByCondition(List<Condition> conditions, Page page, String exampleName) {
        Object example = null;
        if (conditions == null || page == null) {
            return null;
        }
        try {
            example = buildExampleByCondition(conditions, exampleName);
            if (CheckEmptyUtil.isNotEmpty(page.getSort())
                    && CheckEmptyUtil.isNotEmpty(page.getOrder())) {
                // 构建排序条件
                StringBuilder orderByClause = new StringBuilder("");

                String sort = null;
                if(page.getSort().startsWith("CONVERT")){
                	sort = page.getSort().substring(page.getSort().indexOf('(')+1, page.getSort().indexOf(" USING gbk"));
                }else{
                	sort = page.getSort() ;
                }
                int periodsIndex = sort.indexOf(BaseConstant.Separate.PERIODS) ;
                if (periodsIndex>= 0) {
                    String childExampleStr = sort.substring(0, periodsIndex);
                    //orderByClause.append(childExampleStr);
                    //String sortStr = new String(sort.substring(periodsIndex, sort.length()));
                    //orderByClause.append(StringUtil.camelToUnderline(sortStr));
                    Method method = example.getClass().getMethod("get"+StringUtil.toUpperCaseFirstOne(childExampleStr)+"Example");
                    Object childExample = method.invoke(example);

                    orderByClause.append(page.getSort()).append(" ").append(page.getOrder());
                    Method method2 = childExample.getClass().getMethod("setOrderByClause", String.class);
                    method2.invoke(childExample, orderByClause.toString());
                } else {
                    orderByClause.append(page.getSort()).append(" ").append(page.getOrder());
                    Method method = example.getClass().getMethod("setOrderByClause", String.class);
                    method.invoke(example, orderByClause.toString());
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return example;
	}
}
