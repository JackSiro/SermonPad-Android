package com.jackson_siro.sermonpad.tools;

public class AppListNote {
    private String texts;
    private String dates;

    public AppListNote(String texts, String dates) {
        this.texts = texts;
        this.dates = dates;
    }

    public String getTexts() {
        return texts;
    }

    public String getDates() {
        return dates;
    }
}
