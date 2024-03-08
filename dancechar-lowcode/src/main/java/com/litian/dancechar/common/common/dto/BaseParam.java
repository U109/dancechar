package com.litian.dancechar.common.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用基础参数，相关实体参数校验可继承此类
 *
 */
@Data
public class BaseParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 参数校验分组：分页
     */
    public @interface page {
    }

    /**
     * 参数校验分组：列表
     */
    public @interface list {
    }

    /**
     * 参数校验分组：下拉
     */
    public @interface dropDown {
    }

    /**
     * 参数校验分组：增加
     */
    public @interface add {
    }

    /**
     * 参数校验分组：编辑
     */
    public @interface edit {
    }

    /**
     * 参数校验分组：更新信息
     */
    public @interface updateInfo {
    }

    /**
     * 参数校验分组：修改密码
     */
    public @interface updatePwd {
    }

    /**
     * 参数校验分组：重置密码
     */
    public @interface resetPwd {
    }

    /**
     * 参数校验分组：修改头像
     */
    public @interface updateAvatar {
    }

    /**
     * 参数校验分组：删除
     */
    public @interface delete {
    }

    /**
     * 参数校验分组：详情
     */
    public @interface detail {
    }

    /**
     * 参数校验分组：授权角色
     */
    public @interface grantRole {
    }

    /**
     * 参数校验分组：授权菜单
     */
    public @interface grantMenu {
    }

    /**
     * 参数校验分组：授权数据
     */
    public @interface grantData {
    }

    /**
     * 参数校验分组：强退
     */
    public @interface force {
    }

    /**
     * 参数校验分组：停用
     */
    public @interface stop {
    }

    /**
     * 参数校验分组：启用
     */
    public @interface start {
    }

    /**
     * 参数校验分组：部署
     */
    public @interface deploy {
    }

    /**
     * 参数校验分组：挂起
     */
    public @interface suspend {
    }

    /**
     * 参数校验分组：激活
     */
    public @interface active {
    }

    /**
     * 参数校验分组：委托
     */
    public @interface entrust {
    }

    /**
     * 参数校验分组：转办
     */
    public @interface turn {
    }

    /**
     * 参数校验分组：追踪
     */
    public @interface trace {
    }

    /**
     * 参数校验分组：跳转
     */
    public @interface jump {
    }

    /**
     * 参数校验分组：提交
     */
    public @interface submit {
    }

    /**
     * 参数校验分组：退回
     */
    public @interface back {
    }

    /**
     * 参数校验分组：终止
     */
    public @interface end {
    }

    /**
     * 参数校验分组：导出
     */
    public @interface export {
    }

    /**
     * 参数校验分组：映射
     */
    public @interface mapping {
    }

    /**
     * 参数校验分组：切换
     */
    public @interface change {
    }

    /**
     * 参数校验分组：历史审批记录
     */
    public @interface commentHistory {
    }

    /**
     * 参数校验分组：修改状态
     */
    public @interface changeStatus {
    }

    /**
     * 参数校验分组：加签
     */
    public @interface addSign {
    }

    /**
     * 参数校验分组：减签
     */
    public @interface deleteSign {
    }

    /**
     * 参数校验分组：生成
     */
    public @interface gen {
    }

    /**
     * 参数校验分组：单功能生成
     */
    public @interface funcGen {
    }

    /**
     * 参数校验分组：DB生成
     */
    public @interface dbGen {
    }

    /**
     * 参数校验分组：table生成
     */
    public @interface tableGen {
    }

    /**
     * 参数校验分组：预览分组
     */
    public @interface previewGen {
    }

    /**
     * 校验JSON工具
     */
    public @interface sqlGen {
    }

    /**
     *列表查询必填
     */
    public @interface pageListRequired {
    }

    /**
     * 保存修改
     */
    public @interface addUpdateRequired {
    }
}
