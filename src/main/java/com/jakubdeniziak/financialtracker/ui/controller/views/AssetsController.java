package com.jakubdeniziak.financialtracker.ui.controller.views;

import com.jakubdeniziak.financialtracker.domain.Asset;
import com.jakubdeniziak.financialtracker.entity.AssetCategory;
import com.jakubdeniziak.financialtracker.entity.AssetType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AssetsController implements Initializable {

    private static final List<Asset> ASSETS = List.of(
            new Asset(1, "Apple Inc.", "AAPL", AssetCategory.STOCK, AssetType.STOCK_COMMON, "USD"),
            new Asset(2, "Google LLC", "GOOGL", AssetCategory.STOCK, AssetType.STOCK_COMMON, "USD"),
            new Asset(3, "Tesla Inc.", "TSLA", AssetCategory.STOCK, AssetType.STOCK_COMMON, "USD"),
            new Asset(4, "Bitcoin", "BTC", AssetCategory.CRYPTO, AssetType.CRYPTO_COIN_NORMAL, "EUR")
    );

    @FXML private TableView<Asset> assetTable;
    @FXML private TableColumn<Asset, String> nameColumn;
    @FXML private TableColumn<Asset, String> symbolColumn;
    @FXML private TableColumn<Asset, String> categoryColumn;
    @FXML private TableColumn<Asset, String> typeColumn;
    @FXML private TableColumn<Asset, String> currencyColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: get values from actual services
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
        assetTable.setItems(FXCollections.observableArrayList(ASSETS));
    }

}
