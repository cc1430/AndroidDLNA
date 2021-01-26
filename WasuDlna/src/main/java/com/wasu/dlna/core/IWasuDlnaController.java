package com.wasu.dlna.core;


import com.wasu.dlna.listener.GetPlayPositionListener;
import com.wasu.dlna.listener.GetVolumeListener;
import com.wasu.dlna.listener.PauseListener;
import com.wasu.dlna.listener.PlayListener;
import com.wasu.dlna.listener.SeekListener;
import com.wasu.dlna.listener.SetMuteListener;
import com.wasu.dlna.listener.SetPlayUrlListener;
import com.wasu.dlna.listener.SetVolumeListener;
import com.wasu.dlna.listener.StopListener;

import org.fourthline.cling.model.meta.Device;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> DLNA投屏控制接口
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/22
 *
 */
public interface IWasuDlnaController {

    /**
     * 设置投屏设备
     * @param device 设备
     */
    void selectDevice(Device device);

    /**
     * 设置投屏的片源和片名
     * @param url 播放地址
     * @param name 视频名称
     */
    void setPlayItem(String url, String name, SetPlayUrlListener listener);

    /**
     * 开始播放
     */
    void play(PlayListener listener);

    /**
     * 暂停
     */
    void pause(PauseListener listener);

    /**
     * 快进、快退
     */
    void seek(int position, SeekListener listener);

    /**
     * 停止
     */
    void stop(StopListener listener);

    /**
     * 获取当前播放进度
     */
    void getPlayPosition(GetPlayPositionListener listener);

    /**
     * 设置音量
     */
    void setVolume(int volume, SetVolumeListener listener);

    /**
     * 获取音量
     */
    void getVolume(GetVolumeListener listener);

    /**
     * 设置静音
     * @param desiredMute 是否静音
     */
    void setMute(boolean desiredMute, SetMuteListener listener);

}
