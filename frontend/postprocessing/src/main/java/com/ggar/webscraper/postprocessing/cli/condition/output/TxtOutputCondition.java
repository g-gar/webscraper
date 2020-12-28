package com.ggar.webscraper.postprocessing.cli.condition.output;

import com.ggar.stepping.core.util.GraphBuilder;
import com.ggar.tools.argsparser.condition.SingleCondition;
import com.ggar.webscraper.postprocessing.model.TxtArticleContentGenerator;
import com.ggar.webscraper.postprocessing.step.OutputTxtStep;
import org.apache.commons.cli.CommandLine;

import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class TxtOutputCondition extends SingleCondition {

    private final GraphBuilder builder;

    public TxtOutputCondition(GraphBuilder builder) {
        super("txt");
        this.builder = builder;
    }

    @Override
    public <T> T get(CommandLine commandLine) {
        AtomicInteger counter = new AtomicInteger(0);
        return (T) builder.addNode(new OutputTxtStep(
                Paths.get(commandLine.getOptionValue('t')),
                new TxtArticleContentGenerator(),
                article -> String.valueOf(counter.getAndIncrement())
        ));
    }
}
