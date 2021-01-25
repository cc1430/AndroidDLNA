package com.wasu.dlna.bean;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 设备控制 返回结果
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/22
 *
 */
public interface IResponse<T> {

    T getResponse();

    void setResponse(T response);
}
