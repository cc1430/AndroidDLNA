package com.wasu.dlna.listener;

import com.wasu.dlna.bean.DlnaResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 设置播放暂停回调
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/25
 *
 */
public interface PauseListener {

    /**
     * 暂停成功
     */
    void onPauseSuccess(DlnaResponse response);
    /**
     * 暂停失败
     */
    void onPauseFailed(DlnaResponse response);
}
