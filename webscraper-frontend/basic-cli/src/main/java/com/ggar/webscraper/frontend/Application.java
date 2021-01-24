package com.ggar.webscraper.frontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggar.webscraper.plugins.elpais.ElPais;
import com.ggar.webscraper.services.ThreadPoolExecutorService;
import com.google.inject.Injector;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

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
		String section = "tecnologia";
		int initialOffset = ElPais.INITIAL_INDEX;
		int maxOffset = ElPais.INITIAL_INDEX + 10;
		Collection<com.ggar.webscraper.plugins.elpais.model.Article> articles = injector.getInstance(ElPais.class).section(section, initialOffset, maxOffset);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(articles);
			String str = "C:/Users/ggarf/Desktop/UEM/UEM3/PROYECTO DE COMPUTACION I/2021-PC1/data/%_%s_%s-%s_%s.json";
			Path path = Paths.get(str.format("elpais", section, initialOffset, maxOffset, "20210124"));
			Files.write(path, json.getBytes(StandardCharsets.UTF_8));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		ThreadPoolExecutorService service = injector.getInstance(ThreadPoolExecutorService.class);
		service.stop();
	}
}
