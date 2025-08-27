package com.jakubdeniziak.financialtracker.ui.controller.dialog;

import com.jakubdeniziak.financialtracker.domain.Asset;
import com.jakubdeniziak.financialtracker.entity.AssetCategory;
import com.jakubdeniziak.financialtracker.entity.AssetType;
import com.jakubdeniziak.financialtracker.ui.loader.layout.LayoutLoader;
import com.jakubdeniziak.financialtracker.ui.loader.layout.View;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class AssetDialogController implements Initializable {

    private final LayoutLoader layoutLoader;

    @FXML private TextField nameField;
    @FXML private TextField symbolField;
    @FXML private ComboBox<AssetCategory> categoryBox;
    @FXML private ComboBox<AssetType> typeBox;
    @FXML private TextField currencyField;
    @FXML private ButtonType addButton;
    @FXML private ButtonType cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryBox.setItems(FXCollections.observableArrayList(AssetCategory.values()));
        typeBox.setItems(FXCollections.observableArrayList(AssetType.values()));
    }

    public Optional<Asset> showDialogAndWait(String title) {
        DialogPane dialogPane = (DialogPane) layoutLoader.load(View.ASSET_DIALOG);
        Dialog<Asset> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setDialogPane(dialogPane);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(addButton)) {
                return Asset.builder()
                        .name(nameField.getText())
                        .symbol(symbolField.getText())
                        .category(categoryBox.getValue())
                        .type(typeBox.getValue())
                        .currency(currencyField.getText())
                        .build();
            }
            return null;
        });
        return dialog.showAndWait();
    }

    public void setAsset(Asset asset) {
        Platform.runLater(() -> {
            nameField.setText(asset.getName());
            symbolField.setText(asset.getSymbol());
            categoryBox.setValue(asset.getCategory());
            typeBox.setValue(asset.getType());
            currencyField.setText(asset.getCurrency());
        });
    }

}
