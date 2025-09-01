package com.jakubdeniziak.financialtracker.ui.controller.dialog;

import com.jakubdeniziak.financialtracker.domain.Asset;
import com.jakubdeniziak.financialtracker.domain.Transaction;
import com.jakubdeniziak.financialtracker.entity.TransactionType;
import com.jakubdeniziak.financialtracker.service.AssetService;
import com.jakubdeniziak.financialtracker.ui.loader.layout.LayoutLoader;
import com.jakubdeniziak.financialtracker.ui.loader.layout.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class TransactionDialogController implements Initializable {

    private final LayoutLoader layoutLoader;
    private final AssetService assetService;

    @FXML private ComboBox<TransactionType> typeBox;
    @FXML private TextField quantityField;
    @FXML private TextField pricePerUnitField;
    @FXML private TextField currencyField;
    @FXML private DatePicker executedAtDatePicker;
    @FXML private Spinner<Integer> executedAtHourSpinner;
    @FXML private Spinner<Integer> executedAtMinuteSpinner;
    @FXML private TextField notesField;
    @FXML private ComboBox<Asset> assetBox;
    @FXML private ButtonType addButton;
    @FXML private ButtonType cancelButton;

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        typeBox.getItems().addAll(TransactionType.values());
        executedAtHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        executedAtMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 1));
        assetBox.getItems().addAll(assetService.readAll());
    }

    public Optional<Transaction> showDialogAndWait(String title) {
        DialogPane dialogPane = (DialogPane) layoutLoader.load(View.TRANSACTION_DIALOG);
        Dialog<Transaction> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setDialogPane(dialogPane);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(addButton)) {
                return Transaction.builder()
                        .type(typeBox.getValue())
                        .quantity(new BigDecimal(quantityField.getText()))
                        .pricePerUnit(new BigDecimal(pricePerUnitField.getText()))
                        .currency(currencyField.getText())
                        .executedAt(getExecutedAt())
                        .notes(notesField.getText())
                        .asset(assetBox.getValue())
                        .build();
            }
            return null;
        });
        return dialog.showAndWait();
    }

    public void setTransaction(Transaction transaction) {
        Platform.runLater(() -> {
            typeBox.getSelectionModel().select(transaction.getType());
            quantityField.setText(transaction.getQuantity().toString());
            pricePerUnitField.setText(transaction.getPricePerUnit().toString());
            currencyField.setText(transaction.getCurrency());
            executedAtDatePicker.setValue(transaction.getExecutedAt().toLocalDate());
            executedAtHourSpinner.getValueFactory().setValue(transaction.getExecutedAt().getHour());
            executedAtMinuteSpinner.getValueFactory().setValue(transaction.getExecutedAt().getMinute());
            notesField.setText(transaction.getNotes());
            assetBox.getSelectionModel().select(transaction.getAsset());
        });
    }

    private ZonedDateTime getExecutedAt() {
        return executedAtDatePicker.getValue()
                .atTime(executedAtHourSpinner.getValue(), executedAtMinuteSpinner.getValue())
                .atZone(ZoneId.systemDefault());
    }

}
