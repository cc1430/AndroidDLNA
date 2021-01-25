package com.wasu.dlna.listener;

import com.wasu.dlna.bean.DlnaResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 设置播放地址回调
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/25
 *
 */
public interface SetPlayUrlListener {
    /**
     * 设置播放成功
     */
    void onSetPlayItemSuccess(DlnaResponse response);
    /**
     * 设置播放失败
     */
    void onSetPlayItemFailed(DlnaResponse response);
}
