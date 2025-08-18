package com.jakubdeniziak.financialtracker.ui.loader.style;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CssLoader implements StyleLoader {

    @Override
    public String load(StyleSheet styleSheet) {
        return Objects.requireNonNull(getClass().getResource(styleSheet.getFilePath())).toExternalForm();
    }

}
