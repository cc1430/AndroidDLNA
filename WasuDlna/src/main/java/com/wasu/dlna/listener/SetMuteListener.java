package com.wasu.dlna.listener;

import com.wasu.dlna.bean.DlnaResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 设置/取消静音回调
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/25
 *
 */
public interface SetMuteListener {
    /**
     * 设置静音失败
     */
    void onSetMuteSuccess(DlnaResponse response);

    /**
     * 设置静音失败
     */
    void onSetMuteFailed(DlnaResponse response);
}
