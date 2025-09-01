package com.jakubdeniziak.financialtracker.ui.loader.layout;

public enum View {

    DASHBOARD {
        @Override
        public String getFilePath() {
            return "/fxml/views/dashboard.fxml";
        }
    },
    ASSETS {
        @Override
        public String getFilePath() {
            return "/fxml/views/assets.fxml";
        }
    },
    EXPENSES {
        @Override
        public String getFilePath() {
            return "/fxml/views/expenses.fxml";
        }
    },
    ASSET_DIALOG {
        @Override
        public String getFilePath() {
            return "/fxml/dialog/asset_dialog.fxml";
        }
    },
    EXPENSE_DIALOG {
        @Override
        public String getFilePath() {
            return "/fxml/dialog/expense.fxml";
        }
    };

    public abstract String getFilePath();

}
