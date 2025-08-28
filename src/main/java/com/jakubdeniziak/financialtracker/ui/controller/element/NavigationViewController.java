package com.jakubdeniziak.financialtracker.ui.controller.element;

import com.jakubdeniziak.financialtracker.ui.loader.layout.View;
import com.jakubdeniziak.financialtracker.ui.manager.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class NavigationViewController implements Initializable {

    private final StageManager stageManager;

    @FXML private ComboBox<String> currencyChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: get values from actual services
        currencyChooser.getItems().addAll(List.of("USD", "EUR", "GBP", "PLN"));
        currencyChooser.setValue("USD");
    }

    @FXML
    public void openDashboard(ActionEvent actionEvent) {
        stageManager.switchScene(View.DASHBOARD);
    }

    @FXML
    public void openAssets(ActionEvent actionEvent) {
        stageManager.switchScene(View.ASSETS);
    }

    public void registerCurrencyObserver(Runnable observer) {
        currencyChooser.valueProperty().addListener((observable, oldValue, newValue) -> observer.run());
    }

    public String getChosenCurrency() {
        return currencyChooser.getValue();
    }

}
