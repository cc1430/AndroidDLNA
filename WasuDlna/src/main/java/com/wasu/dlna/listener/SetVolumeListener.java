package com.wasu.dlna.listener;

import com.wasu.dlna.bean.DlnaResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 设置音量回调
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/25
 *
 */
public interface SetVolumeListener {
    /**
     * 设置音量成功
     */
    void onSetVolumeSuccess(DlnaResponse response);

    /**
     * 设置音量失败
     */
    void onSetVolumeFailed(DlnaResponse response);
}
