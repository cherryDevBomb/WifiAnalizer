package analyzer.service;

import analyzer.model.GenericManagementFrame;
import analyzer.model.ManagementFrameSubtype;
import analyzer.model.WirelessNetworkInfo;
import com.google.common.primitives.Bytes;

import java.util.stream.Collectors;

public class FrameParser {

    public static WirelessNetworkInfo parseFrame(byte[] frame) {
        GenericManagementFrame genericManagementFrame = GenericManagementFrame.parse(frame);

        if (ManagementFrameSubtype.BEACON.equals(genericManagementFrame.getFrameControl())) {
            //TODO what?
        }

        String macAddress = Bytes.asList(genericManagementFrame.getBssid())
                .stream()
                .map(Byte::toUnsignedInt)
                .map(Integer::toHexString)
                .collect(Collectors.joining(":"));

        return new WirelessNetworkInfo("1", macAddress);
    }
}
