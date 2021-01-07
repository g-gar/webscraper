package com.ggar.webscraper.extensions.jsoup.model;

import com.ggar.webscraper.interfaces.DownloadTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@Getter
@ToString
public class JsoupDownloadPageTask implements DownloadTask<Document, Connection> {
	@NonNull
	private URL url;
	private final List<Function<Connection, Connection>> interceptors = new LinkedList<>();

	@Override
	public Document call() throws Exception {
		return this.intercept(Jsoup.connect(this.url.toString())).get();
	}

	@Override
	public DownloadTask<Document, Connection> registerConnectionInterceptors(Function<Connection, Connection>... interceptors) {
		this.interceptors.clear();
		for (Function<Connection, Connection> interceptor : interceptors) {
			this.interceptors.add(interceptor);
		}
		return this;
	}

	@Override
	public Connection intercept(Connection connection) {
		int index = 0;
		while (index < this.interceptors.size()) {
			this.interceptors.get(index++).apply(connection);
		}
		return connection;
	}
}
