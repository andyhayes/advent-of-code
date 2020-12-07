package com.checkraiseit.adventofcode.y2020.d02;

public class PasswordPolicy {

    private final int minOccurrences;
    private final int maxOccurrences;
    private final char letter;

    public PasswordPolicy(int minOccurrences, int maxOccurrences, String letter) {
        this.minOccurrences = minOccurrences;
        this.maxOccurrences = maxOccurrences;
        this.letter = letter.charAt(0);
    }

    public boolean meetsPolicy(String password) {
        int count = (int) password.chars().filter(ch -> ch == letter).count();
        return count >= minOccurrences && count <= maxOccurrences;
    }

    public boolean meetsNewPolicy(String password) {
        return letter == password.charAt(minOccurrences - 1) ^
                letter == password.charAt(maxOccurrences - 1);
    }
}
