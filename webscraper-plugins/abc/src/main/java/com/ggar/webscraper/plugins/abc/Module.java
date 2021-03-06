package com.ggar.webscraper.plugins.abc;

import com.ggar.webscraper.interfaces.DownloadRegistry;
import com.ggar.webscraper.interfaces.HttpService;
import com.ggar.webscraper.interfaces.ExecutorService;
import com.ggar.webscraper.interfaces.LoggerService;
import com.ggar.webscraper.plugins.abc.command.CommandFactory;
import com.ggar.webscraper.plugins.abc.command.ConvertDocumentToArticle;
import com.ggar.webscraper.plugins.abc.model.Article;
import com.ggar.webscraper.plugins.common.CommonPluginModule;
import com.ggar.webscraper.plugins.common.interfaces.Command;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.util.function.Function;

public class Module extends PrivateModule {

    private final Function<Connection, Connection>[] interceptors;

    public Module(Function<Connection, Connection>...  interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    protected void configure() {

        requireBinding(LoggerService.class);
        requireBinding(ExecutorService.class);

//        install(new JsoupModule());
        requireBinding(new Key<DownloadRegistry<Document>>(){});
        requireBinding(new Key<HttpService<Document>>(){});

        install(new CommonPluginModule());
//        requireBinding(com.ggar.webscraper.plugins.common.interfaces.CommandFactory.class);

//        Function<Connection, Connection>[] interceptors = new Function[this.interceptors.size()];
//        bind(new TypeLiteral<Function<Connection, Connection>[]>(){}).toInstance(this.interceptors.toArray(interceptors));
        bind(new TypeLiteral<Function<Connection, Connection>[]>(){}).toInstance(this.interceptors);

        install(new FactoryModuleBuilder()
            .implement(new TypeLiteral<Command<Article>>(){}, ConvertDocumentToArticle.class)
            .build(CommandFactory.class)
        );
//        expose(CommandFactory.class);

    }

}
