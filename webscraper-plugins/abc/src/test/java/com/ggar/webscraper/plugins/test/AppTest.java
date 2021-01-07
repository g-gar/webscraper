package com.ggar.webscraper.plugins.test;

import com.ggar.webscraper.BaseModule;
import com.ggar.webscraper.plugins.abc.Abc;
import com.ggar.webscraper.plugins.abc.Module;
import com.ggar.webscraper.plugins.abc.command.CommandFactory;
import com.ggar.webscraper.plugins.abc.model.Article;
import com.ggar.webscraper.plugins.abc.model.FindArticleNodesFromListing;
import com.ggar.webscraper.plugins.test.model.FactoryHolder;
import com.ggar.webscraper.plugins.test.module.TestModule;
import com.google.inject.Injector;
import lombok.SneakyThrows;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private BaseModule module;
    private Injector injector;

    @Before
    public void configure() {
        this.module = new BaseModule(new Module());
        this.injector = this.module.getInjector();
    }

    @Test
    public void getsSingleArticle() throws MalformedURLException {
        String url = "https://www.abc.es/internacional/abci-iran-captura-petrolero-corea-medio-tensiones-entre-paises-202101041606_noticia.html";
        Abc abc = module.getInjector().getInstance(Abc.class);
        Article article = abc.single(new URL(url));
        assertNotNull(article);
    }

    @Test
    public void getByKeyword() {
        String keyword = "españa+vaciada";
        Abc abc = module.getInjector().getInstance(Abc.class);
        Collection<Article> articles = abc.keyword(keyword);
        assertTrue(articles.size() > 0);
    }

    @SneakyThrows
    @Test
    public void injectsFactory() {
        String keyword = "españa+vaciada";
        String url = "https://www.abc.es/internacional/abci-iran-captura-petrolero-corea-medio-tensiones-entre-paises-202101041606_noticia.html";
        BaseModule module = new BaseModule(new TestModule());
        FactoryHolder holder = module.getInjector().getInstance(FactoryHolder.class);
        Iterator<Document> kiterator = holder.getKeywordIterator(keyword);
        Iterator<Document> titerator = holder.getTestIterator(new URL(url));
        assertNotNull(kiterator);
        assertNotNull(titerator);
    }

    @SneakyThrows
    @Test
    public void testIfRetrievesArticlesFromListing() {
        URL url = new URL("https://www.abc.es/hemeroteca/resultados-busqueda-avanzada/noticia/pagina-1?tod=españa+vaciada&nres=20");
        CommandFactory factory = injector.getInstance(CommandFactory.class);

        Document document = factory.getSingle(url).execute();
        Elements elements = factory.filterNodes(document, new FindArticleNodesFromListing()).execute();
        assertNotNull(document);
        assertTrue(elements.size() == 20);
    }

}
