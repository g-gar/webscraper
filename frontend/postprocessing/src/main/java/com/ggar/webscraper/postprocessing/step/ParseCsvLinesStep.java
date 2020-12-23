package com.ggar.webscraper.postprocessing.step;

import com.ggar.stepping.core.AbstractStep;
import com.ggar.stepping.core.AbstractStepInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@Setter
public class ParseCsvLinesStep<T> extends AbstractStep<List<String>, List<T>, AbstractStepInfo<List<String>, List<T>>> {

    @NonNull
    private Function<String, T> conversor;

    public ParseCsvLinesStep(Function<String, T> conversor) {
        super();
        this.conversor = conversor;
    }

    @Override
    public List<T> call() {
        return this.getStepInfo().getInput().stream()
                .map(line -> ParseCsvLinesStep.this.conversor.apply(line))
                .filter(e -> e != null)
                .collect(Collectors.toList());
    }
}
