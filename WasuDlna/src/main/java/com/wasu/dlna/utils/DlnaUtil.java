package com.wasu.dlna.utils;

import android.util.Log;

import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.ProtocolInfo;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.item.AudioItem;
import org.fourthline.cling.support.model.item.ImageItem;
import org.fourthline.cling.support.model.item.VideoItem;
import org.seamless.util.MimeType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DlnaUtil {

    /**
     * Check if the device is a media render device
     *
     * @param device
     * @return
     */
    public static boolean isMediaRenderDevice(Device device) {
        if (device != null
                && Constant.DEVICE_MEDIA_RENDER.equalsIgnoreCase(device.getType().toString())) {
            return true;
        }

        return false;
    }

    public static String pushMediaToRender(String url, String id, String name, String duration, int ItemType) {
        long size = 0;
        long bitrate = 0;
        Res res = new Res(new MimeType(ProtocolInfo.WILDCARD, ProtocolInfo.WILDCARD), size, url);

        String creator = "unknow";
        String resolution = "unknow";
        String metadata = null;

        switch (ItemType) {
            case Constant.IMAGE_TYPE:
                ImageItem imageItem = new ImageItem(id, "0", name, creator, res);
                metadata = createItemMetadata(imageItem);
                break;
            case Constant.VIDEO_TYPE:
                VideoItem videoItem = new VideoItem(id, "0", name, creator, res);
                metadata = createItemMetadata(videoItem);
                break;
            case Constant.AUDIO_TYPE:
                AudioItem audioItem = new AudioItem(id, "0", name, creator, res);
                metadata = createItemMetadata(audioItem);
                break;
        }

        Log.e(Constant.TAG, "metadata: " + metadata);
        return metadata;
    }

    public static String createItemMetadata(DIDLObject item) {
        StringBuilder metadata = new StringBuilder();
        metadata.append(Constant.DIDL_LITE_HEADER);

        metadata.append(String.format("<item id=\"%s\" parentID=\"%s\" restricted=\"%s\">", item.getId(), item.getParentID(), item.isRestricted() ? "1" : "0"));

        metadata.append(String.format("<dc:title>%s</dc:title>", item.getTitle()));
        String creator = item.getCreator();
        if (creator != null) {
            creator = creator.replaceAll("<", "_");
            creator = creator.replaceAll(">", "_");
        }
        metadata.append(String.format("<upnp:artist>%s</upnp:artist>", creator));
        metadata.append(String.format("<upnp:class>%s</upnp:class>", item.getClazz().getValue()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date now = new Date();
        String time = sdf.format(now);
        metadata.append(String.format("<dc:date>%s</dc:date>", time));

        Res res = item.getFirstResource();
        if (res != null) {
            // protocol info
            String protocolinfo = "";
            ProtocolInfo pi = res.getProtocolInfo();
            if (pi != null) {
                protocolinfo = String.format("protocolInfo=\"%s:%s:%s:%s\"", pi.getProtocol(), pi.getNetwork(), pi.getContentFormatMimeType(), pi
                        .getAdditionalInfo());
            }
            Log.e(Constant.TAG, "protocolinfo: " + protocolinfo);

            // resolution, extra info, not adding yet
            String resolution = "";
            if (res.getResolution() != null && res.getResolution().length() > 0) {
                resolution = String.format("resolution=\"%s\"", res.getResolution());
            }

            // duration
            String duration = "";
            if (res.getDuration() != null && res.getDuration().length() > 0) {
                duration = String.format("duration=\"%s\"", res.getDuration());
            }

            // res begin
            //            metadata.append(String.format("<res %s>", protocolinfo)); // no resolution & duration yet
            metadata.append(String.format("<res %s %s %s>", protocolinfo, resolution, duration));

            // url
            String url = res.getValue();
            metadata.append(url);

            // res end
            metadata.append("</res>");
        }
        metadata.append("</item>");

        metadata.append(Constant.DIDL_LITE_FOOTER);

        return metadata.toString();
    }

    public static boolean isSupportAction(Service service, String actionName) {
        boolean res = false;
        if (Utils.isNotNull(service)) {
            Action action = service.getAction(actionName);
            res = Utils.isNotNull(action);
        }

        Log.d(Constant.TAG, actionName + " is supported = " + res);
        return res;
    }

}
