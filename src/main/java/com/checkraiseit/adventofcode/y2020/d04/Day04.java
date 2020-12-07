package com.checkraiseit.adventofcode.y2020.d04;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class Day04 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Resources.readLines(Resources.getResource("y2020/day04.txt"), Charsets.UTF_8);
        List<Passport> passports = Lists.newArrayList();
        Passport passport = new Passport();
        for (String line : lines) {
            if (line.equals("")) {
                passports.add(passport);
                passport = new Passport();
                continue;
            }
            String[] keyValuePairs = line.split(" ");
            for (String keyValuePair : keyValuePairs) {
                String key = keyValuePair.substring(0, keyValuePair.indexOf(':'));
                String value = keyValuePair.substring(keyValuePair.indexOf(':') + 1);
                passport.set(key, value);
            }
        }
        passports.add(passport);
        long count = passports.stream().filter(Passport::isValid).count();
        System.out.println(count);
    }
}
