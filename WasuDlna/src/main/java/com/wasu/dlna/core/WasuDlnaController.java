package com.wasu.dlna.core;

import android.util.Log;

import com.wasu.dlna.bean.DlnaResponse;
import com.wasu.dlna.bean.PlayPositionResponse;
import com.wasu.dlna.bean.TransportInfoResponse;
import com.wasu.dlna.bean.VolumeResponse;
import com.wasu.dlna.listener.GetPlayPositionListener;
import com.wasu.dlna.listener.GetTransportInfoListener;
import com.wasu.dlna.listener.GetVolumeListener;
import com.wasu.dlna.listener.PauseListener;
import com.wasu.dlna.listener.PlayListener;
import com.wasu.dlna.listener.SeekListener;
import com.wasu.dlna.listener.SetMuteListener;
import com.wasu.dlna.listener.SetPlayUrlListener;
import com.wasu.dlna.listener.SetVolumeListener;
import com.wasu.dlna.listener.StopListener;
import com.wasu.dlna.utils.Constant;
import com.wasu.dlna.utils.DlnaUtil;
import com.wasu.dlna.utils.Utils;

import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.UDAServiceId;
import org.fourthline.cling.support.avtransport.callback.GetPositionInfo;
import org.fourthline.cling.support.avtransport.callback.GetTransportInfo;
import org.fourthline.cling.support.avtransport.callback.Pause;
import org.fourthline.cling.support.avtransport.callback.Play;
import org.fourthline.cling.support.avtransport.callback.Seek;
import org.fourthline.cling.support.avtransport.callback.SetAVTransportURI;
import org.fourthline.cling.support.avtransport.callback.Stop;
import org.fourthline.cling.support.model.PositionInfo;
import org.fourthline.cling.support.model.TransportInfo;
import org.fourthline.cling.support.renderingcontrol.callback.GetVolume;
import org.fourthline.cling.support.renderingcontrol.callback.SetMute;
import org.fourthline.cling.support.renderingcontrol.callback.SetVolume;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> DLNA投屏控制器
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/22
 *
 */
public class WasuDlnaController implements IWasuDlnaController {

    private final ControlPoint controlPoint;
    private Service avTransportService;
    private Service renderingControlService;
    private Device device;

    public WasuDlnaController(ControlPoint controlPoint) {
        this.controlPoint = controlPoint;
    }

    @Override
    public void selectDevice(Device device) {
        this.device = device;
        UDAServiceId udaServiceId = new UDAServiceId(Constant.SERVICE_ID_AV_TRANSPORT);
        avTransportService = device.findService(udaServiceId);

        udaServiceId = new UDAServiceId(Constant.SERVICE_ID_RENDERING_CONTROL);
        renderingControlService = device.findService(udaServiceId);

        if (Utils.isNull(avTransportService)) {
            Log.d(Constant.TAG, "selectDevice: avTransportService == null");
        }

        if (Utils.isNull(renderingControlService)) {
            Log.d(Constant.TAG, "selectDevice: renderingControlService == null");
        }
    }

