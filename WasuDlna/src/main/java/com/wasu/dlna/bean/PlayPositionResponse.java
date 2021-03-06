package com.wasu.dlna.bean;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.support.model.PositionInfo;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 获取播放进度回调结果
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/22
 *
 */
public class PlayPositionResponse extends BaseResponse<PositionInfo> implements IResponse<PositionInfo> {


    public PlayPositionResponse(ActionInvocation actionInvocation) {
        super(actionInvocation);
    }

    public PlayPositionResponse(ActionInvocation actionInvocation, UpnpResponse operation, String defaultMsg) {
        super(actionInvocation, operation, defaultMsg);
    }

    public PlayPositionResponse(ActionInvocation actionInvocation, PositionInfo info) {
        super(actionInvocation, info);
    }

}
