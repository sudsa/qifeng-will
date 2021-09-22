package com.hanxiaozhang.contracttemplate.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈合同模板〉
 *
 * @author hanxinghua
 * @create 2020/2/17
 * @since 1.0.0
 */
public class ContractTemplateDO implements Serializable {

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板状态
     */
    private Integer templateStatus;

    /**
     * 模板内容
     */
    private String templateContent;

    /**
     * 渠道id
     */
    private Long channelId;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 版本
     */
    private String version;

    private Long createBy;
    private Long updateBy;
    private Date createDate;
    private Date updateDate;
    private String remark;
    private String delFlag;

    public ContractTemplateDO() {
    }

    public Long getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getTemplateContent() {
        return this.templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent == null ? null : templateContent.trim();
    }

    public Integer getTemplateStatus() {
        return this.templateStatus;
    }

    public void setTemplateStatus(Integer templateStatus) {
        this.templateStatus = templateStatus;
    }

    public Long getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Long getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }


}
