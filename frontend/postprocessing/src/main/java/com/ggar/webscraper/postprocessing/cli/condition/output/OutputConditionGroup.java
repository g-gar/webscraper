package com.ggar.webscraper.postprocessing.cli.condition.output;

import com.ggar.stepping.core.util.GraphBuilder;
import com.ggar.tools.argsparser.condition.MutuallyExclusiveConditionGroup;

public class OutputConditionGroup extends MutuallyExclusiveConditionGroup {

    private final GraphBuilder builder;

    public OutputConditionGroup(GraphBuilder builder) {
        super("output");
        this.builder = builder;
    }

}
