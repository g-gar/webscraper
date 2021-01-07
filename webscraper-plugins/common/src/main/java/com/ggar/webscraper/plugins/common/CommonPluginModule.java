package com.ggar.webscraper.plugins.common;

import com.ggar.webscraper.interfaces.DownloadRegistry;
import com.ggar.webscraper.interfaces.DownloaderService;
import com.ggar.webscraper.plugins.common.command.FilterNodesFromDocument;
import com.ggar.webscraper.plugins.common.command.GenerateIterator;
import com.ggar.webscraper.plugins.common.command.GetMultipleDocuments;
import com.ggar.webscraper.plugins.common.command.GetSingleDocument;
import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.ggar.webscraper.plugins.common.interfaces.CommandFactory;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.Iterator;

public class CommonPluginModule extends PrivateModule {
	@Override
	protected void configure() {

		requireBinding(new Key<DownloadRegistry<Document>>(){});
		requireBinding(new Key<DownloaderService<Document>>(){});

		install(new FactoryModuleBuilder()
			.implement(new TypeLiteral<Command<Document>>(){}, GetSingleDocument.class)
			.implement(new TypeLiteral<Command<Collection<Document>>>(){}, GetMultipleDocuments.class)
			.implement(new TypeLiteral<Command<Iterator<Document>>>(){}, GenerateIterator.class)
			.implement(new TypeLiteral<Command<Elements>>(){}, FilterNodesFromDocument.class)
			.build(CommandFactory.class)
		);
		expose(CommandFactory.class);
	}
}
