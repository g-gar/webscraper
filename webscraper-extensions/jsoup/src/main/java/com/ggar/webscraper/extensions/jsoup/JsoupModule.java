package com.ggar.webscraper.extensions.jsoup;

import com.ggar.webscraper.extensions.jsoup.model.JsoupDownloadRegistry;
import com.ggar.webscraper.extensions.jsoup.model.JsoupDownloadService;
import com.ggar.webscraper.interfaces.DownloadRegistry;
import com.ggar.webscraper.interfaces.HttpService;
import com.google.inject.PrivateModule;
import com.google.inject.TypeLiteral;
import org.jsoup.nodes.Document;

public class JsoupModule extends PrivateModule {
	@Override
	protected void configure() {
		bind(new TypeLiteral<DownloadRegistry<Document>>(){}).to(JsoupDownloadRegistry.class);
		expose(new TypeLiteral<DownloadRegistry<Document>>(){});

		bind(new TypeLiteral<HttpService<Document>>(){}).to(JsoupDownloadService.class);
		expose(new TypeLiteral<HttpService<Document>>(){});
	}
}
