package com.valagja.search.ternarytree.prefix;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 *
 * Showing usage
 *
 */
public class ExampleTest {

    /**
     * Create one entry per key.
     */
    @Test
    public void usageSingleWords() {
        // create a new ternary search tree that can only hold one string per node and per key
        TernarySearchTree<String, SingleStringCollector> ternarySearchTree = new TernarySearchTree<>();

        // add a word: Here "hello" is the key and "Hello world!" the value.
        ternarySearchTree.put("hello", new SingleStringCollector("Hello world!"));

        // add another word
        ternarySearchTree.put("helicopter", new SingleStringCollector("Helicopter is flying!"));

        // search for prefix "he"
        List<SingleStringCollector> result = ternarySearchTree.find("he");

        // get two results
        assertEquals(2, result.size());
        assertEquals("Helicopter is flying!", result.get(0).getValue());
        assertEquals("Hello world!", result.get(1).getValue());

        // remove the entry "hello" with value "Hello World"
        ternarySearchTree.remove("hello", new SingleStringCollector("Hello world!"));

        // search for prefix "he"
        result = ternarySearchTree.find("he");
        // get one result
        assertEquals(1, result.size());
        assertEquals("Helicopter is flying!", result.get(0).getValue());
    }

    /**
     * Create one entry and multiple keys.
     */
    @Test
    public void usageMultiWords() {
        // create a new ternary search tree that can only hold one string per node and per key
        TernarySearchTree<String, ItemCollector<String>> ternarySearchTree = new TernarySearchTree<>();

        // add a word: Here "hello" is the key and "Hello world!" the value.
        ternarySearchTree.put("hello", new ItemCollector<>("Hello world!"));

        // add another value for the same key
        ternarySearchTree.put("hello", new ItemCollector<>("Hello helicopter, wow, you fly!"));

        // add more keys
        ternarySearchTree.put("wow", new ItemCollector<>("Hello helicopter, wow, you fly!"));
        ternarySearchTree.put("world", new ItemCollector<>("Hello world!"));

        // add a different key and a different value
        ternarySearchTree.put("helium", new ItemCollector<>("Helium is a chemical element!"));


        // the tree has now four keys: "hello", "wow", "world" and "helium".
        // but only three values:
        // "Hello world!", "Hello helicopter, wow, you fly!" and "Helium is a chemical element!"

        // search for prefix "he"
        List<ItemCollector<String>> result = ternarySearchTree.find("he");

        // get one results with two values and one result with one value
        assertEquals(2, result.size());
        String value = result.get(0).getValue();
        assertEquals("Helium is a chemical element!", value);
        String values = result.get(1).getValues().toString();
        assertEquals("[Hello helicopter, wow, you fly!, Hello world!]", values);

        // search for prefix "wo"
        result = ternarySearchTree.find("wo");
        // get two results with one value respectively
        assertEquals(2, result.size());
        value = result.get(0).getValue();
        assertEquals("Hello helicopter, wow, you fly!", value);
        value = result.get(1).getValue();
        assertEquals("Hello world!", value);
    }

}
