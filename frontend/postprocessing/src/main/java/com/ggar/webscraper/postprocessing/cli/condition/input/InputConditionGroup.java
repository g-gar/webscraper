package com.ggar.webscraper.postprocessing.cli.condition.input;

import com.ggar.stepping.core.util.GraphBuilder;
import com.ggar.tools.argsparser.condition.MutuallyExclusiveConditionGroup;

public class InputConditionGroup extends MutuallyExclusiveConditionGroup {

    private final GraphBuilder builder;

    public InputConditionGroup(GraphBuilder builder) {
        super('i');
        this.builder = builder;
    }
}
