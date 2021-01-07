package com.ggar.webscraper.plugins.abc.model;

import com.ggar.webscraper.interfaces.UrlTemplate;

import java.net.URL;

public enum Templates implements UrlTemplate {

    KEYWORD("https://www.abc.es/hemeroteca/resultados-busqueda-avanzada/noticia/pagina-%s?tod=**&nres=20");

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
