package com.checkraiseit.adventofcode.y2020.d07;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class BagCount {
    private final Bag bag;
    private final int count;
}
