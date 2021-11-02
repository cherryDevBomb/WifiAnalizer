package analyzer.controller;

import analyzer.model.WirelessNetworkInfo;
import analyzer.service.PacketCaptureService;
import javafx.collections.FXCollections;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.concurrent.Executors;

public class Controller {

    @FXML private TableView<WirelessNetworkInfo> tableView;

    private final PacketCaptureService pCapService = new PacketCaptureService();

    @FXML private void initialize() {
        Platform.runLater(() -> tableView.setItems(FXCollections.observableArrayList​(pCapService.getNetworks().values())));
        Executors.newSingleThreadExecutor().execute(pCapService::capture);
    }

}
