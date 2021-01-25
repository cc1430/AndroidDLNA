package com.wasu.dlna.bean;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 回调基类
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/22
 *
 */
public class BaseResponse<T> implements IResponse<T> {

    protected ActionInvocation mActionInvocation;
    protected UpnpResponse operation;
    protected String defaultMsg;
    protected T info;

    /**
     * 控制操作成功 构造器
     *
     * @param actionInvocation  cling action 调用
     */
    public BaseResponse(ActionInvocation actionInvocation) {
        mActionInvocation = actionInvocation;
    }

    /**
     * 控制操作失败 构造器
     *
     * @param actionInvocation  cling action 调用
     * @param operation     执行状态
     * @param defaultMsg    错误信息
     */
    public BaseResponse(ActionInvocation actionInvocation, UpnpResponse operation, String defaultMsg) {
        mActionInvocation = actionInvocation;
        this.operation = operation;
        this.defaultMsg = defaultMsg;
    }

    /**
     * 接收时的回调
     *
     * @param actionInvocation  cling action 调用
     * @param info      回调的对象
     */
    public BaseResponse(ActionInvocation actionInvocation, T info) {
        mActionInvocation = actionInvocation;
        this.info = info;
    }

    @Override
    public T getResponse() {
        return info;
    }

    @Override
    public void setResponse(T response) {
        info = response;
    }
}
