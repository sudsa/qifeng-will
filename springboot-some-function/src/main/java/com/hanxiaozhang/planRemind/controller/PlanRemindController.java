package com.hanxiaozhang.planRemind.controller;

import java.util.List;
import java.util.Map;


import com.hanxiaozhang.utils.PageUtils;
import com.hanxiaozhang.utils.Query;
import com.hanxiaozhang.utils.R;
import com.hanxiaozhang.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanxiaozhang.planRemind.domain.PlanRemindDO;
import com.hanxiaozhang.planRemind.service.PlanRemindService;


/**
 * 计划提醒表
 * 开发耗时9h
 *
 *
 * @author hanxinghua
 * @email hanxinghua2015@sina.com
 * @date 2020-07-05 10:18:16
 */
@Slf4j
@Controller
@RequestMapping("/planRemind")
public class PlanRemindController {

    @Autowired
    private PlanRemindService planRemindService;

    @GetMapping()
    String PlanRemind() {
        return "planRemind/planRemind";
    }

    @ResponseBody
    @GetMapping("/list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
      
        Query query = new Query(params);
        List<PlanRemindDO> planRemindList = planRemindService.list(query);
        int total = planRemindService.count(query);
        PageUtils pageUtils = new PageUtils(planRemindList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    String add() {
        return "planRemind/add";
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        PlanRemindDO planRemind = planRemindService.get(id);
        model.addAttribute("planRemind", planRemind);
        return "planRemind/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(PlanRemindDO planRemind) {
        try {
            checkPlanRemind(planRemind);
            planRemindService.save(planRemind);
        } catch (RuntimeException e) {
            log.error("保存计划提醒参数异常,异常信息:[{}]",e);
            return R.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }


    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(PlanRemindDO planRemind) {
        try {
            checkPlanRemind(planRemind);
            planRemindService.update(planRemind);
        } catch (RuntimeException e) {
            log.error("更新计划提醒参数异常,异常信息:[{}]",e);
            return R.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    /**
     * 校验计划
     *
     * @param planRemind
     */
    private void  checkPlanRemind(PlanRemindDO planRemind){
        if (StringUtil.isBlank(planRemind.getTopic())) {
            throw new RuntimeException("主题不能为空!");
        }
        if (StringUtil.isBlank(planRemind.getContent())) {
            throw new RuntimeException("内容不能为空!");
        }
        if (StringUtil.isBlank(planRemind.getRemindValue())) {
            throw new RuntimeException("提醒值不能为空!");
        }

    }
    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {
        if (planRemindService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Long[] ids) {
        planRemindService.batchRemove(ids);
        return R.ok();
    }

    @ResponseBody
    @PostMapping(value = "/changeExecuteStatus")
    public R changeJobStatus(Long id,Integer executeStatus) {
        try {
            planRemindService.changeExecuteStatus(id, executeStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }


}
