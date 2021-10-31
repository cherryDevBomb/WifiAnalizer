package analyzer.controller;

import analyzer.model.WirelessNetworkInfo;
import analyzer.service.PacketCaptureService;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.concurrent.Executors;

public class Controller {

    @FXML private TableView<WirelessNetworkInfo> tableView;

    private final PacketCaptureService pCapService = new PacketCaptureService();

    @FXML private void initialize() {
        Executors.newSingleThreadExecutor().execute(pCapService::capture);
        Platform.runLater(() -> tableView.setItems(new ObservableListWrapper<>(pCapService.getNetworks())));
    }

}
