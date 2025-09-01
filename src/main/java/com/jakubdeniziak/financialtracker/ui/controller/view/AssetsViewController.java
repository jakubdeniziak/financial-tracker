package com.jakubdeniziak.financialtracker.ui.controller.view;

import com.jakubdeniziak.financialtracker.domain.Asset;
import com.jakubdeniziak.financialtracker.service.AssetService;
import com.jakubdeniziak.financialtracker.ui.controller.dialog.AssetDialogController;
import com.jakubdeniziak.financialtracker.ui.controller.dialog.AssetPriceDialogController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class AssetsViewController implements Initializable {

    private final AssetService assetService;
    private final AssetDialogController assetDialogController;
    private final AssetPriceDialogController assetPriceDialogController;

    @FXML private TableView<Asset> assetTable;
    @FXML private TableColumn<Asset, String> nameColumn;
    @FXML private TableColumn<Asset, String> symbolColumn;
    @FXML private TableColumn<Asset, String> categoryColumn;
    @FXML private TableColumn<Asset, String> typeColumn;
    @FXML private TableColumn<Asset, String> currencyColumn;
    @FXML private TableColumn<Asset, Void> priceColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
        initializePrizeColumn();
        assetTable.setItems(FXCollections.observableArrayList(assetService.readAll()));
    }

    @FXML
    public void onAdd() {
        assetDialogController.showDialogAndWait("Add asset").ifPresent(asset -> {
            assetService.create(asset);
            assetTable.setItems(FXCollections.observableArrayList(assetService.readAll()));
        });
    }

    @FXML
    public void onEdit() {
        Asset selected = assetTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        assetDialogController.setAsset(selected);
        assetDialogController.showDialogAndWait("Edit asset").ifPresent(asset -> {
            assetService.update(selected.getId(), asset);
            assetTable.setItems(FXCollections.observableArrayList(assetService.readAll()));
        });
    }

    @FXML
    public void onDelete() {
        Asset selected = assetTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        assetService.delete(selected.getId());
        assetTable.getItems().remove(selected);
        assetTable.getSelectionModel().clearSelection();
    }

    private void initializePrizeColumn() {
        priceColumn.setCellFactory(col -> new TableCell<>() {
            private final Button viewPriceButton = new Button("View price");

            {
                viewPriceButton.setOnAction(event -> {
                    Asset asset = getTableView().getItems().get(getIndex());
                    assetPriceDialogController.showDialogAndWait("Asset price info", asset);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewPriceButton);
                }
            }
        });
    }

}
