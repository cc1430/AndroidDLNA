package com.wasu.dlna.listener;

import com.wasu.dlna.bean.VolumeResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 获取音量回调
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/25
 *
 */
public interface GetVolumeListener {
    /**
     * 获取音量成功
     */
    void onGetVolumeSuccess(VolumeResponse response);
    /**
     * 收到音量值
     */
    void onReceivedVolume(VolumeResponse response);
    /**
     * 获取音量失败
     */
    void onGetVolumeFailed(VolumeResponse response);
}
