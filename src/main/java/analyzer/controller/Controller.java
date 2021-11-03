package analyzer.controller;

import analyzer.model.WirelessNetworkInfo;
import analyzer.util.Observer;
import analyzer.pcap.PacketCaptureService;
import javafx.collections.FXCollections;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class Controller implements Observer {

    @FXML private TableView<WirelessNetworkInfo> tableView;

    private ObservableList<WirelessNetworkInfo> observableList;

    private final PacketCaptureService pCapService = new PacketCaptureService();

    @FXML private void initialize() {
        pCapService.registerObserver(this);
        Platform.runLater(() -> tableView.setItems(FXCollections.observableArrayList​(pCapService.getNetworks().values())));
        Executors.newSingleThreadExecutor().execute(pCapService::capture);
    }

    @Override
    public void updateView() {
        tableView.setItems(FXCollections.observableArrayList​(pCapService.getNetworks().values()));
    }
}
