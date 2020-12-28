package com.ggar.webscraper.postprocessing.cli.condition.output;

import com.ggar.stepping.core.util.GraphBuilder;
import com.ggar.tools.argsparser.condition.SingleCondition;
import com.ggar.webscraper.postprocessing.step.OutputJsonStep;
import org.apache.commons.cli.CommandLine;

import java.nio.file.Paths;

public class JsonOutputCondition extends SingleCondition {

    private final GraphBuilder builder;

    public JsonOutputCondition(GraphBuilder builder) {
        super("json");
        this.builder = builder;
    }

    @Override
    public <T> T get(CommandLine commandLine) {
        return (T) builder.addNode(new OutputJsonStep(Paths.get(commandLine.getOptionValue('j'))));
    }
}
