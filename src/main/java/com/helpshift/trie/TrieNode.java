package com.helpshift.trie;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class TrieNode<T> {
    // TODO : Generalize char set size from config.
    public static final int CHAR_SET_SIZE = 26;
    private boolean isCompleteWord = false;
    private TrieNode[] characters;
    private Set<T> dataList;

    public TrieNode() {
        this.characters = new TrieNode[CHAR_SET_SIZE];
        this.dataList = new HashSet<>();
    }
}
