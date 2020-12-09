package com.ggar.webscraper.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.ggar.webscraper.core.AbstractArticle;
import com.ggar.webscraper.core.PluginOperations;
import com.ggar.webscraper.core.PluginParams;

public class Main {
	
	private final static Logger log = Logger.getGlobal();

	@SuppressWarnings({ "unused", "rawtypes" })
	public static void main(String[] args) throws Exception {
		App app = App.getInstance();
		File file = new File(args[1]);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
		for (PluginParams param : parseCsvInput(Paths.get(args[0]))) {
			log.info(String.format("Received %s\n", param));
			List<AbstractArticle> articles = app.execute(param).parallelStream().filter(e -> e!=null).map(e -> (AbstractArticle)e).collect(Collectors.toList());
			String line = null;
			for (AbstractArticle a : articles) {
//				log.info(a.toString());
				try {
					bw.write(String.format(
							"%s, %s, %s, %s, %s, %s, %s, %s, %s, %s\n", 
							a.getUrl(), 
							a.getPublishedDate(), 
							a.getModifiedDate(),
							String.format("\"%s\"", a.getTitle()),
							String.format("\"%s\"", a.getSummary()),
							String.format("\"%s\"", a.getContent()),
							a.getLanguage(),
							a.getSection() != null ? a.getSection().contains(":") ? a.getSection().split(":")[0] : a.getSection() : "",
							a.getTags() != null ? a.getTags().toString().replaceAll("\\[|\\]", "").replaceAll("\\,", "\\;"): "",
							a.getAuthors() != null ? a.getAuthors().toString().replaceAll("\\[|\\]", "").replaceAll("\\,", "\\;") : ""
						));
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(a);
				}
			}
			log.info(String.format("Finished processing request %s [got %s articles]\n", param, articles.size()));
		}
		bw.close();
	}

	public static List<PluginParams> parseCsvInput(Path path) {
		List<PluginParams> result = null;

		try {
			result = new ArrayList<PluginParams>();
			List<String> temp;
			PluginParams params = null;
			for (String line : Files.readAllLines(path)) {
				temp = Arrays.asList(line.trim().replaceAll("\\s*", "").split(","));
				switch (temp.get(0)) {
				case "20minutos":
					params = getVeinteMinutosParams(temp);
					break;
				case "elpais":
					params = getElPaisParams(temp);
					break;
				case "abc":
					params = getAbcParams(temp);
					break;
				}
				if (params != null) {
					result.add(params);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static PluginParams<PluginOperations> getVeinteMinutosParams(List<String> args) {
		PluginParams<PluginOperations> result = null;
		PluginOperations constant = null;
		String value = null;
		if (args.get(1).contains("::")) {
			value = args.get(1).split("::")[0].trim().toLowerCase();
			switch (value) {
			case "keyword":
				constant = args.get(1).split("::")[1].contains(";") 
					? com.ggar.webscraper.plugins.veinteminutos.config.Operations.KEYWORD_WITH_SECTION 
					: com.ggar.webscraper.plugins.veinteminutos.config.Operations.KEYWORD;
				break;
			case "category":
				constant = com.ggar.webscraper.plugins.veinteminutos.config.Operations.CATEGORY;
				break;
			case "minuteca":
				constant = com.ggar.webscraper.plugins.veinteminutos.config.Operations.MINUTECA;
				break;
			}
			value = args.get(1).split("::")[1].trim().toLowerCase();
		} else {
			value = args.get(1);
			constant = com.ggar.webscraper.plugins.veinteminutos.config.Operations.SINGLE_ARTICLE;
		}
		if (constant != null) {
			result = new PluginParams<PluginOperations>(constant, value) {};
		}
		return result;
	}

	public static PluginParams<PluginOperations> getElPaisParams(List<String> args) {
		PluginParams<PluginOperations> result = null;
		com.ggar.webscraper.plugins.elpais.config.Operations constant = null;
		String value = null;
		if (args.get(1).contains("::")) {
			value = args.get(1).split("::")[0].trim().toLowerCase();
			switch (value) {
			case "section":
				constant = com.ggar.webscraper.plugins.elpais.config.Operations.SECTION;
				break;
			}
			value = args.get(1).split("::")[1].trim().toLowerCase();
		} else {
			value = args.get(1);
			constant = com.ggar.webscraper.plugins.elpais.config.Operations.SINGLE_ARTICLE;
		}
		if (constant != null) {
			result = new PluginParams<PluginOperations>(constant, value) {};
		}
		return result;
	}

	public static PluginParams<PluginOperations> getAbcParams(List<String> args) {
		PluginParams<PluginOperations> result = null;
		PluginOperations constant = null;
		String value = null;
		if (args.get(1).contains("::")) {
			value = args.get(1).split("::")[0].trim().toLowerCase();
			switch (value) {
			case "keyword":
				constant = com.ggar.webscraper.plugins.abc.config.Operations.KEYWORD;
				break;
			}
			value = args.get(1).split("::")[1].trim().toLowerCase();
		} else {
			value = args.get(1);
			constant = com.ggar.webscraper.plugins.abc.config.Operations.SINGLE_ARTICLE;
		}
		if (constant != null) {
			result = new PluginParams<PluginOperations>(constant, value) {};
		}
		return result;
	}
}