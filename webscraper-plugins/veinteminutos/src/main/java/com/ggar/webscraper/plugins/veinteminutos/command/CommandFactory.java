package com.ggar.webscraper.plugins.veinteminutos.command;

import org.jsoup.nodes.Document;

public interface CommandFactory extends com.ggar.webscraper.plugins.common.interfaces.CommandFactory {

    ConvertDocumentToArticle convert(Document document);

}
