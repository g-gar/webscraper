package com.ggar.webscraper.plugins.elpais.config;

public enum UrlTemplateMapping {

	SECTION(UrlTemplate.SECTION);
	
	private final UrlTemplate template;
	
	private UrlTemplateMapping(UrlTemplate template) {
		this.template = template;
	}
	
	public UrlTemplate get() {
		return template;
	}
}
