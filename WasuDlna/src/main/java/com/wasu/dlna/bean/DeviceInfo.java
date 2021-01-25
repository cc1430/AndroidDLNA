package com.wasu.dlna.bean;

import org.fourthline.cling.model.meta.Device;

/**
 * <p>------------------------------------------------------
 * <p>Copyright (C) 2021 wasu company, All rights reserved.
 * <p>------------------------------------------------------
 * <p> 设备信息
 * <p>
 * @author Created by chenchen
 * @date  on 2021/1/21
 *
 */
public class DeviceInfo {
    private Device device = null;
    private String name = null;

    public DeviceInfo(Device device) {
        this.device = device;
        this.name = getDeviceName(device);
    }

    public DeviceInfo() {
    }

    public Device getDevice() {
        return this.device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private String getDeviceName(Device device) {
        String name = "";
        if(device.getDetails() != null && device.getDetails().getFriendlyName() != null) {
            name = device.getDetails().getFriendlyName();
        } else {
            name = device.getDisplayString();
        }

        return name;
    }
}
