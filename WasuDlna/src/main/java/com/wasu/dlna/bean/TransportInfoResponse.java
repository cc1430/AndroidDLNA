package com.wasu.dlna.bean;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.support.model.TransportInfo;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 播放状态回调
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/27
 *
 */
public class TransportInfoResponse extends BaseResponse<TransportInfo> implements IResponse<TransportInfo>{

    public TransportInfoResponse(ActionInvocation actionInvocation) {
        super(actionInvocation);
    }

    public TransportInfoResponse(ActionInvocation actionInvocation, UpnpResponse operation, String defaultMsg) {
        super(actionInvocation, operation, defaultMsg);
    }

    public TransportInfoResponse(ActionInvocation actionInvocation, TransportInfo info) {
        super(actionInvocation, info);
    }
}
