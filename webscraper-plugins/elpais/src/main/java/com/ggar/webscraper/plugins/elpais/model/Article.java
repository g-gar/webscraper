package com.ggar.webscraper.plugins.elpais.model;

import lombok.*;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@Builder(toBuilder=true, access=AccessLevel.PUBLIC)
@AllArgsConstructor
public class Article {

    private final String url, publishedDate, modifiedDate, title, summary, content, language, section;
    private final List<String> tags, authors;

}
