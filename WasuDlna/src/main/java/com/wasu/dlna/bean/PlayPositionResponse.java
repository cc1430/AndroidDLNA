package com.wasu.dlna.bean;

import com.wasu.dlna.utils.Constant;

import org.fourthline.cling.model.action.ActionArgumentValue;
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

    public String getTrackDuration() {
        String res = "";
        try {
            ActionArgumentValue actionArgumentValue = mActionInvocation.getOutput(Constant.ARG_TRACK_DURATION);
            res = (String) actionArgumentValue.getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public String getRelTime() {
        String res = "";
        try {
            ActionArgumentValue actionArgumentValue = mActionInvocation.getOutput(Constant.ARG_REL_TIME);
            res = (String) actionArgumentValue.getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
