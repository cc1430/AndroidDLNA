package com.wasu.dlna.utils;

public class Constant {
    public static final String TAG = "WASU_DLNA";

    public static final int IMAGE_TYPE = 0;
    public static final int VIDEO_TYPE = 1;
    public static final int AUDIO_TYPE = 2;

    public static final String DIDL_LITE_FOOTER = "</DIDL-Lite>";
    public static final String DIDL_LITE_HEADER = "<?xml version=\"1.0\"?>" +
            "<DIDL-Lite " + "xmlns=\"urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/\" " +
            "xmlns:dc=\"http://purl.org/dc/elements/1.1/\" " + "xmlns:upnp=\"urn:schemas-upnp-org:metadata-1-0/upnp/\" " +
            "xmlns:dlna=\"urn:schemas-dlna-org:metadata-1-0/\">";

    public static final String AV_TRANSPORT = "AVTransport";

    public static final String ACTION_SET_AV_TRANSPORT_URI = "SetAVTransportURI";
    public static final String ACTION_PLAY = "Play";
    public static final String ACTION_PAUSE = "Pause";
    public static final String ACTION_SEEK = "Seek";
    public static final String ACTION_STOP = "Stop";
    public static final String ACTION_GET_POSITION_INFO = "GetPositionInfo";
    public static final String ACTION_SET_VOLUME = "SetVolume";
    public static final String ACTION_GET_VOLUME = "GetVolume";
    public static final String ACTION_SET_MUTE = "SetMute";

    public static final String ARG_TRACK_DURATION = "TrackDuration";
    public static final String ARG_REL_TIME = "RelTime";
}
