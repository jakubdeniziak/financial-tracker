package com.jakubdeniziak.financialtracker.ui.controller.dialog;

import com.jakubdeniziak.financialtracker.domain.Asset;
import com.jakubdeniziak.financialtracker.domain.AssetPrice;
import com.jakubdeniziak.financialtracker.service.AssetPriceService;
import com.jakubdeniziak.financialtracker.ui.loader.layout.LayoutLoader;
import com.jakubdeniziak.financialtracker.ui.loader.layout.View;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;

@Controller
@RequiredArgsConstructor
public class AssetPriceDialogController {

    private final AssetPriceService assetPriceService;
    private final LayoutLoader layoutLoader;

    @FXML private Label nameLabel;
    @FXML private Label latestPriceLabel;
    @FXML private LineChart<String, Double> priceChart;
    @FXML private DatePicker datePicker;
    @FXML private Spinner<Integer> hourSpinner;
    @FXML private Spinner<Integer> minuteSpinner;
    @FXML private Label priceLabel;
    @FXML private TextField priceField;

    private Asset asset;

    @FXML
    public void onAddPrice() {
        BigDecimal price = new BigDecimal(priceField.getText());
        ZonedDateTime recordedAt = datePicker.getValue()
                .atTime(hourSpinner.getValue(), minuteSpinner.getValue())
                .atZone(ZoneOffset.UTC);
        AssetPrice assetPrice = AssetPrice.builder()
                .asset(asset)
                .price(price)
                .recordedAt(recordedAt)
                .build();
        assetPriceService.create(assetPrice);
        clearElements();
    }

    public void showDialogAndWait(String title, Asset asset) {
        this.asset = asset;
        DialogPane dialogPane = (DialogPane) layoutLoader.load(View.ASSET_PRICE_DIALOG);
        Dialog<Void> dialog = new Dialog<>();
        initializeElements();
        dialog.setTitle(title);
        dialog.setDialogPane(dialogPane);
        dialog.showAndWait();
    }

    private void clearElements() {
        initializeElements();
        datePicker.setValue(null);
        priceField.setText("");
    }

    private void initializeElements() {
        nameLabel.setText(asset.getName());
        initializeLatestPriceLabel(asset);
        initializePriceChart(asset.getId());
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12));
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        priceLabel.setText(String.format("Price [%s]:", asset.getCurrency()));
    }

    private void initializeLatestPriceLabel(Asset asset) {
        String latestPrice = assetPriceService.readLatestPriceForAsset(asset.getId())
                .map(BigDecimal::toString)
                .orElse("?");
        String labelText = String.format("Latest price: %s %s", latestPrice, asset.getCurrency());
        latestPriceLabel.setText(labelText);
    }

    private void initializePriceChart(long assetId) {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Price history");
        assetPriceService.readAllForAsset(assetId).stream()
                .sorted(Comparator.comparing(AssetPrice::getRecordedAt))
                .forEach(p -> series.getData().add(new XYChart.Data<>(p.getRecordedAt().toLocalDate().toString(), p.getPrice().doubleValue())));
        priceChart.getData().clear();
        priceChart.getData().add(series);
    }

}
