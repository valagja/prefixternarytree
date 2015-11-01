package com.valagja.search.ternarytree.prefix;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Implementation of a ternary search tree for prefix search.
 *
 * Each node of a word is associated with a {@link Collector}.
 * A Collector holds one or more values.
 * A node is only removed if the collector is empty.
 *
 * In that way the very same node (representing a word) can hold different values.
 *
 * The exact behaviour depends on the implementation of a collector.
 * The tree adds values to the existing collector and removes values from the collector.
 * The tree does not know how many values a collector holds but can ask the collector if it is empty.
 *
 * This tree fully supports removing a word and the associated collector.
 *
 * Note: The values being added or removed are hold by a collector, so this implementation accepts only
 * collectors to be added or removed.
 *
 * @param <V> the value type a collector holds
 * @param <Entry> an implementation of a collector
 */
public class TernarySearchTree<V, Entry extends Collector<V>> {

    private TernaryNode<V, Entry> root;

    /**
     * Adds a word to the tree associated with a {@link Collector}.
     *
     * If the word was previously added to this tree the specified collector
     * is added to the existing collector.
     *
     * @param word the key
     * @param entry a collector with values
     */
    public void put(String word, Entry entry) {
        // empty or null not allowed
        if ( word == null || word.isEmpty() ) {
            return;
        }
        put(null, root, word, entry, 0);
    }

    private void put(TernaryNode<V, Entry> parent, TernaryNode<V, Entry> currentNode, String word, Entry entry, int counter) {
        boolean isLowerKid = false;
        boolean isEqualKid = false;
        boolean isHigherKid = false;
        do {
            char key = word.charAt(counter);
            if (currentNode == null) {
                currentNode = new TernaryNode<>(key, parent);
                if (isLowerKid) {
                    parent.setLowerKid(currentNode);
                } else if (isEqualKid) {
                    parent.setEqualKid(currentNode);
                } else if (isHigherKid) {
                    parent.setHigherKid(currentNode);
                }
                else {
                    // root was null, creating root here
                    root = currentNode;
                }
            }
            isLowerKid = false;
            isEqualKid = false;
            isHigherKid = false;

            if (key < currentNode.getKey()) {
                parent = currentNode;
                currentNode = currentNode.getLowerKid();
                isLowerKid = true;
            } else if (key > currentNode.getKey()) {
                parent = currentNode;
                currentNode = currentNode.getHigherKid();
                isHigherKid = true;
            } else {
                parent = currentNode;
                currentNode = currentNode.getEqualKid();
                counter++;
                isEqualKid = true;
            }
        }
        while (counter < word.length());
        if (parent.getEntry() == null) {
            parent.setEntry(entry);
        }
        else {
            parent.getEntry().add(entry);
        }
    }


    /**
     * Searches for collectors associated with words that starts with the specified prefix.
     *
     * @param prefix the search prefix
     * @return a list of collectors, that contain values
     */
    public List<Entry> find(String prefix) {
        // empty or null not allowed
        if (prefix == null || prefix.isEmpty() ) {
            return new ArrayList<>(0);
        }
        TernaryNode<V, Entry> currentNode = get( root, prefix, 0);
        if (currentNode == null) {
            // nothing found
            return new ArrayList<>(0);
        }
        List<Entry> result = new ArrayList<>();
        if (currentNode.getEntry() != null) {
            result.add(currentNode.getEntry());
        }
        // are there more matches for the prefix?
        // collect from subtree
        if (currentNode.getEqualKid() != null) {
            traversePreOrder(currentNode.getEqualKid(), result);
        }
        return result;
    }

    private TernaryNode<V, Entry> get(TernaryNode<V, Entry> currentNode, String prefix, int counter) {
        while (currentNode != null)
        {
            char key = prefix.charAt(counter);

            if (key < currentNode.getKey()) {
                currentNode = currentNode.getLowerKid();
            }
            else if (key > currentNode.getKey()) {
                currentNode = currentNode.getHigherKid();
            }
            else if (counter < prefix.length() - 1) {
                currentNode = currentNode.getEqualKid();
                counter++;
            }
            else {
               return currentNode;
            }
        }
        return null;
    }

