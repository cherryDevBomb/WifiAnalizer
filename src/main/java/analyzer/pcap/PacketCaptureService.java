package analyzer.pcap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

import analyzer.model.WirelessNetworkInfo;
import analyzer.parser.PacketParser;
import analyzer.util.Observable;
import analyzer.util.Observer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

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
        setNpcapSystemProperty();
        PcapHandle handle = null;
        try {
            PcapNetworkInterface networkInterface = Pcaps.getDevByName(WLAN0MON);
            if (networkInterface != null) {
                handle = networkInterface.openLive(SNAP_LEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, TIMEOUT);
                BpfProgram bpfProgram = handle.compileFilter(BFP_EXPRESSION, BpfProgram.BpfCompileMode.NONOPTIMIZE, PcapHandle.PCAP_NETMASK_UNKNOWN);
                handle.setFilter(bpfProgram);
            }
        } catch (PcapNativeException | NotOpenException e) {
            log.error("Error on initHandle()", e);
        }

        return handle;
    }

    public void capture() {
        PcapHandle handle = initHandle();

        if (handle == null) {
            return;
        }

        try {
            handle.loop(0, (Packet packet) -> {
                WirelessNetworkInfo wirelessNetworkInfo = PacketParser.parseFrame(packet.getHeader().getRawData(), packet.getPayload().getRawData());
                if (wirelessNetworkInfo.getMAC() != null) {
                    networks.put(wirelessNetworkInfo.getMAC(), wirelessNetworkInfo);
                }
                observers.forEach(Observer::updateView);
            });
        } catch (PcapNativeException | InterruptedException | NotOpenException e) {
            log.error("Error on capture()", e);
        }
    }

    private void setNpcapSystemProperty() {
        String prop = System.getProperty("jna.library.path");
        if (prop == null || prop.isEmpty()) {
            prop = "C:/Windows/System32/Npcap";
        } else {
            prop += ";C:/Windows/System32/Npcap";
        }
        System.setProperty("jna.library.path", prop);
    }
}
