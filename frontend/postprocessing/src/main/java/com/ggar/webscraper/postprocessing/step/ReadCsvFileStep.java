package com.ggar.webscraper.postprocessing.step;

import com.ggar.stepping.core.AbstractStep;
import com.ggar.stepping.core.AbstractStepInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class ReadCsvFileStep extends AbstractStep<Path, List<String>, AbstractStepInfo<Path, List<String>>> {
    @NonNull
    @Getter
    private Path path;

    public ReadCsvFileStep(Path path) {
        super();
        this.path = path;
    }

    @Override
    public List<String> call() throws Exception {
        return Files.readAllLines(this.path);
    }
}
