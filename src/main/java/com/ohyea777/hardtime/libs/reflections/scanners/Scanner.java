package com.ohyea777.hardtime.libs.reflections.scanners;

import com.google.common.base.Predicate;
import com.google.common.collect.Multimap;
import com.ohyea777.hardtime.libs.reflections.Configuration;
import com.ohyea777.hardtime.libs.reflections.vfs.Vfs;

/**
 *
 */
public interface Scanner {

    void setConfiguration(Configuration configuration);

    Multimap<String, String> getStore();

    void setStore(Multimap<String, String> store);

    Scanner filterResultsBy(Predicate<String> filter);

    boolean acceptsInput(String file);

    void scan(Vfs.File file);

    boolean acceptResult(String fqn);
}
