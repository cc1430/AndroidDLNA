package com.wasu.dlna.core;

import android.content.Intent;
import android.os.IBinder;

import org.fourthline.cling.UpnpServiceConfiguration;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.registry.Registry;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> UPNP服务
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/22
 *
 */
public class WasuDlnaService extends AndroidUpnpServiceImpl {

    private LocalDevice mLocalDevice = null;

    @Override
    public void onCreate() {
        super.onCreate();

        //LocalBinder instead of binder
        binder = new LocalBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public LocalDevice getLocalDevice() {
        return mLocalDevice;
    }

    public UpnpServiceConfiguration getConfiguration() {
        return upnpService.getConfiguration();
    }

    public Registry getRegistry() {
        return upnpService.getRegistry();
    }

    public ControlPoint getControlPoint() {
        return upnpService.getControlPoint();
    }

    public class LocalBinder extends Binder {
        public WasuDlnaService getService() {
            return WasuDlnaService.this;
        }
    }
}
