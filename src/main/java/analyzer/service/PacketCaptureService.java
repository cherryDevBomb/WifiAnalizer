package analyzer.service;

import analyzer.model.WirelessNetworkInfo;
import analyzer.observer.Observable;
import analyzer.observer.Observer;
import analyzer.util.ByteUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PacketCaptureService implements Observable {

    private static final String WLAN0MON = "wlan0mon";
    private static final String BFP_EXPRESSION = "wlan type mgt subtype beacon";

    private static final int SNAP_LEN = 65536;
    private static final int TIMEOUT = 10;

    @Getter
    private final List<Observer> observers = new ArrayList<>();

    @Getter
    private final Map<String, WirelessNetworkInfo> networks = new HashMap<>();

    private PcapHandle initHandle() {
        PcapHandle handle = null;
        try {
            PcapNetworkInterface networkInterface = Pcaps.getDevByName(WLAN0MON);
            handle = networkInterface.openLive(SNAP_LEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, TIMEOUT);
            BpfProgram bpfProgram = handle.compileFilter(BFP_EXPRESSION, BpfProgram.BpfCompileMode.NONOPTIMIZE, PcapHandle.PCAP_NETMASK_UNKNOWN);
            handle.setFilter(bpfProgram);
        } catch (PcapNativeException | NotOpenException e) {
            log.error("Error on initHandle()", e);
        }

        return handle;
    }

    public void capture() {
        PcapHandle handle = initHandle();
        try {
            handle.loop(0, (Packet packet) -> {
                System.out.println("getRawData() len: " + packet.getRawData().length + " -----" + ByteUtils.byteArrayToHexString(packet.getRawData()));
                System.out.println("getHeader().getRawData() len: " + packet.getHeader().getRawData().length + " -----" + ByteUtils.byteArrayToHexString(packet.getHeader().getRawData()));
                System.out.println("getPayload().getRawData() len: " + packet.getPayload().getRawData().length + " -----" + ByteUtils.byteArrayToHexString(packet.getPayload().getRawData()));
                WirelessNetworkInfo wirelessNetworkInfo = FrameParser.parseFrame(packet.getPayload().getRawData()); //or packet.getRawData()?
                if (wirelessNetworkInfo != null) {
                    networks.put(Integer.toString(packet.getHeader().length()), new WirelessNetworkInfo(Integer.toString(packet.getHeader().length()), wirelessNetworkInfo.getMAC()));
                    observers.forEach(Observer::updateView);
                }
            });
        } catch (PcapNativeException | InterruptedException | NotOpenException e) {
            log.error("Error on capture()", e);
        }

        //for testing UI on windows
//        int i = 0;
//        while (i++ < 10) {
//            networks.put(Integer.toString(i), new WirelessNetworkInfo());
//            observers.forEach(Observer::updateView);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
