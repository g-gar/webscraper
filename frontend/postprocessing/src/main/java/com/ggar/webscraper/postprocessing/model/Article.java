package com.ggar.webscraper.postprocessing.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@FieldDefaults(makeFinal = true)
@Builder
public class Article implements Serializable {

    private String url, publishedDate, modifiedDate, title, summary, content, language, section;
    private List<String> tags, authors;

}
