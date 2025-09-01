package com.jakubdeniziak.financialtracker.ui.controller.dialog;

import com.jakubdeniziak.financialtracker.domain.Expense;
import com.jakubdeniziak.financialtracker.ui.loader.layout.LayoutLoader;
import com.jakubdeniziak.financialtracker.ui.loader.layout.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
@RequiredArgsConstructor
public class ExpenseDialogController implements Initializable {

    private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    private final LayoutLoader layoutLoader;

    @FXML private DatePicker monthPicker;
    @FXML private TextField amountField;
    @FXML private ButtonType addButton;
    @FXML private ButtonType cancelButton;

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        monthPicker.setConverter(createYearMonthConverter());
    }

    public Optional<Expense> showDialogAndWait(String title) {
        DialogPane dialogPane = (DialogPane) layoutLoader.load(View.EXPENSE_DIALOG);
        Dialog<Expense> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setDialogPane(dialogPane);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(addButton)) {
                return Expense.builder()
                        .yearMonth(YearMonth.from(monthPicker.getValue()))
                        .amount(new BigDecimal(amountField.getText()))
                        .build();
            }
            return null;
        });
        return dialog.showAndWait();
    }

    public void setExpense(Expense expense) {
        Platform.runLater(() -> {
           monthPicker.setValue(expense.getYearMonth().atDay(1));
           amountField.setText(String.valueOf(expense.getAmount()));
        });
    }

    private StringConverter<LocalDate> createYearMonthConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? date.format(YEAR_MONTH_FORMATTER) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? YearMonth.parse(string, YEAR_MONTH_FORMATTER).atDay(1) : null;
            }
        };
    }

}
