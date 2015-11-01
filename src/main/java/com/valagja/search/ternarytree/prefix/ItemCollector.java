package com.valagja.search.ternarytree.prefix;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *  Implements {@link Collector}.
 *
 *  Holds many items as set, that is contains no duplicate items.
 */
public class ItemCollector<Item> implements Collector<Item> {

    private Set<Item> items;

    /**
     * Constructs a new ItemCollector containing the specified item.
     *
     * @param item the first item of this collector
     */
    public ItemCollector(Item item) {
        items = new HashSet<>(1);
        items.add(item);
    }

    /**
     * Adds the content of the specified collector.
     *
     * @param anotherCollector the collector which content is added
     */
    public void add(Collector<Item> anotherCollector) {
        items.addAll(anotherCollector.getValues());
    }

    /**
     * Gets all stored items.
     *
     * @return the stored items.
     */
    public Collection<Item> getValues() {
        return items;
    }

    /**
     * Gets an arbitrary string from the stored ones.
     *
     * @return any string or null if this collector is empty
     */
    public Item getValue() {
        return (items.isEmpty()) ? null : items.iterator().next();
    }

    /**
     * Removes the content of the specified collector.
     *
     * @param anotherCollector the collector which content is removed
     * @return true if this collector is empty after this action else false
     */
    public boolean remove(Collector<Item> anotherCollector) {
        items.removeAll(anotherCollector.getValues());
        return items.isEmpty();
    }
}
