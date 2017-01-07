package com.helpshift.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by amitsingh.c on 06/01/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<L,R> {
    private L left;
    private R right;
}
