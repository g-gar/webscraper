package com.ggar.webscraper.postprocessing.step;

import com.ggar.stepping.core.AbstractStep;
import com.ggar.stepping.core.AbstractStepInfo;
import com.ggar.webscraper.core.AbstractEntity;
import com.ggar.webscraper.core.Plugin;
import com.ggar.webscraper.core.PluginParams;
import com.ggar.webscraper.postprocessing.model.PluginAdapter;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class ScrapeStep<T extends AbstractEntity> extends AbstractStep<Void, List<T>, AbstractStepInfo<Void, List<T>>> {

    @NonNull
    @Getter
    private final PluginAdapter adapter;

    @NonNull
    @Getter
    private final String operation, value;

    public ScrapeStep(@NonNull PluginAdapter adapter, @NonNull String operation, @NonNull String value) {
        this.adapter = adapter;
        this.operation = operation;
        this.value = value;
    }


    @Override
    public List<T> call() throws Exception {
        Plugin plugin = adapter.getPlugin();
        PluginParams params = adapter.getPluginParams(this.operation, this.value);

        return plugin.canHandle(params) ? plugin.handle(params) : null;
    }
}
