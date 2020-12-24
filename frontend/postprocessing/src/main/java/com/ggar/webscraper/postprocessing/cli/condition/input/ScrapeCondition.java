package com.ggar.webscraper.postprocessing.cli.condition.input;

import com.ggar.tools.argsparser.condition.MutuallyExclusiveConditionGroup;

public class ScrapeCondition extends MutuallyExclusiveConditionGroup {
    public ScrapeCondition(Character option) {
        super(option);
    }
}
