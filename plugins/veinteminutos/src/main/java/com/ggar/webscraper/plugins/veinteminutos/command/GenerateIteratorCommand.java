package com.ggar.webscraper.plugins.veinteminutos.command;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import com.ggar.webscraper.plugins.veinteminutos.VeinteMinutos;
import com.ggar.webscraper.plugins.veinteminutos.config.UrlTemplate;
import com.ggar.webscraper.plugins.veinteminutos.util.UrlIterator;

public class GenerateIteratorCommand implements Command<UrlTemplate, UrlIterator> {

	private final Logger log = Logger.getLogger(VeinteMinutos.class.getName());
	private final String[] params;
	
	public GenerateIteratorCommand(String...params) {
		this.params = params;
	}
	
	@Override
	public UrlIterator execute(UrlTemplate template) {
		return  new UrlIterator(i -> {
			URL result = null;
			try {
				result = new URL(String.format(template.get(params), i));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return result;
		});
	}

}
