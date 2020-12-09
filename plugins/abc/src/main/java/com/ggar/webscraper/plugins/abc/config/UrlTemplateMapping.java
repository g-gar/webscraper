package com.ggar.webscraper.plugins.abc.config;

public enum UrlTemplateMapping {

	KEYWORD(UrlTemplate.KEYWORD);
	
	private final UrlTemplate template;
	
	private UrlTemplateMapping(UrlTemplate template) {
		this.template = template;
	}
	
	public UrlTemplate get() {
		return template;
	}
}
