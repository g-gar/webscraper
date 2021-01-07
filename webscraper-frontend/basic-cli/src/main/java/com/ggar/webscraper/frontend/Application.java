package com.ggar.webscraper.frontend;

import com.ggar.webscraper.plugins.abc.Abc;
import com.ggar.webscraper.plugins.abc.model.Article;
import com.ggar.webscraper.services.ThreadPoolExecutorService;
import com.google.inject.Injector;
import lombok.SneakyThrows;

import java.net.URL;

public class Application {

	private final Module module;
	private final Injector injector;

	public Application(Module module) {
		this.module = module;
		this.injector = module.getInjector();
	}

	public static void main(String[] args) {
		Application app = new Application(new Module());
		app.start();
		app.stop();
	}

	@SneakyThrows
	public void start() {
		Abc abc = injector.getInstance(Abc.class);
		Article article = abc.single(new URL("https://www.abc.es/internacional/abci-iran-captura-petrolero-corea-medio-tensiones-entre-paises-202101041606_noticia.html"));

		int a = 0;
	}

	public void stop() {
		ThreadPoolExecutorService service = injector.getInstance(ThreadPoolExecutorService.class);
		service.stop();
	}
}
