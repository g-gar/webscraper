package com.ggar.webscraper.plugins.veinteminutos.config;

public enum UrlTemplateMapping {

	CATEGORY(UrlTemplate.CATEGORY),
	MINUTECA(UrlTemplate.MINUTECA),
	KEYWORD(UrlTemplate.KEYWORD),
	KEYWORD_WITH_SECTION(UrlTemplate.KEYWORD_WITH_SECTION);
	
	private final UrlTemplate template;
	
	private UrlTemplateMapping(UrlTemplate template) {
		this.template = template;
	}
	
	public UrlTemplate get() {
		return template;
	}
}
