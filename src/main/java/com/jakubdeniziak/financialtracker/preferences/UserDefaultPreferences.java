package com.jakubdeniziak.financialtracker.preferences;

import com.jakubdeniziak.financialtracker.Main;
import org.springframework.stereotype.Component;

import java.util.prefs.Preferences;

@Component
public class UserDefaultPreferences implements UserPreferences {

    private static final String PREFERRED_CURRENCY = "preferred_currency";
    private static final String DEFAULT_CURRENCY = "USD";

    @Override
    public String getPreferredCurrency() {
        Preferences preferences = Preferences.userNodeForPackage(Main.class);
        return preferences.get(PREFERRED_CURRENCY, DEFAULT_CURRENCY);
    }

    @Override
    public void setPreferredCurrency(String currency) {
        Preferences preferences = Preferences.userNodeForPackage(Main.class);
        preferences.put(PREFERRED_CURRENCY, currency);
    }

}
