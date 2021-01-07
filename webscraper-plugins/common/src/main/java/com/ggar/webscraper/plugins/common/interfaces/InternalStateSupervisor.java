package com.ggar.webscraper.plugins.common.interfaces;

import java.util.function.Function;

public interface InternalStateSupervisor<T extends InternalStateSupervisor<T>> {
    T setStateCheckerFunctions(Function<T, Boolean>... stateCheckerFunctions);
    boolean checkCurrentState();
}
