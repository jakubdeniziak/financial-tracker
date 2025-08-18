package com.jakubdeniziak.financialtracker.ui.loader.layout;

public enum View {

    DASHBOARD {
        @Override
        public String getFilePath() {
            return "/fxml/dashboard.fxml";
        }
    };

    public abstract String getFilePath();

}
