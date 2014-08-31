package com.ohyea777.hardtime.libs.reflections;

import com.ohyea777.hardtime.libs.reflections.adapters.MetadataAdapter;
import com.ohyea777.hardtime.libs.reflections.scanners.Scanner;
import com.ohyea777.hardtime.libs.reflections.serializers.Serializer;

import java.net.URL;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * Configuration is used to create a configured instance of {@link Reflections}
 * <p>it is preferred to use {@link com.ohyea777.hardtime.libs.reflections.util.ConfigurationBuilder}
 */
public interface Configuration {
    /** the scanner instances used for scanning different metadata */
    Set<Scanner> getScanners();

    /** the urls to be scanned */
    Set<URL> getUrls();

    /** the metadata adapter used to fetch metadata from classes */
    @SuppressWarnings({"RawUseOfParameterizedType"})
    MetadataAdapter getMetadataAdapter();

    /** the fully qualified name filter used to filter types to be scanned */
    boolean acceptsInput(String inputFqn);

    /** executor service used to scan files
     * if null, scanning is done in a simple for loop */
    ExecutorService getExecutorService();

    /** the default serializer to use when saving Reflection */
    Serializer getSerializer();

    /** get class loaders, might be used for resolving methods/fields */
    /*@Nullable*/ ClassLoader[] getClassLoaders();
}
