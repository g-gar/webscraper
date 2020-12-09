package com.ggar.webscraper.plugins.elpais.config;

import com.ggar.webscraper.core.PluginUrlTemplate;

public enum UrlTemplate implements PluginUrlTemplate {
	
	SECTION("https://www.elpais.com/**/%s/");
	
	private final String template;
	
	private UrlTemplate(String template) {
		this.template = template;
	}
	
	public String get(Object...replacements) {
		String result = this.template;
		for (Object replacement : replacements) {
			result = result.replaceFirst("\\*\\*", replacement.toString());
		}
		return result;
	}
	
}
