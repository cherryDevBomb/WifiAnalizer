package analyzer.controller;

import java.util.concurrent.Executors;

import analyzer.model.WirelessNetworkInfo;
import analyzer.pcap.PacketCaptureService;
import analyzer.util.Observer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class Controller implements Observer {

    @FXML
    private TableView<WirelessNetworkInfo> tableView;

    private final PacketCaptureService pCapService = new PacketCaptureService();

    @FXML
    private void initialize() {
        pCapService.registerObserver(this);
        Platform.runLater(() -> tableView.setItems(FXCollections.observableArrayList(pCapService.getNetworks().values())));
        Executors.newSingleThreadExecutor().execute(pCapService::capture);
    }

    @Override
    public void updateView() {
        tableView.setItems(FXCollections.observableArrayList(pCapService.getNetworks().values()));
    }
}
