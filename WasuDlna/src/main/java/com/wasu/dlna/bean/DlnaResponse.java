package com.wasu.dlna.bean;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 控制操作返回结果
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/22
 *
 */
public class DlnaResponse implements IResponse<ActionInvocation> {

    private ActionInvocation mActionInvocation;
    private UpnpResponse operation;
    private String defaultMsg;

    /**
     * 控制操作成功 构造器
     *
     * @param actionInvocation  cling action 调用
     */
    public DlnaResponse(ActionInvocation actionInvocation) {
        mActionInvocation = actionInvocation;
    }

    /**
     * 控制操作失败 构造器
     *
     * @param actionInvocation  cling action 调用
     * @param operation     执行状态
     * @param defaultMsg    错误信息
     */
    public DlnaResponse(ActionInvocation actionInvocation, UpnpResponse operation, String defaultMsg) {
        mActionInvocation = actionInvocation;
        this.operation = operation;
        this.defaultMsg = defaultMsg;
    }

    @Override
    public ActionInvocation getResponse() {
        return null;
    }

    @Override
    public void setResponse(ActionInvocation response) {
        mActionInvocation = response;
    }
}
