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
    ASSET_DIALOG {
        @Override
        public String getFilePath() {
            return "/fxml/dialog/asset_dialog.fxml";
        }
    };

    public abstract String getFilePath();

}
