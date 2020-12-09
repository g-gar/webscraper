```
Warning: this project's current state migh be unsafe and generate some ilegal issues. Use it responsibly.
```

webscraper

## Abstract

The *core* project contains the main definitions for how plugins should behave but they are mostly free to do what it's needed in order to maintain their own capabilities.

The initial plugins (20minutos, ABC and ElPais) are intended to iterate over a well defined list of URLs as a result of any search within the sites. And it's done pretty well and fast.

So fast that it can be considered as a DDoS attack. TODO: log URLs not served because of this or any other reason for a manual inspection and posterior processing.

The *app* project shows a basic functionality where it reads & parses `com.ggar.webscrapper.core.PluginParams` from a CSV input and ouputs the generated results into another CSV file.

But it should be robust enough to design any other kind of application (e.g. a twitter or a Pixiv/Imgur scrapper).

Plugins are automatically injected in the `com.ggar.webscrapper.core.PluginManager` thanks to [GitHub - bmc/javautil: org.clapper.util Java Utility Library](https://github.com/bmc/javautil) and their functionality will be documented in each respective project but, meanwhile, it's indicated in each `com.ggar.webscrapper.core.PluginUrlIterator` implementation.
