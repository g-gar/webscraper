```
Disclaimer: this project's current state migh be unsafe and generate some legal issues.
Use with caution and under your own risk.
```

# webscraper

## Abstract

Scrap any source to recover any kind of data over HTTP/HTTPS protocols.

It currently supports a limited number of sources, which are:
* [abc](https://www.abc.es/): retrieves articles
* [el pais](https://elpais.com/): retrieves articles
* [20 minutos](https://www.20minutos.es/): retrieves articles

Planned and possible future additions:
* [twitter](https://twitter.com/): retrieves tweets (w/o Twitter API)
* [netflix](https://www.netflix.com/): retrieves movies and series
* [stackexchange](https://stackexchange.com/): retrieves questions w/o answers

## Frameworks & Tools:

* [Google Guice](https://github.com/google/guice) with [AssistedInject](https://github.com/square/AssistedInject)
* [jsoup](https://github.com/jhy/jsoup/)
* [Selenium](https://github.com/SeleniumHQ/selenium) (planned future addition)
* [Project Lombok](https://projectlombok.org/)
* [Apache Maven](https://maven.apache.org/)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa) (Very probable future addition for data persistence)
* [JWorkFlow](https://github.com/danielgerlag/jworkflow) (planned future addition)