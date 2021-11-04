package analyzer.parser;

import java.util.Optional;

import analyzer.model.frame.GenericManagementFrame;
import analyzer.model.frame.InformationElement;
import analyzer.model.frame.InformationElementID;
import analyzer.model.WirelessNetworkInfo;
import analyzer.model.radiotap.Radiotap;
import analyzer.util.ByteUtils;

public class FrameParser {

    public static WirelessNetworkInfo parseFrame(byte[] header, byte[] frame) {
        WirelessNetworkInfo wirelessNetworkInfo = new WirelessNetworkInfo();

        Radiotap radiotap = Radiotap.parse(header);
        GenericManagementFrame genericManagementFrame = GenericManagementFrame.parse(frame);

        // extract SSID from information element
        Optional<InformationElement> ssidElement = genericManagementFrame.getFrameBody().getInformationElementById(InformationElementID.SERVICE_SET_IDENTITY);
        if (ssidElement.isPresent()) {
            String ssid = new String(ssidElement.get().getBody());
            wirelessNetworkInfo.setSSID(ssid);
        }

        // set MAC address to BSSID
        String macAddress = ByteUtils.byteArrayToHexString(genericManagementFrame.getBssid(), ":");
        wirelessNetworkInfo.setMAC(macAddress);

        // extract signal power from radiotap
        String signalPower = radiotap.getAntennaSignal().getDBmSignalPower() + "dBm";
        wirelessNetworkInfo.setSignalPower(signalPower);

        // extract Channel from information element
        Optional<InformationElement> dsParameterSetElement = genericManagementFrame.getFrameBody().getInformationElementById(InformationElementID.DIRECT_SEQUENCE_PARAMETER_SET);
        if (dsParameterSetElement.isPresent()) {
            byte channel = dsParameterSetElement.get().getBody()[0];
            wirelessNetworkInfo.setChannel(String.valueOf(channel));
        }

        // extract frequency from radiotap
        String frequency = radiotap.getChannel().getFrequency() + "MHz";
        wirelessNetworkInfo.setFrequency(frequency);

        // TODO is check needed?
//        if (ManagementFrameSubtype.BEACON.equals(genericManagementFrame.getFrameControl())) {
//        }

        return wirelessNetworkInfo;
    }
}
