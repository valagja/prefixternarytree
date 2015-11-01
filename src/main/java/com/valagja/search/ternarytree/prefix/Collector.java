package com.valagja.search.ternarytree.prefix;

import java.util.Collection;

/**
 * An object that holds values.
 *
 * @param <Value> type of values
 */
public interface Collector<Value> {

    /**
     * Adds the values of another collector.
     *
     * @param anotherCollector with values to be added
     */
    void add(Collector<Value> anotherCollector);

    /**
     * Gets the values.
     *
     * @return the stored values
     */
    Collection<Value> getValues();

    /**
     * Gets one value.
     *
     * Method for convenience, some implementation hold only one value at a time.
     *
     * @return one value
     */
    Value getValue();

    /**
     * Removes the values of the specified collector from the stored ones.
     *
     * @param anotherCollector with values to be removed.
     * @return true if this collector is empty after this action else false
     */
    boolean remove(Collector<Value> anotherCollector);




}
