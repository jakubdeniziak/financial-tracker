package com.jakubdeniziak.financialtracker.ui.manager;

import com.jakubdeniziak.financialtracker.ui.loader.layout.View;
import javafx.stage.Stage;

public interface StageManager {

    StageManager init(Stage primaryStage);
    void setupScene(View view);
    void switchScene(View view);

}
