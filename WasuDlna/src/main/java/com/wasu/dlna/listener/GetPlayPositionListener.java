package com.wasu.dlna.listener;

import com.wasu.dlna.bean.PlayPositionResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 获取播放进度回调
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/25
 *
 */
public interface GetPlayPositionListener {
    /**
     * 获取播放进去成功
     */
    void onGetPlayPositionSuccess(PlayPositionResponse response);

    /**
     * 收到播放进度
     */
    void onReceivedPlayPosition(PlayPositionResponse response);
    /**
     * 获取播放进度失败
     */
    void onGetPlayPositionFailed(PlayPositionResponse response);
}