    @Override
    public void setPlayItem(String url, String name, SetPlayUrlListener listener) {
        if (Utils.isNull(avTransportService)) {
            Log.d(Constant.TAG, "setPlayItem: service == null");
            return;
        }

        if (Utils.isNull(controlPoint)) {
            Log.d(Constant.TAG, "setPlayItem: controlPoint == null");
            return;
        }

        if (!DlnaUtil.isSupportAction(avTransportService, Constant.ACTION_SET_AV_TRANSPORT_URI)) {
            return;
        }

        String metaData = DlnaUtil.pushMediaToRender(url, "id", name, "0", Constant.VIDEO_TYPE);
        SetAVTransportURI setAVTransportURI = new SetAVTransportURI(avTransportService, url, metaData) {
            @Override
            public void success(ActionInvocation invocation) {
                Log.d(Constant.TAG, "SetAVTransportURI success: ");
                if (Utils.isNotNull(listener)) {
                    listener.onSetPlayItemSuccess(new DlnaResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                Log.d(Constant.TAG, "SetAVTransportURI failure: " + defaultMsg);
                if (Utils.isNotNull(listener)) {
                    listener.onSetPlayItemFailed(new DlnaResponse(invocation, operation, defaultMsg));
                }
            }
        };
        controlPoint.execute(setAVTransportURI);
    }

    @Override
    public void play(PlayListener listener) {
        if (Utils.isNull(avTransportService)) {
            Log.d(Constant.TAG, "play: service == null");
            return;
        }

        if (Utils.isNull(controlPoint)) {
            Log.d(Constant.TAG, "play: controlPoint == null");
            return;
        }

        if (!DlnaUtil.isSupportAction(avTransportService, Constant.ACTION_PLAY)) {
            return;
        }

        controlPoint.execute(new Play(avTransportService) {
            @Override
            public void success(ActionInvocation invocation) {
                Log.d(Constant.TAG, "play success: ");
                if (Utils.isNotNull(listener)) {
                    listener.onPlaySuccess(new DlnaResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                Log.d(Constant.TAG, "play failure: ");
                if (Utils.isNotNull(listener)) {
                    listener.onPlayFailed(new DlnaResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }

    @Override
    public void pause(PauseListener listener) {
        if (Utils.isNull(avTransportService)) {
            Log.d(Constant.TAG, "pause: service == null");
            return;
        }

        if (Utils.isNull(controlPoint)) {
            Log.d(Constant.TAG, "pause: controlPoint == null");
            return;
        }

        if (!DlnaUtil.isSupportAction(avTransportService, Constant.ACTION_PAUSE)) {
            return;
        }

        controlPoint.execute(new Pause(avTransportService) {
            @Override
            public void success(ActionInvocation invocation) {
                Log.d(Constant.TAG, "pause success: ");
                if (Utils.isNotNull(listener)) {
                    listener.onPauseSuccess(new DlnaResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                Log.d(Constant.TAG, "pause failure: ");
                if (Utils.isNotNull(listener)) {
                    listener.onPauseFailed(new DlnaResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }

    @Override
    public void seek(int position, SeekListener listener) {
        if (Utils.isNull(avTransportService)) {
            Log.d(Constant.TAG, "seek: service == null");
            return;
        }

        if (Utils.isNull(controlPoint)) {
            Log.d(Constant.TAG, "seek: controlPoint == null");
            return;
        }

        if (!DlnaUtil.isSupportAction(avTransportService, Constant.ACTION_SEEK)) {
            return;
        }

        String time = Utils.getStringTime(position);
        controlPoint.execute(new Seek(avTransportService, time) {

            @Override
            public void success(ActionInvocation invocation) {
                Log.d(Constant.TAG, "seek success: ");
                if (Utils.isNotNull(listener)) {
                    listener.onSeekSuccess(new DlnaResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                Log.d(Constant.TAG, "seek failure: ");
                if (Utils.isNotNull(listener)) {
                    listener.onSeekFailed(new DlnaResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }

    @Override
    public void stop(StopListener listener) {
        if (Utils.isNull(avTransportService)) {
            Log.d(Constant.TAG, "stop: service == null");
            return;
        }

        if (Utils.isNull(controlPoint)) {
            Log.d(Constant.TAG, "stop: controlPoint == null");
            return;
        }

        if (!DlnaUtil.isSupportAction(avTransportService, Constant.ACTION_STOP)) {
            return;
        }

        controlPoint.execute(new Stop(avTransportService) {
            @Override
            public void success(ActionInvocation invocation) {
                Log.d(Constant.TAG, "Stop success: ");
                if (Utils.isNotNull(listener)) {
                    listener.onStopSuccess(new DlnaResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                Log.d(Constant.TAG, "Stop failure: ");
                if (Utils.isNotNull(listener)) {
                    listener.onStopFailed(new DlnaResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }

    @Override
    public void getPlayPosition(GetPlayPositionListener listener) {
        if (Utils.isNull(avTransportService)) {
            Log.d(Constant.TAG, "getPlayPosition: service == null");
            return;
        }

        if (Utils.isNull(controlPoint)) {
            Log.d(Constant.TAG, "getPlayPosition: controlPoint == null");
            return;
        }

        if (!DlnaUtil.isSupportAction(avTransportService, Constant.ACTION_GET_POSITION_INFO)) {
            return;
        }

        controlPoint.execute(new GetPositionInfo(avTransportService) {
            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                Log.d(Constant.TAG, "GetPositionInfo success: ");
                if (Utils.isNotNull(listener)) {
                    listener.onGetPlayPositionSuccess(new PlayPositionResponse(invocation));
                }
            }

            @Override
            public void received(ActionInvocation invocation, PositionInfo positionInfo) {
                Log.d(Constant.TAG, "GetPositionInfo received: ");
                if (Utils.isNotNull(listener)) {
                    listener.onReceivedPlayPosition(new PlayPositionResponse(invocation, positionInfo));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                Log.d(Constant.TAG, "failure: ");
                if (Utils.isNotNull(listener)) {
                    listener.onGetPlayPositionFailed(new PlayPositionResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }

    @Override
    public void setVolume(int volume, SetVolumeListener listener) {
        if (Utils.isNull(renderingControlService)) {
            Log.d(Constant.TAG, "setVolume: service == null");
            return;
        }

        if (Utils.isNull(controlPoint)) {
            Log.d(Constant.TAG, "setVolume: controlPoint == null");
            return;
        }

        if (!DlnaUtil.isSupportAction(renderingControlService, Constant.ACTION_SET_VOLUME)) {
            return;
        }

        controlPoint.execute(new SetVolume(renderingControlService, volume) {
            @Override
            public void success(ActionInvocation invocation) {
                Log.d(Constant.TAG, "SetVolume success: ");
                if (Utils.isNotNull(listener)) {
                    listener.onSetVolumeSuccess(new DlnaResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                Log.d(Constant.TAG, "SetVolume failure: ");
                if (Utils.isNotNull(listener)) {
                    listener.onSetVolumeFailed(new DlnaResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }

    @Override
    public void getVolume(GetVolumeListener listener) {
        if (Utils.isNull(renderingControlService)) {
            Log.d(Constant.TAG, "getVolume: service == null");
            return;
        }

        if (Utils.isNull(controlPoint)) {
            Log.d(Constant.TAG, "getVolume: controlPoint == null");
            return;
        }

        if (!DlnaUtil.isSupportAction(renderingControlService, Constant.ACTION_GET_VOLUME)) {
            return;
        }

        controlPoint.execute(new GetVolume(renderingControlService) {
            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                Log.d(Constant.TAG, "GetVolume success: ");

                if (Utils.isNotNull(listener)) {
                    listener.onGetVolumeSuccess(new VolumeResponse(invocation));
                }
            }

            @Override
            public void received(ActionInvocation actionInvocation, int currentVolume) {
                Log.d(Constant.TAG, "GetVolume received: " + currentVolume);
                if (Utils.isNotNull(listener)) {
                    listener.onReceivedVolume(new VolumeResponse(actionInvocation, currentVolume));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                Log.d(Constant.TAG, "GetVolume failure: ");
                if (Utils.isNotNull(listener)) {
                    listener.onGetVolumeFailed(new VolumeResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }

    @Override
    public void setMute(boolean desiredMute, SetMuteListener listener) {
        if (Utils.isNull(renderingControlService)) {
            Log.d(Constant.TAG, "setMute: service == null");
            return;
        }

        if (Utils.isNull(controlPoint)) {
            Log.d(Constant.TAG, "setMute: controlPoint == null");
            return;
        }

        if (!DlnaUtil.isSupportAction(renderingControlService, Constant.ACTION_SET_MUTE)) {
            return;
        }

        controlPoint.execute(new SetMute(renderingControlService, desiredMute) {
            @Override
            public void success(ActionInvocation invocation) {
                Log.d(Constant.TAG, "SetMute success: ");
                if (Utils.isNotNull(listener)) {
                    listener.onSetMuteSuccess(new DlnaResponse(invocation));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                Log.d(Constant.TAG, "SetMute failure: ");
                if (Utils.isNotNull(listener)) {
                    listener.onSetMuteFailed(new DlnaResponse(invocation, operation, defaultMsg));
                }
            }
        });
    }

    @Override
    public void getTransportInfo(GetTransportInfoListener listener) {
        if (Utils.isNull(avTransportService)) {
            Log.d(Constant.TAG, "getTransportInfo: service == null");
            return;
        }

        if (Utils.isNull(controlPoint)) {
            Log.d(Constant.TAG, "setMute: controlPoint == null");
            return;
        }

        if (!DlnaUtil.isSupportAction(avTransportService, Constant.ACTION_GET_TRANSPORT_INFO)) {
            return;
        }

        controlPoint.execute(new GetTransportInfo(avTransportService) {

            @Override
            public void success(ActionInvocation invocation) {
                super.success(invocation);
                if (Utils.isNotNull(listener)) {
                    listener.onGetTransportInfoSuccess(new TransportInfoResponse(invocation));
                }
            }

            @Override
            public void received(ActionInvocation invocation, TransportInfo transportInfo) {
                if (Utils.isNotNull(listener)) {
                    listener.onReceivedTransportInfo(new TransportInfoResponse(invocation, transportInfo));
                }
            }

            @Override
            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                if (Utils.isNotNull(listener)) {
                    listener.onGetTransportInfoFailed(new TransportInfoResponse(invocation, operation, defaultMsg));
                }

            }
        });
    }
}
