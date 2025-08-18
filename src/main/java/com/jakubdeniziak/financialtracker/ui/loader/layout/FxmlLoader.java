package com.jakubdeniziak.financialtracker.ui.loader.layout;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;

@Component
@RequiredArgsConstructor
public class FxmlLoader implements LayoutLoader {

    private final ApplicationContext context;

    @Override
    public Parent load(View view) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setLocation(getClass().getResource(view.getFilePath()));
        try {
            return loader.load();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

}
