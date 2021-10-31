package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pcap4j.core.BpfProgram;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        PcapNetworkInterface nif = Pcaps.getDevByName("wlan0mon");
        int snapLen = 65536;
        int timeout = 10;
        PcapHandle handle = nif.openLive(snapLen, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, timeout);
        BpfProgram bpfProgram = handle.compileFilter("wlan type mgt subtype beacon", BpfProgram.BpfCompileMode.NONOPTIMIZE, PcapHandle.PCAP_NETMASK_UNKNOWN);
        handle.setFilter(bpfProgram);

        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        // handle.loop(0, (Packet packet) -> {
        //     System.out.println("header len: " + packet.getHeader().length() + "; payload len:" + packet.getPayload().length());
        // });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
