package org.example.personalizedstudyplanner.context;

import java.util.Locale;

public class LocaleContext {
    private static Locale currentLocale = Locale.getDefault();

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static void setCurrentLocale(Locale locale) {
        currentLocale = locale;
    }
}
