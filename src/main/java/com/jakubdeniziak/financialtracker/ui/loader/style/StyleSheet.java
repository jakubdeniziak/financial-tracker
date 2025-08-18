package com.jakubdeniziak.financialtracker.ui.loader.style;

public enum StyleSheet {

    MAIN {
        @Override
        public String getFilePath() {
            return "/css/main.css";
        }
    };

    public abstract String getFilePath();

}
