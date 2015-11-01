package com.valagja.search.ternarytree.prefix;

import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Tests for TernarySearchTree
 */
public class TernarySearchTreeTest {

    @Test
    public void addWords() {
        TernarySearchTree<String, SingleStringCollector> ternarySearchTree = new TernarySearchTree<>();

        List<SingleStringCollector> result = ternarySearchTree.find("a");
        assertTrue(result.isEmpty());

        ternarySearchTree.put("austria", new SingleStringCollector("austria command"));
        ternarySearchTree.put("aux", new SingleStringCollector("aux command"));
        ternarySearchTree.put("banana", new SingleStringCollector("banana command"));

        result = ternarySearchTree.find("bana");
        assertEquals(1, result.size());
        assertEquals("banana command", result.get(0).getValue());

        result = ternarySearchTree.find("au");
        assertEquals(2, result.size());
        String first = result.get(0).getValue();
        String second = result.get(1).getValue();
        assertEquals("austria command", first);
        assertEquals("aux command", second);

        result = ternarySearchTree.find("aux");
        assertEquals(1, result.size());
        assertEquals("aux command", result.get(0).getValue());

        result = ternarySearchTree.find("x");
        assertTrue(result.isEmpty());

        result = ternarySearchTree.find("");
        assertTrue(result.isEmpty());
    }

    @Test
    public void removeWords() {
        TernarySearchTree<String, SingleStringCollector> ternarySearchTree = new TernarySearchTree<>();
        String[] inputs = {"is", "in", "iu", "it", "be", "by", "as", "at", "he", "on", "of", "or", "to"};
        for (String input : inputs) {
            SingleStringCollector collector = new SingleStringCollector(input + " command");
            ternarySearchTree.put(input, collector);
        }

        List<SingleStringCollector> result = ternarySearchTree.find("is");
        List<String> expected = Collections.singletonList("is command");
        assertEquals(1, result.size());
        assertEquals(expected, result.get(0).getValues());

        boolean isRemoved = ternarySearchTree.remove("is", new SingleStringCollector("is command"));
        assertTrue(isRemoved);

        result = ternarySearchTree.find("is");
        assertTrue(result.isEmpty());

        result = ternarySearchTree.find("it");
        assertEquals(1, result.size());
        assertEquals("it command", result.get(0).getValue());

        for (String input: inputs) {

            result = ternarySearchTree.find(input);
            if ("is".equals(input)) {
                assertTrue(result.isEmpty());
            }
            else {
                assertEquals(1, result.size());
                assertEquals(input + " command", result.get(0).getValue());
            }

            SingleStringCollector collector = new SingleStringCollector(input + " command");
            isRemoved = ternarySearchTree.remove(input, collector);
            if ("is".equals(input)) {
                assertFalse(isRemoved);
            }
            else {
                assertTrue(isRemoved);
            }

            result = ternarySearchTree.find(input);
            assertTrue(result.isEmpty());
        }
    }

    @Test
    public void removeFromEmptyTree() {
        TernarySearchTree<String, SingleStringCollector> ternarySearchTree = new TernarySearchTree<>();
        boolean isRemoved = ternarySearchTree.remove("i", new SingleStringCollector("some command"));
        assertFalse(isRemoved);
    }

    @Test
    public void removeFromTreeWithOneNode() {
        TernarySearchTree<String, SingleStringCollector> ternarySearchTree = new TernarySearchTree<>();
        ternarySearchTree.put("a", new SingleStringCollector("a command"));

        List<SingleStringCollector> result = ternarySearchTree.find("a");
        assertEquals(1, result.size());
        assertEquals("a command", result.get(0).getValue());

        boolean isRemoved = ternarySearchTree.remove("a", new SingleStringCollector("a command"));
        assertTrue(isRemoved);

        result = ternarySearchTree.find("a");
        assertTrue(result.isEmpty());
    }

    @Test
    public void removeFromTreeWithOneNodeAndOneHigherKid() {
        TernarySearchTree<String, SingleStringCollector> ternarySearchTree = new TernarySearchTree<>();
        ternarySearchTree.put("a", new SingleStringCollector("a command"));
        ternarySearchTree.put("b", new SingleStringCollector("b command"));

        List<SingleStringCollector> result = ternarySearchTree.find("a");
        assertEquals(1, result.size());
        assertEquals("a command", result.get(0).getValue());

        boolean isRemoved = ternarySearchTree.remove("a", new SingleStringCollector("a command"));
        assertTrue(isRemoved);

        result = ternarySearchTree.find("a");
        assertTrue(result.isEmpty());
    }

    @Test
    public void removeFromTreeWithOneNodeAndOneLowerKid() {
        TernarySearchTree<String, SingleStringCollector> ternarySearchTree = new TernarySearchTree<>();
        ternarySearchTree.put("b", new SingleStringCollector("b command"));
        ternarySearchTree.put("a", new SingleStringCollector("a command"));

        List<SingleStringCollector> result = ternarySearchTree.find("b");
        assertEquals(1, result.size());
        assertEquals("b command", result.get(0).getValue());

        boolean isRemoved = ternarySearchTree.remove("b", new SingleStringCollector("b command"));
        assertTrue(isRemoved);

        result = ternarySearchTree.find("b");
        assertTrue(result.isEmpty());
    }


    @Test
    public void removeFromTreeWithOneNodeAndOneHigherKidAndOneLowerKid() {
        TernarySearchTree<String, SingleStringCollector> ternarySearchTree = new TernarySearchTree<>();
        ternarySearchTree.put("b", new SingleStringCollector("b command"));
        ternarySearchTree.put("a", new SingleStringCollector("a command"));
        ternarySearchTree.put("c", new SingleStringCollector("c command"));

        List<SingleStringCollector> result = ternarySearchTree.find("b");
        assertEquals(1, result.size());
        assertEquals("b command", result.get(0).getValue());

        boolean isRemoved = ternarySearchTree.remove("b", new SingleStringCollector("b command"));
        assertTrue(isRemoved);

        result = ternarySearchTree.find("b");
        assertTrue(result.isEmpty());
    }
}
