package com.jakubdeniziak.financialtracker.ui.loader.layout;

public enum View {

    DASHBOARD {
        @Override
        public String getFilePath() {
            return "/fxml/views/dashboard.fxml";
        }
    };

    public abstract String getFilePath();

}
