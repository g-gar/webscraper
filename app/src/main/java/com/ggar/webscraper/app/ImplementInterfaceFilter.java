package com.ggar.webscraper.app;

import org.clapper.util.classutil.ClassFilter;
import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;

public class ImplementInterfaceFilter implements ClassFilter {

    private String interfaceName;

    public <T> ImplementInterfaceFilter(String name) {
        this.interfaceName = name;
    }

    public boolean accept(ClassInfo info, ClassFinder finder) {
        for (String i : info.getInterfaces()) {
            if (i.endsWith(this.interfaceName)) {
                return true;
            }
        }
        return false;
    }

}
