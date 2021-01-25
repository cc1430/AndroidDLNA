package com.wasu.dlna.listener;

import com.wasu.dlna.bean.DlnaResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 快进快退回调
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/25
 *
 */
public interface SeekListener {
    /**
     * 快进快退成功
     */
    void onSeekSuccess(DlnaResponse response);
    /**
     * 快进快退成功
     */
    void onSeekFailed(DlnaResponse response);
}
