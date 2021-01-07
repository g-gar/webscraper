package com.ggar.webscraper.plugins.elpais.model;

import com.ggar.webscraper.interfaces.UrlTemplate;

import java.net.URL;

public enum Templates implements UrlTemplate {

    SECTION("https://www.elpais.com/**/%s/");

    private final String template;

    Templates(String template) {
        this.template = template;
    }

    @Override
    public String get(String...replacements) {
        String str = this.template;
        URL url = null;
        for (Object replacement : replacements) {
            str = str.replaceFirst("\\*\\*", replacement.toString());
        }
        return str;
    }

}
