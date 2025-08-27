package com.jakubdeniziak.financialtracker.ui.controller.views;

import com.jakubdeniziak.financialtracker.domain.Asset;
import com.jakubdeniziak.financialtracker.service.AssetService;
import com.jakubdeniziak.financialtracker.ui.controller.dialog.AssetDialogController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class AssetsController implements Initializable {

    private final AssetService assetService;
    private final AssetDialogController assetDialogController;

    @FXML private TableView<Asset> assetTable;
    @FXML private TableColumn<Asset, String> nameColumn;
    @FXML private TableColumn<Asset, String> symbolColumn;
    @FXML private TableColumn<Asset, String> categoryColumn;
    @FXML private TableColumn<Asset, String> typeColumn;
    @FXML private TableColumn<Asset, String> currencyColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
        assetTable.setItems(FXCollections.observableArrayList(assetService.readAll()));
    }

    @FXML
    public void onAdd() {
        Optional<Asset> created = assetDialogController.showDialogAndWait("Add asset");
        created.ifPresent(asset -> {
            assetService.save(created.get());
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
        Optional<Asset> edited = assetDialogController.showDialogAndWait("Edit asset");
        edited.ifPresent(asset -> {
            assetService.update(selected.getId(), edited.get());
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

}
