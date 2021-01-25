package com.wasu.dlna.listener;

import com.wasu.dlna.bean.DlnaResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 停止播放回调
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/25
 *
 */
public interface StopListener {
    /**
     * 停止播放成功
     */
    void onStopSuccess(DlnaResponse response);
    /**
     * 停止播放失败
     */
    void onStopFailed(DlnaResponse response);
}
