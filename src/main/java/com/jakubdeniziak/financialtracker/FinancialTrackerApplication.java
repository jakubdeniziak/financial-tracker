package com.jakubdeniziak.financialtracker;

import com.jakubdeniziak.financialtracker.ui.loader.layout.View;
import com.jakubdeniziak.financialtracker.ui.manager.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class FinancialTrackerApplication extends Application {

    private ConfigurableApplicationContext applicationContext;
    private Stage primaryStage;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(Main.class).run();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        applicationContext.getBean(StageManager.class)
                .init(primaryStage)
                .setupScene(View.DASHBOARD);
    }

    @Override
    public void stop() {
        applicationContext.close();
        primaryStage.close();
    }

}
