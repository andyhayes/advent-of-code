package com.checkraiseit.adventofcode.y2020.d25;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class Day25 {

    public static void main(String[] args) throws IOException {
        log.info("part 1 example: {}", calculateEncryptionKey(17807724L, 5764801L));
        log.info("part 1: {}", calculateEncryptionKey(11404017L, 13768789L));
    }

    static long calculateEncryptionKey(long cardPublicKey, long doorPublicKey) {
        int cardLoopSize = calculateLoopSize(cardPublicKey);
        return transform(doorPublicKey, cardLoopSize);
    }

    static long transform(long subject, int loopSize) {
        long value = 1L;
        for (int i = 0; i < loopSize; i++) {
            value = transformValue(value, subject);
        }
        return value;
    }

    private static long transformValue(long value, long subject) {
        value *= subject;
        value %= 20201227L;
        return value;
    }

    public static int calculateLoopSize(long publicKey) {
        long value = 1L;
        int loopSize = 0;
        while (value != publicKey) {
            loopSize++;
            value = transformValue(value, 7);
        }
        return loopSize;
    }
}
