package com.jakubdeniziak.financialtracker.ui.manager;

import com.jakubdeniziak.financialtracker.ui.loader.layout.LayoutLoader;
import com.jakubdeniziak.financialtracker.ui.loader.layout.View;
import com.jakubdeniziak.financialtracker.ui.loader.style.StyleLoader;
import com.jakubdeniziak.financialtracker.ui.loader.style.StyleSheet;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultStageManager implements StageManager {

    private final LayoutLoader layoutLoader;
    private final StyleLoader styleLoader;

    @Value("${application.title}")
    private String title;
    @Value("${application.width}")
    private double width;
    @Value("${application.height}")
    private double height;

    private Stage primaryStage;

    public DefaultStageManager init(Stage primaryStage) {
        this.primaryStage = primaryStage;
        return this;
    }

    @Override
    public void setupScene(View view) {
        Parent rootNode = layoutLoader.load(view);
        String stylesheet = styleLoader.load(StyleSheet.MAIN);
        Scene scene = new Scene(rootNode, width, height);
        scene.getStylesheets().add(stylesheet);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void switchScene(View view) {
        Parent rootNode = layoutLoader.load(view);
        primaryStage.getScene().setRoot(rootNode);
        primaryStage.show();
    }

}
