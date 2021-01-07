package com.ggar.webscraper.plugins.abc.command;

import com.ggar.webscraper.plugins.common.command.GetSingleDocument;
import org.jsoup.nodes.Document;

import java.net.URL;

public interface CommandFactory extends com.ggar.webscraper.plugins.common.interfaces.CommandFactory {

    ConvertDocumentToArticle convert(Document document);

}
