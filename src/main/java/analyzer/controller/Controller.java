package analyzer.controller;

import java.util.concurrent.Executors;

import analyzer.model.WirelessNetworkInfo;
import analyzer.pcap.PacketCaptureService;
import analyzer.util.Observer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller implements Observer {

    @FXML
    private TableView<WirelessNetworkInfo> tableView;

    @FXML
    private TableColumn<WirelessNetworkInfo, Image> iconColumn;

    private final PacketCaptureService pCapService = new PacketCaptureService();

    @FXML
    private void initialize() {
        pCapService.registerObserver(this);
        bindIconColumn();
        Platform.runLater(() -> tableView.setItems(FXCollections.observableArrayList(pCapService.getNetworks().values())));
        Executors.newSingleThreadExecutor().execute(pCapService::capture);
    }

    @Override
    public void updateView() {
        tableView.setItems(FXCollections.observableArrayList(pCapService.getNetworks().values()));
    }

    private void bindIconColumn() {
        iconColumn.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(50);
            imageview.setFitWidth(50);

            //Set up the Table
            TableCell<WirelessNetworkInfo, Image> cell = new TableCell<WirelessNetworkInfo, Image>() {
                public void updateItem(Image item, boolean empty) {
                    if (empty || item == null) {
                        imageview.setImage(null);
                    } else {
                        imageview.setImage(item);
                    }
                }
            };
            // Attach the imageview to the cell
            cell.setGraphic(imageview);
            return cell;
        });
        iconColumn.setCellValueFactory(new PropertyValueFactory<>("icon"));
    }
}
