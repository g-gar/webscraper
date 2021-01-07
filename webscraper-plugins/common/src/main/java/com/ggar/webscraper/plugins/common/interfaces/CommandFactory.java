package com.ggar.webscraper.plugins.common.interfaces;

import com.ggar.webscraper.interfaces.UrlTemplate;
import com.ggar.webscraper.plugins.common.command.FilterNodesFromDocument;
import com.ggar.webscraper.plugins.common.command.GenerateIterator;
import com.ggar.webscraper.plugins.common.command.GetMultipleDocuments;
import com.ggar.webscraper.plugins.common.command.GetSingleDocument;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.Iterator;
import java.util.function.Function;

//TODO: Add a generic type to this factory in order to enable working with more com.ggar.webscraper.interfaces.DownloaderService than just com.ggar.webscraper.interfaces.DownloaderService<org.jsoup.nodes.Document>
public interface CommandFactory {

    public GetSingleDocument getSingle(URL url);
    public GenerateIterator getIterator(UrlTemplate template, Integer offset, String... replacements);
    public GetMultipleDocuments getMultiple(Iterator<Document> iterator, Function<Document, Elements>... filters);
    public FilterNodesFromDocument filterNodes(Document document, Function<Document, Elements>... functions);

}
