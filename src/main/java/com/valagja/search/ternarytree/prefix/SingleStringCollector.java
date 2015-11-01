package com.valagja.search.ternarytree.prefix;

import java.util.Collection;
import java.util.Collections;

/**
 *  Implements {@link Collector}.
 *
 *  Holds only one string, does not collect at all.
 *
 *  Convenient class for testing.
 *
 */
public class SingleStringCollector implements Collector<String> {

    private String value;

    /**
     * Constructs a new SingleStringCollector containing the specified string.
     *
     * @param value the first value
     */
    public SingleStringCollector(String value) {
        this.value = value;
    }

    /**
     * Replaces the string with the string of the specified collector.
     *
     * @param anotherCollector the collector which value is replacing the current one
     */
    public void add(Collector<String> anotherCollector) {
        this.value = anotherCollector.getValue();
    }

    /**
     * Gets the string in a list.
     *
     * @return a one element list holding the only string that this instance has
     */
    public Collection<String> getValues() {
        return Collections.singletonList(value);
    }

    /**
     * Gets the string.
     *
     * @return the string
     */
    public String getValue() {
        return value;
    }

    /**
     * If the string of the specified collector is equal to the stored one,
     * this instance removes its stored string and becomes empty.
     *
     * @param anotherCollector the collector with a string to be removed from this instance
     * @return true if this instance is empty after the action else false
     */
    @Override
    public boolean remove(Collector<String> anotherCollector) {
        if (value == null || value.equals(anotherCollector.getValue())) {
            value = null;
            return true;
        }
        return false;
    }
}
