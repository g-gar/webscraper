package com.ggar.webscraper.postprocessing.step;

import com.ggar.stepping.core.AbstractStep;
import com.ggar.stepping.core.AbstractStepInfo;

import java.util.List;

public class PeekStep<T> extends AbstractStep<List<T>, List<T>, AbstractStepInfo<List<T>, List<T>>> {

    @Override
    public List<T> call() throws Exception {
        List<T> items = this.getStepInfo().getInput();
        return null;
    }
}
