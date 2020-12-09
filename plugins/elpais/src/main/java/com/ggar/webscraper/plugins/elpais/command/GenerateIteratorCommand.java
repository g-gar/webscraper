package com.ggar.webscraper.plugins.elpais.command;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import com.ggar.webscraper.plugins.elpais.ElPais;
import com.ggar.webscraper.plugins.elpais.config.UrlTemplate;
import com.ggar.webscraper.plugins.elpais.util.UrlIterator;

public class GenerateIteratorCommand implements Command<UrlTemplate, UrlIterator> {

	private final Logger log = Logger.getLogger(ElPais.class.getName());
	private final String[] params;
	
	public GenerateIteratorCommand(String...params) {
		this.params = params;
	}
	
	@Override
	public UrlIterator execute(UrlTemplate template) {
		return  new UrlIterator(i -> {
			URL result = null;
			try {
				log.info("Processing " + String.format(template.get(params), i));
				result = new URL(String.format(template.get(params), i));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return result;
		});
	}

}
