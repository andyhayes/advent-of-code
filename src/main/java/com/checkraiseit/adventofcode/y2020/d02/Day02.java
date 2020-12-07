package com.checkraiseit.adventofcode.y2020.d02;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 {

    public static void main(String[] args) throws IOException {
        long validPasswords = Resources.readLines(Resources.getResource("y2020/day02.txt"), Charsets.UTF_8)
                .stream().map(Day02::toCorruptedPassword)
                .filter(CorruptedPassword::isValid)
                .count();
        System.out.println(validPasswords);
    }

    private static CorruptedPassword toCorruptedPassword(String s) {
        Pattern pattern = Pattern.compile("(\\d+)-(\\d+) (.): (.+)");
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            PasswordPolicy passwordPolicy = new PasswordPolicy(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), matcher.group(3));
            return new CorruptedPassword(passwordPolicy, matcher.group(4));
        }
        throw new IllegalStateException();
    }
}
