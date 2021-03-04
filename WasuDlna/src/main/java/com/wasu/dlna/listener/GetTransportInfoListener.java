package com.wasu.dlna.listener;

import com.wasu.dlna.bean.TransportInfoResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 获取播放状态接口
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/27
 *
 */
public interface GetTransportInfoListener {

    /**
     * 获取播放状态成功
     */
    void onGetTransportInfoSuccess(TransportInfoResponse response);

    /**
     * 获取播放状态结果
     */
    void onReceivedTransportInfo(TransportInfoResponse response);

    /**
     * 获取播放状态失败
     */
    void onGetTransportInfoFailed(TransportInfoResponse response);
}
