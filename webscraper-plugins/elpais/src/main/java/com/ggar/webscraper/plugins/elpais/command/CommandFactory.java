package com.ggar.webscraper.plugins.elpais.command;

import org.jsoup.nodes.Document;

public interface CommandFactory extends com.ggar.webscraper.plugins.common.interfaces.CommandFactory {

    ConvertDocumentToArticle convert(Document document);

}
