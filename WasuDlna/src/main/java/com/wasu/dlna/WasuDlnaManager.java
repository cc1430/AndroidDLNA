package com.wasu.dlna;

import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.wasu.dlna.core.IWasuDlnaController;
import com.wasu.dlna.core.WasuDlnaController;
import com.wasu.dlna.core.WasuDlnaService;
import com.wasu.dlna.listener.BrowseRegistryListener;
import com.wasu.dlna.utils.Constant;

import org.fourthline.cling.controlpoint.ControlPoint;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> DLNA
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/21
 *
 */
public class WasuDlnaManager {

    private Context context;
    private WasuDlnaService wasuDlnaService;
    private ControlPoint controlPoint;
    private BrowseRegistryListener browseRegistryListener;
    private IWasuDlnaController wasuDlnaController;

    private static WasuDlnaManager instance;

    public static synchronized WasuDlnaManager getInstance() {
        if (instance == null) {
            instance = new WasuDlnaManager();
        }
        return instance;
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(Constant.TAG, "onServiceConnected: ");
            WasuDlnaService.LocalBinder binder = ((WasuDlnaService.LocalBinder) service);
            wasuDlnaService = binder.getService();
            controlPoint = wasuDlnaService.getControlPoint();
            wasuDlnaController = new WasuDlnaController(controlPoint);

            wasuDlnaService.getRegistry().addListener(browseRegistryListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(Constant.TAG, "onServiceDisconnected: ");
            wasuDlnaService = null;
            wasuDlnaController = null;
        }
    };

    public WasuDlnaManager() {

    }

    public void init(Application application, BrowseRegistryListener listener) {
        this.context = application.getApplicationContext();
        this.browseRegistryListener = listener;
    }

    public void prepare() {
        Log.d(Constant.TAG, "prepare: ");
        Intent intent = new Intent(context, WasuDlnaService.class);
        context.bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    public void searchDevices() {
        if (wasuDlnaService != null) {
            wasuDlnaService.getRegistry().removeAllRemoteDevices();
        }

        if (controlPoint != null) {
            controlPoint.search();
        }
    }

    public IWasuDlnaController getWasuDlnaController() {
        return wasuDlnaController;
    }

    public void unInit() {
        if (wasuDlnaService != null) {
            wasuDlnaService.getRegistry().removeListener(browseRegistryListener);
        }

        context.unbindService(serviceConnection);
    }

}
