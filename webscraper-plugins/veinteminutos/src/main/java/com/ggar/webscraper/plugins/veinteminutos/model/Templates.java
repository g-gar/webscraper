package com.ggar.webscraper.plugins.veinteminutos.model;

import com.ggar.webscraper.interfaces.UrlTemplate;

import java.net.URL;

public enum Templates implements UrlTemplate {

    CATEGORY("https://www.20minutos.es/**/%s/"),
    MINUTECA("https://www.20minutos.es/minuteca/**/%s/"),
    KEYWORD("https://www.20minutos.es/busqueda/%s/?q=**&sort_field=&category=&publishedAt[from]=&publishedAt[until]=&articleTypes[0]=default"),
    KEYWORD_WITH_SECTION("https://www.20minutos.es/busqueda/%s/?q=**&sort_field=&category=**&publishedAt[from]=&publishedAt[until]=&articleTypes[]=default");

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
