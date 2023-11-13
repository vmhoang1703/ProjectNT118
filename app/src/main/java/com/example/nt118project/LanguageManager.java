package com.example.nt118project;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {
    private static LanguageManager instance;
    private Locale currentLocale;

    private LanguageManager() {
        // Khởi tạo LanguageManager với ngôn ngữ mặc định
        currentLocale = Locale.getDefault();
    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    public void changeLanguage(Resources resources, String languageCode) {
        Locale newLocale = new Locale(languageCode);
        Locale.setDefault(newLocale);

        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.setLocale(newLocale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        currentLocale = newLocale;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }
}


