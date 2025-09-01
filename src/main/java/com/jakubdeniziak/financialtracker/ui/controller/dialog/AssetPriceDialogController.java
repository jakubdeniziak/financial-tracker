package com.jakubdeniziak.financialtracker.ui.controller.dialog;

import com.jakubdeniziak.financialtracker.domain.Asset;
import com.jakubdeniziak.financialtracker.service.AssetPriceService;
import com.jakubdeniziak.financialtracker.ui.loader.layout.LayoutLoader;
import com.jakubdeniziak.financialtracker.ui.loader.layout.View;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class AssetPriceDialogController {

    private final AssetPriceService assetPriceService;
    private final LayoutLoader layoutLoader;

    @FXML private Label nameLabel;
    @FXML private Label currentPriceLabel;
    @FXML private LineChart<String, Double> priceChart;

    public void showDialogAndWait(String title, Asset asset) {
        DialogPane dialogPane = (DialogPane) layoutLoader.load(View.ASSET_PRICE_DIALOG);
        Dialog<Void> dialog = new Dialog<>();
        initializeElements(asset);
        dialog.setTitle(title);
        dialog.setDialogPane(dialogPane);
        dialog.showAndWait();
    }

    private void initializeElements(Asset asset) {
        nameLabel.setText(asset.getName());
        initializeCurrentPriceLabel(asset);
        initializePriceChart(asset.getId());
    }

    private void initializeCurrentPriceLabel(Asset asset) {
        String currentPrice = assetPriceService.readLatestPriceForAsset(asset.getId())
                .map(BigDecimal::toString)
                .orElse("?");
        String labelText = String.format("Current Price: %s %s", currentPrice, asset.getCurrency());
        currentPriceLabel.setText(labelText);
    }

    private void initializePriceChart(long assetId) {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Price history");
        assetPriceService.readAllForAsset(assetId).forEach(p -> series.getData().add(
                new XYChart.Data<>(
                        p.getRecordedAt().toLocalDate().toString(),
                        p.getPrice().doubleValue()
                )
        ));
        priceChart.getData().clear();
        priceChart.getData().add(series);
    }

}
