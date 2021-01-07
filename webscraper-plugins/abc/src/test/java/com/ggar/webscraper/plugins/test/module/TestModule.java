package com.ggar.webscraper.plugins.test.module;

import com.ggar.webscraper.interfaces.UrlTemplate;
import com.ggar.webscraper.plugins.abc.Abc;
import com.ggar.webscraper.plugins.abc.model.Keyword;
import com.ggar.webscraper.plugins.abc.model.Templates;
import com.ggar.webscraper.plugins.test.model.FactoryHolder;
import com.ggar.webscraper.plugins.test.model.annotation.TestAnnotation;
import com.ggar.webscraper.plugins.test.model.interfaces.TestFactory;
import com.ggar.webscraper.plugins.test.service.TestIterator;
import com.google.inject.PrivateModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.jsoup.nodes.Document;

import java.util.Iterator;

public class TestModule extends PrivateModule {

    @Override
    protected void configure() {

//        bind(new TypeLiteral<DownloaderService<Document>>(){}).to(JsoupDownloadService.class).in(Singleton.class);
        bind(UrlTemplate.class).annotatedWith(Keyword.class).toInstance(Templates.KEYWORD);

        install(new FactoryModuleBuilder()
                .implement(new TypeLiteral<Iterator<Document>>(){}, TestAnnotation.class, TestIterator.class)
                .build(TestFactory.class)
        );

        bind(Abc.class);
        bind(FactoryHolder.class);

        expose(Abc.class);
        expose(FactoryHolder.class);
    }

}
