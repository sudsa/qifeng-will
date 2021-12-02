package com.qifeng.will.planRemind.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;



/**
 * 计划提醒表
 * 
 * @author hanxinghua
 * @email hanxinghua2015@sina.com
 * @date 2020-07-05 10:18:16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PlanRemindDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键ID
	private Long id;
	//计划类型：0：单次执行，1：每日执行，2：周期执行，3：星期执行，4：周期星期执行
	private Integer planType;
	//执行状态：0：停止，1：启动
	private Integer executeStatus;
	//主题
	private String topic;
	//内容
	private String content;
	//提醒类型：0：邮件
	private Integer remindType;
	//提醒值
	private String remindValue;
	//完成标识：0：未完成，1：完成
	private Integer finishFlag;
	//开始日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate startDate;
	//结束日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate endDate;
	//执行日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate executeDate;
	//执行时间
	@DateTimeFormat(pattern="HH:mm:ss")
	private LocalTime executeTime;
	//周期个数
	private Integer cycleNumber;
	//周期单位，0：秒，1：分，2：时，3：天，4：月，5：年
	private Integer cycleUnit;
	//最近一次执行时间
	private LocalDateTime recentExecuteDate;
	//最近一次执行时间标识 1清除
	private Integer recentExecuteDateFlag;
	// 周一标识：0，否，1：是
	private Integer mondayFlag;
	// 周二标识：0，否，2：是
	private Integer tuesdayFlag;
	// 周三标识：0，否，3：是
	private Integer wednesdayFlag;
	// 周四标识：0，否，4：是
	private Integer thursdayFlag;
	// 周五标识：0，否，5：是
	private Integer fridayFlag;
	// 周六标识：0，否，6：是
	private Integer saturdayFlag;
	// 周日标识：0，否，7：是
	private Integer sundayFlag;
	//创建人
	private Long createBy;
	//创建时间
	private Date createTime;
	//修改人
	private Long updateBy;
	//更新时间
	private Date updateTime;
	//备注
	private String remark;
	//是否删除  1：已删除  0：正常
	private String delFlag;

}
