package com.wasu.dlna.listener;


import android.util.Log;

import com.wasu.dlna.bean.DeviceInfo;
import com.wasu.dlna.utils.Constant;
import com.wasu.dlna.utils.DlnaUtil;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 搜索设备监听
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/21
 * 
 */
public abstract class BrowseRegistryListener extends DefaultRegistryListener {

    /* Discovery performance optimization for very slow Android devices! */
    @Override
    public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {
        Log.d(Constant.TAG, "remoteDeviceDiscoveryStarted: " + device.getIdentity().getUdn().getIdentifierString());
        deviceAdded(registry, device);
    }

    @Override
    public void remoteDeviceDiscoveryFailed(Registry registry, final RemoteDevice device, final Exception ex) {
        Log.d(Constant.TAG, "remoteDeviceDiscoveryFailed: ");
        DeviceInfo deviceInfo = new DeviceInfo(device);
        deviceRemoved(registry, device);
    }
    /* End of optimization, you can remove the whole block if your Android handset is fast (>= 600 Mhz) */

    @Override
    public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
        Log.d(Constant.TAG, "remoteDeviceAdded: ");
        deviceAdded(registry, device);
    }

    @Override
    public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
        Log.d(Constant.TAG, "remoteDeviceRemoved: ");
        deviceRemoved(registry, device);
    }

    @Override
    public void localDeviceAdded(Registry registry, LocalDevice device) {
        Log.d(Constant.TAG, "localDeviceAdded: ");
        deviceAdded(registry, device);
    }

    @Override
    public void localDeviceRemoved(Registry registry, LocalDevice device) {
        Log.d(Constant.TAG, "localDeviceRemoved: ");
        deviceRemoved(registry, device);
    }

    @Override
    public void deviceAdded(Registry registry, Device device) {
        if (DlnaUtil.isMediaRenderDevice(device) && device.isFullyHydrated()) {
            onDeviceAdded(device);
        }
    }

    @Override
    public void deviceRemoved(Registry registry, Device device) {
        onDeviceRemoved(device);
    }

    protected abstract void onDeviceAdded(Device device);

    protected abstract void onDeviceRemoved(Device device);

}