package analyzer.parser;

import java.util.Optional;

import analyzer.model.GenericManagementFrame;
import analyzer.model.InformationElement;
import analyzer.model.InformationElementID;
import analyzer.model.WirelessNetworkInfo;
import analyzer.util.ByteUtils;

public class FrameParser {

    public static WirelessNetworkInfo parseFrame(byte[] frame) {
        WirelessNetworkInfo wirelessNetworkInfo = new WirelessNetworkInfo();

        GenericManagementFrame genericManagementFrame = GenericManagementFrame.parse(frame);

        // extract SSID from information element
        Optional<InformationElement> ssidElement = genericManagementFrame.getInformationElementById(InformationElementID.SERVICE_SET_IDENTITY);
        if (ssidElement.isPresent()) {
            String ssid = new String(ssidElement.get().getBody());
            wirelessNetworkInfo.setSSID(ssid);
        }

        // set MAC address to BSSID
        String macAddress = ByteUtils.byteArrayToHexString(genericManagementFrame.getBssid(), ":");
        wirelessNetworkInfo.setMAC(macAddress);

        // extract Channel from information element
        Optional<InformationElement> dsParameterSetElement = genericManagementFrame.getInformationElementById(InformationElementID.DIRECT_SEQUENCE_PARAMETER_SET);
        if (dsParameterSetElement.isPresent()) {
            byte channel = dsParameterSetElement.get().getBody()[0];
            wirelessNetworkInfo.setChannel(String.valueOf(channel));
        }

        // TODO is check needed?
//        if (ManagementFrameSubtype.BEACON.equals(genericManagementFrame.getFrameControl())) {
//        }

        return wirelessNetworkInfo;
    }
}
