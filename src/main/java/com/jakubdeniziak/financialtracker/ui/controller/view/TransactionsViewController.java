package com.jakubdeniziak.financialtracker.ui.controller.view;

import com.jakubdeniziak.financialtracker.domain.Asset;
import com.jakubdeniziak.financialtracker.domain.Transaction;
import com.jakubdeniziak.financialtracker.entity.TransactionType;
import com.jakubdeniziak.financialtracker.service.TransactionService;
import com.jakubdeniziak.financialtracker.ui.controller.dialog.TransactionDialogController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class TransactionsViewController implements Initializable {

    private final TransactionService transactionService;
    private final TransactionDialogController transactionDialogController;

    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, TransactionType> typeColumn;
    @FXML private TableColumn<Transaction, BigDecimal> quantityColumn;
    @FXML private TableColumn<Transaction, BigDecimal> pricePerUnitColumn;
    @FXML private TableColumn<Transaction, ZonedDateTime> executedAtColumn;
    @FXML private TableColumn<Transaction, String> notesColumn;
    @FXML private TableColumn<Transaction, Asset> assetColumn;

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        pricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
        executedAtColumn.setCellValueFactory(new PropertyValueFactory<>("executedAt"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
        assetColumn.setCellValueFactory(new PropertyValueFactory<>("asset"));
        transactionTable.setItems(FXCollections.observableArrayList(transactionService.readAll()));
    }

    @FXML
    public void onAdd() {
        transactionDialogController.showDialogAndWait("Add transaction").ifPresent(asset -> {
            transactionService.create(asset);
            transactionTable.setItems(FXCollections.observableArrayList(transactionService.readAll()));
        });
    }

    @FXML
    public void onEdit() {
        Transaction selected = transactionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        transactionDialogController.setTransaction(selected);
        transactionDialogController.showDialogAndWait("Edit transaction").ifPresent(transaction -> {
            transactionService.update(selected.getId(), transaction);
            transactionTable.setItems(FXCollections.observableArrayList(transactionService.readAll()));
        });
    }

    @FXML
    public void onDelete() {
        Transaction selected = transactionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        transactionService.delete(selected.getId());
        transactionTable.getItems().remove(selected);
        transactionTable.getSelectionModel().clearSelection();
    }

}
