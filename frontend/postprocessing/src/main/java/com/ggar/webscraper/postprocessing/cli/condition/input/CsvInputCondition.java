package com.ggar.webscraper.postprocessing.cli.condition.input;

import com.ggar.stepping.core.Step;
import com.ggar.stepping.core.util.GraphBuilder;
import com.ggar.tools.argsparser.condition.SingleCondition;
import com.ggar.webscraper.postprocessing.model.Article;
import com.ggar.webscraper.postprocessing.model.CsvStringToArticleConversor;
import com.ggar.webscraper.postprocessing.step.ParseCsvLinesStep;
import com.ggar.webscraper.postprocessing.step.ReadCsvFileStep;
import org.apache.commons.cli.CommandLine;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CsvInputCondition extends SingleCondition {

    private final GraphBuilder builder;

    public CsvInputCondition(GraphBuilder builder) {
        super("csv");
        this.builder = builder;
    }

    @Override
    public <T> T get(CommandLine commandLine) {
        return (T) builder.addRootNodes(Arrays.asList(commandLine.getOptionValues('c')).stream()
                .map(Paths::get)
                .map(ReadCsvFileStep::new)
                .collect(Collectors.toList())
                .toArray(new Step[commandLine.getOptionValues('c').length])
        ).addNode(new ParseCsvLinesStep<Article>(new CsvStringToArticleConversor()));
    }
}
