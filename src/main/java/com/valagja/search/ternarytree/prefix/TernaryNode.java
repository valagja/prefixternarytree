package com.valagja.search.ternarytree.prefix;

/**
 * The node of the {@link TernarySearchTree}.
 *
 * The node can hold several values, an entry implements {@link Collector}.
 *
 * @param <V> type of the value of the collector
 * @param <Entry> type of the collector
 */
public class TernaryNode<V, Entry extends Collector<V>> {

    private char key;

    private Entry entry;

    private TernaryNode<V,Entry> lowerKid;
    private TernaryNode<V,Entry> equalKid;
    private TernaryNode<V,Entry> higherKid;

    private TernaryNode<V,Entry> parent;

    public TernaryNode(char key, TernaryNode<V,Entry> parent) {
        this.key = key;
        this.parent = parent;
    }

    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public TernaryNode<V, Entry> getLowerKid() {
        return lowerKid;
    }

    public void setLowerKid(TernaryNode<V, Entry> lowerKid) {
        this.lowerKid = lowerKid;
    }

    public TernaryNode<V, Entry> getEqualKid() {
        return equalKid;
    }

    public void setEqualKid(TernaryNode<V, Entry> equalKid) {
        this.equalKid = equalKid;
    }

    public TernaryNode<V, Entry> getHigherKid() {
        return higherKid;
    }

    public void setHigherKid(TernaryNode<V, Entry> higherKid) {
        this.higherKid = higherKid;
    }

    public TernaryNode<V, Entry> getParent() {
        return parent;
    }

    public void setParent(TernaryNode<V, Entry> parent) {
        this.parent = parent;
    }

    public void replaceKid(TernaryNode<V, Entry> currentNode, TernaryNode<V, Entry> newNode) {
        if ( lowerKid == currentNode) {
            lowerKid = newNode;
            return;
        }
        if ( equalKid == currentNode) {
            equalKid = newNode;
            return;
        }
        if (higherKid == currentNode) {
            higherKid = newNode;
        }
    }
}
