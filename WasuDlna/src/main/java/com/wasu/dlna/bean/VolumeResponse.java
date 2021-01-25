package com.wasu.dlna.bean;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 音量返回
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/22
 *
 */
public class VolumeResponse extends BaseResponse<Integer> {

    public VolumeResponse(ActionInvocation actionInvocation) {
        super(actionInvocation);
    }

    public VolumeResponse(ActionInvocation actionInvocation, UpnpResponse operation, String defaultMsg) {
        super(actionInvocation, operation, defaultMsg);
    }

    public VolumeResponse(ActionInvocation actionInvocation, Integer info) {
        super(actionInvocation, info);
    }
}
