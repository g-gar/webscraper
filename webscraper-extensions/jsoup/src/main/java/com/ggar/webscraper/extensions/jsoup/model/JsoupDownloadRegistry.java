package com.ggar.webscraper.extensions.jsoup.model;

import com.ggar.webscraper.interfaces.DownloadRegistry;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JsoupDownloadRegistry implements DownloadRegistry<Document> {

	private final Map<URL, Document> registry;

	public JsoupDownloadRegistry() {
		this.registry = new ConcurrentHashMap<URL, Document>();
	}

	@Override
	public int size() {
		return registry.size();
	}

	@Override
	public boolean isEmpty() {
		return registry.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return registry.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return registry.containsValue(value);
	}

	@Override
	public Document get(Object key) {
		return registry.get(key);
	}

	@Override
	public Document put(URL key, Document value) {
		Document result = null;
		if (value != null) {
			result = registry.put(key, value);
		}
		return result;
	}

	@Override
	public Document remove(Object key) {
		return registry.remove(key);
	}

	@Override
	public void putAll(Map<? extends URL, ? extends Document> m) {
		registry.putAll(m);
	}

	@Override
	public void clear() {
		registry.clear();
	}

	@Override
	public Set<URL> keySet() {
		return registry.keySet();
	}

	@Override
	public Collection<Document> values() {
		return registry.values();
	}

	@Override
	public Set<Entry<URL, Document>> entrySet() {
		return registry.entrySet();
	}
}
