package analyzer.controller;

import analyzer.model.WirelessNetworkInfo;
import analyzer.observer.Observer;
import analyzer.service.PacketCaptureService;
import com.sun.javafx.collections.ObservableListWrapper;
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

        observableList = new ObservableListWrapper<>(new ArrayList<>(pCapService.getNetworks().values()));
        Platform.runLater(() -> tableView.setItems(observableList));
        Executors.newSingleThreadExecutor().execute(pCapService::capture);
    }

    @Override
    public void updateView() {
        observableList.setAll(pCapService.getNetworks().values());
    }
}
