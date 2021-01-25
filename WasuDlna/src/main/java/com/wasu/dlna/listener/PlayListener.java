package com.wasu.dlna.listener;

import com.wasu.dlna.bean.DlnaResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 播放回调
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/25
 *
 */
public interface PlayListener {

    /**
     * 播放成功
     */
    void onPlaySuccess(DlnaResponse response);
    /**
     * 播放失败
     */
    void onPlayFailed(DlnaResponse response);
}