    private void traversePreOrder(TernaryNode<V, Entry> currentNode, List<Entry> result) {
        // avoid recursion
        Deque<TernaryNode<V, Entry>> deque = new ArrayDeque<>();
        deque.push(currentNode);
        while (!deque.isEmpty()) {
            currentNode = deque.pop();
            if (currentNode.getEntry() != null) {
                result.add(currentNode.getEntry());
            }
            if (currentNode.getHigherKid() != null) {
                deque.push(currentNode.getHigherKid());
            }
            if (currentNode.getEqualKid() != null) {
                deque.push(currentNode.getEqualKid());
            }
            if (currentNode.getLowerKid() != null) {
                deque.push(currentNode.getLowerKid());
            }
        }
    }

    /**
     * Removes a word from this tree.
     *
     * If the word was previously added to this tree the specified {@link Collector}
     * is removed from the existing collector.
     *
     * If the existing collector is then empty the existing collector is removed.
     *
     * @param word to be removed
     * @param entry the associated collector with values to be removed
     * @return true if the node in the tree was removed (existing collector was empty after removing the specified collector)
     */
    public boolean remove(String word, Entry entry) {
        // empty or null not allowed
        if (word == null || word.isEmpty() ) {
            return false;
        }
        // search for the node to be removed
        TernaryNode<V, Entry> currentNode = get( root, word, 0);
        if (currentNode == null) {
            // nothing found to remove
            return false;
        }
        // remove found node
        boolean isEmpty = currentNode.getEntry().remove(entry);
        if (! isEmpty) {
            // node is not empty, do not remove this node!
            return false;
        }
        currentNode.setEntry(null);

        // avoid recursion
        do {
             currentNode = killNode(currentNode);
        }
        while (currentNode != null);
        return true;
    }

    private TernaryNode<V, Entry> killNode(TernaryNode<V, Entry> currentNode) {
        // current node has no value and has no children at all
        if (currentNode.getEntry() == null &&
                currentNode.getLowerKid() == null &&
                currentNode.getEqualKid() == null &&
                currentNode.getHigherKid() == null) {
            // remove from parent
            if (currentNode.getParent() == null) {
                // this node is the root, tree will be empty
                root = null;
                return null;
            }
            currentNode.getParent().replaceKid(currentNode, null);
            // continue with parent
            return currentNode.getParent();
        }
        if (currentNode.getEqualKid() != null || currentNode.getEntry() != null) {
            // can't remove any node
            return null;
        }
        // current node has no equal kid and has no value but one or two children
        if ( currentNode.getLowerKid() == null) {
            // higher kid is not null!
            // set the new parent!
            currentNode.getHigherKid().setParent(currentNode.getParent());
            if (currentNode.getParent() == null) {
                // current node is root
                root = currentNode.getHigherKid();
            }
            else {
                currentNode.getParent().replaceKid(currentNode, currentNode.getHigherKid());
            }
            // current node is removed!
            return null;
        }
        if ( currentNode.getHigherKid() == null) {
            // lower kid is not null!
            // set the new parent!
            currentNode.getLowerKid().setParent(currentNode.getParent());
            if (currentNode.getParent() == null) {
                // current node is root
                root = currentNode.getLowerKid();
            }
            else {
                currentNode.getParent().replaceKid(currentNode, currentNode.getLowerKid());
            }
            // current node is removed!
            return null;
        }
        // there are two kids - which one will be the kid of the current parent?
        int higherDif = currentNode.getHigherKid().getKey() - currentNode.getKey();
        int lowerDif = currentNode.getKey() - currentNode.getLowerKid().getKey();
        if ( lowerDif <= higherDif ) {
            // search for the max in lower
            TernaryNode<V, Entry> max = currentNode.getLowerKid();
            while (max.getHigherKid() != null) {
                max = max.getHigherKid();
            }
            TernaryNode<V, Entry> maxParent = max.getParent();
            maxParent.replaceKid(max, max.getLowerKid());
            currentNode.setEntry(max.getEntry());
            currentNode.setKey(max.getKey());
            currentNode.setEqualKid(max.getEqualKid());
        }
        else {
            // search for the min in higher
            TernaryNode<V, Entry> min = currentNode.getHigherKid();
            while (min.getLowerKid() != null) {
                min = min.getLowerKid();
            }
            TernaryNode<V, Entry> minParent = min.getParent();
            minParent.replaceKid(min, min.getHigherKid());
            currentNode.setEntry(min.getEntry());
            currentNode.setKey(min.getKey());
            currentNode.setEqualKid(min.getEqualKid());
        }
        return null;
    }
}


