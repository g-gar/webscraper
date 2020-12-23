package com.ggar.webscraper.postprocessing.step;

import com.ggar.stepping.core.AbstractStep;
import com.ggar.stepping.core.AbstractStepInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class OutputTxtStep<T> extends AbstractStep<List<T>, Void, AbstractStepInfo<List<T>, Void>> {

    Path folder;
    Function<T, String> contentFn;
    Function<T, String> titleFn;

    public OutputTxtStep(Path folder, Function<T, String> contentFn, Function<T, String> titleFn) {
        super();
        this.folder = folder;
        this.contentFn = contentFn;
        this.titleFn = titleFn;
    }

    @Override
    public Void call() throws Exception {
        List<T> items = this.getStepInfo().getInput();
        for (T item : items) {
            try {
                Files.write(
                        Paths.get(folder.toAbsolutePath().toString(), String.format("/%s.txt", this.titleFn.apply(item))),
                        this.contentFn.apply(item).getBytes()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
