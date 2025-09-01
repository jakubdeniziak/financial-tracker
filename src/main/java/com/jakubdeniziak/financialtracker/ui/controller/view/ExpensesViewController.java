package com.jakubdeniziak.financialtracker.ui.controller.view;

import com.jakubdeniziak.financialtracker.domain.Expense;
import com.jakubdeniziak.financialtracker.service.ExpenseService;
import com.jakubdeniziak.financialtracker.ui.controller.dialog.ExpenseDialogController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class ExpensesViewController implements Initializable {

    private final ExpenseService expenseService;
    private final ExpenseDialogController expenseDialogController;

    @FXML private TableView<Expense> expenseTable;
    @FXML private TableColumn<Expense, String> monthYearColumn;
    @FXML private TableColumn<Expense, String> amountColumn;

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        monthYearColumn.setCellValueFactory(new PropertyValueFactory<>("yearMonth"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        expenseTable.setItems(FXCollections.observableArrayList(expenseService.readAll()));
    }

    @FXML
    public void onAdd() {
        expenseDialogController.showDialogAndWait("Add expense").ifPresent(expense -> {
            expenseService.create(expense);
            expenseTable.setItems(FXCollections.observableArrayList(expenseService.readAll()));
        });
    }

    @FXML
    public void onEdit() {
        Expense selected = expenseTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        expenseDialogController.setExpense(selected);
        expenseDialogController.showDialogAndWait("Edit expense").ifPresent(expense -> {
            expenseService.update(selected.getId(), expense);
            expenseTable.setItems(FXCollections.observableArrayList(expenseService.readAll()));
        });
    }

    @FXML
    public void onDelete() {
        Expense selected = expenseTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        expenseService.delete(selected.getId());
        expenseTable.getItems().remove(selected);
        expenseTable.getSelectionModel().clearSelection();
    }

}
