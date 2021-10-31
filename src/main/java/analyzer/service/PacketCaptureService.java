package analyzer.service;

import analyzer.model.WirelessNetworkInfo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PacketCaptureService {

    private static final String WLAN0MON = "wlan0mon";
    private static final String BFP_EXPRESSION = "wlan type mgt subtype beacon";

    private static final int SNAP_LEN = 65536;
    private static final int TIMEOUT = 10;

    @Getter
    private final List<WirelessNetworkInfo> networks = new ArrayList<>();

    public void capture() {
        PcapHandle handle = initHandle();
        try {
            handle.loop(0, (Packet packet) -> {
                System.out.println("header len: " + packet.getHeader().length() + "; payload len:" + packet.getPayload().length());
                networks.add(new WirelessNetworkInfo(Integer.toString(packet.getHeader().length()), Integer.toString(packet.getPayload().length())));
            });
        } catch (PcapNativeException | InterruptedException | NotOpenException e) {
            log.error("Error on capture()", e);
        }

        //for testing UI on windows
//        networks.add(new WirelessNetworkInfo());
    }

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
}
