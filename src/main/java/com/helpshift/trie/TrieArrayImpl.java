package com.helpshift.trie;

import com.google.common.collect.Sets;

import com.helpshift.utils.Pair;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * Created by amitsingh.c on 06/01/17.
 *
 * Very generic Trie data store implementation
 */
@Getter
public class TrieArrayImpl<T> implements Trie<T> {
    private TrieNode<T> root;

    public TrieArrayImpl() {
        root = new TrieNode();
    }

    @Override
    public void insertWord(String word, T data) {
        TrieNode<T> node = root;
        Validate.isTrue(!StringUtils.isEmpty(word), "Attempt to insert an empty word.");
        for (char ch : word.trim().toLowerCase().toCharArray()) {
            if (node.getCharacters()[getIndex(ch)] == null) {
                TrieNode<T> temp = new TrieNode<>();
                node.getCharacters()[getIndex(ch)] = temp;
                node = temp;
            } else {
                node = node.getCharacters()[getIndex(ch)];
            }
        }
        node.setCompleteWord(true);
        node.getDataList().add(data);
    }

    @Override
    public Set<T> getDataListForWordExactMatch(String word) {
        Set<T> exactMatchingData = Sets.newHashSet();
        Optional<TrieNode<T>> nodeO = searchNode(word);
        if (nodeO.isPresent() && nodeO.get().isCompleteWord()) {
            exactMatchingData.addAll(nodeO.get().getDataList());
        }
        return exactMatchingData;
    }

    public Set<Pair<T, Boolean>> getDataListForWordStartsWith(String prefix) {
        Set<Pair<T, Boolean>> prefixMatchedSet = new HashSet<>();
        Optional<TrieNode<T>> nodeO = searchNode(prefix);
        if (nodeO.isPresent()) {
            prefixMatchedSet = getAllCompleteWordsBelow(nodeO.get(), true).stream()
                    .flatMap(o1 ->
                            o1.getLeft().getDataList().stream().map(o2 -> new Pair<>(o2, o1.getRight().booleanValue())))
                    .collect(Collectors.toSet());
        }
        return prefixMatchedSet;
    }

    private Set<Pair<TrieNode<T>, Boolean>> getAllCompleteWordsBelow(TrieNode<T> trieNode, boolean isExactMatch) {
        Set<Pair<TrieNode<T>, Boolean>> completeWords = new HashSet<>();
        if (trieNode != null) {
            if (trieNode.isCompleteWord()) completeWords.add(new Pair<>(trieNode, isExactMatch));
            for (int i = 0; i < TrieNode.CHAR_SET_SIZE; i++) {
                completeWords.addAll(getAllCompleteWordsBelow(trieNode.getCharacters()[i], false));
            }
        }
        return completeWords;
    }

    private Optional<TrieNode<T>> searchNode(String word) {
        TrieNode<T> node = root;
        if (StringUtils.isEmpty(word)) return Optional.empty();

        for (char ch : word.trim().toLowerCase().toCharArray()) {
            if (node.getCharacters()[getIndex(ch)] == null) {
                return Optional.empty();
            } else {
                node = node.getCharacters()[getIndex(ch)];
            }
        }
        if (node == root) return Optional.empty();
        return Optional.of(node);
    }

    private int getIndex(char ch) {
        return ch - 'a';
    }


}
