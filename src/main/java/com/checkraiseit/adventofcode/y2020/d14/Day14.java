package com.checkraiseit.adventofcode.y2020.d14;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.EntryStream;
import one.util.streamex.LongStreamEx;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Maps.newHashMap;

@Log4j2
public class Day14 {

    private static final Pattern memoryAssignmentPattern = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");

    public static void main(String[] args) throws IOException {
        List<String> lines = Resources.readLines(Resources.getResource("y2020/day14.txt"), Charsets.UTF_8);
        part1(lines);
        part2(lines);
    }

    private static void part1(List<String> lines) {
        Map<Integer, Long> memory = newHashMap();
        String mask = "";
        for (String line : lines) {
            if (line.startsWith("mask")) {
                mask = line.substring(7);
                continue;
            }
            Matcher matcher = memoryAssignmentPattern.matcher(line);
            if (matcher.matches()) {
                int address = Integer.parseInt(matcher.group(1));
                String value = to36BitBinaryString(matcher.group(2));
                String newMemoryValue = applyBitMaskTo(mask, value);
                memory.put(address, Long.parseLong(newMemoryValue, 2));
            }
        }
        log.info(LongStreamEx.of(memory.values()).sum());
    }

    private static String applyBitMaskTo(String mask, String value) {
        return EntryStream.of(value.split("")).map(entry -> {
            char maskChar = mask.charAt(entry.getKey());
            if (maskChar != 'X') {
                return maskChar;
            }
            return entry.getValue();
        }).joining();
    }

    private static void part2(List<String> lines) {
        Map<Long, Long> memory = newHashMap();
        String mask = "";
        for (String line : lines) {
            if (line.startsWith("mask")) {
                mask = line.substring(7);
                continue;
            }
            Matcher matcher = memoryAssignmentPattern.matcher(line);
            if (matcher.matches()) {
                String address = to36BitBinaryString(matcher.group(1));
                int value = Integer.parseInt(matcher.group(2));
                String floatingAddress = applyBitMaskToMemoryAddress(mask, address);
                addressPermutations(floatingAddress)
                        .map(binaryAddress -> Long.parseLong(binaryAddress, 2))
                        .forEach(a -> memory.put(a, (long) value));
            }
        }
        log.info(LongStreamEx.of(memory.values()).sum());
    }

    private static String to36BitBinaryString(String value) {
        return Strings.padStart(Integer.toBinaryString(Integer.parseInt(value)), 36, '0');
    }

    private static StreamEx<String> addressPermutations(String floatingAddress) {
        if (!floatingAddress.contains("X")) {
            return StreamEx.of(floatingAddress);
        }
        int indexOfX = floatingAddress.indexOf('X');
        String address0 = replaceCharAtWith(floatingAddress, indexOfX, '0');
        String address1 = replaceCharAtWith(floatingAddress, indexOfX, '1');
        return StreamEx.of(addressPermutations(address0)).append(addressPermutations(address1));
    }

    private static String replaceCharAtWith(String value, int i, char replace) {
        StringBuilder builder = new StringBuilder(value);
        builder.setCharAt(i, replace);
        return builder.toString();
    }

    private static String applyBitMaskToMemoryAddress(String mask, String value) {
        return EntryStream.of(value.split("")).map(entry -> {
            char maskChar = mask.charAt(entry.getKey());
            if (maskChar == '0') {
                return entry.getValue();
            }
            return maskChar;
        }).joining();
    }
}
