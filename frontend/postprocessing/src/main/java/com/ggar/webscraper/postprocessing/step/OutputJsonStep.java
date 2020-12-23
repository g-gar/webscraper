package com.ggar.webscraper.postprocessing.step;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggar.stepping.core.AbstractStep;
import com.ggar.stepping.core.AbstractStepInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class OutputJsonStep<T> extends AbstractStep<List<T>, Void, AbstractStepInfo<List<T>, Void>> {

    Path output;

    public OutputJsonStep(Path output) {
        super();
        this.output = output;
    }

    @Override
    public Void call() throws Exception {

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(out, this.getStepInfo().getInput());
        final byte[] data = out.toByteArray();

        Files.write(this.output, new String(data).getBytes());

        return null;
    }
}
