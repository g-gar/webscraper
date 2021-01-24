package com.ggar.webscraper.extensions.jsoup.model;

import com.ggar.webscraper.interfaces.DownloadTask;
import com.ggar.webscraper.model.Interceptor;
import lombok.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.List;
import java.util.function.Function;

@Getter
@ToString
@EqualsAndHashCode
public class JsoupDownloadPageTask implements DownloadTask<Document, Connection> {

	private final URL url;
	private final List<Interceptor<Connection>> interceptors;

	public JsoupDownloadPageTask(URL url, List<Interceptor<Connection>> interceptors) {
		this.url = url;
		this.interceptors = interceptors;
	}

	@Override
	public Document call() throws Exception {
		Document result = null;
		Connection.Response response = null;
		try {
			Connection connection = Jsoup.connect(this.url.toString());
			for (Interceptor<Connection> interceptor : interceptors) {
				interceptor.apply(connection);
			}
			if ((response = connection.execute()) != null) {
				result = response.parse();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
