package com.helpshift.trie;


import com.helpshift.utils.Pair;

import java.util.Set;

/**
 * Created by amitsingh.c on 06/01/17.
 */
public interface Trie<T> {

    /**
     * Inserts word and data associated with it in trie DS.
     * @param word
     * @param data
     */
    void insertWord(String word, T data);

    /**
     * Performs exact match of the given word and return data associated with word.
     * @param word
     * @return T Data associated with given word if exact match is found, if not then returns empty set.
     */
    Set<T> getDataListForWordExactMatch(String word);

    /**
     * Performs partial match of the given prefix.
     * @param prefix
     * @return Set of pair consisting of T [data] and boolean [if prefix was exact match for this data.]
     */
    Set<Pair<T, Boolean>> getDataListForWordStartsWith(String prefix);
}
