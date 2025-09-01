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
            return "/fxml/dialogs/asset.fxml";
        }
    },
    ASSET_PRICE_DIALOG {
        @Override
        public String getFilePath() {
            return "/fxml/dialogs/asset_price.fxml";
        }
    },
    EXPENSE_DIALOG {
        @Override
        public String getFilePath() {
            return "/fxml/dialogs/expense.fxml";
        }
    };

    public abstract String getFilePath();

}
