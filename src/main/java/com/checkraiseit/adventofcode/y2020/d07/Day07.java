package com.checkraiseit.adventofcode.y2020.d07;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

@Log4j2
public class Day07 {

    private static final Map<Bag, Set<BagCount>> bagRules = newHashMap();

    public static void main(String[] args) throws IOException {
        List<String> lines = Resources.readLines(Resources.getResource("y2020/day07.txt"), Charsets.UTF_8);
        lines.forEach(line -> {
            Pattern bagColourPattern = Pattern.compile("^([^ ]+ [^ ]+) .*");
            Matcher matcher = bagColourPattern.matcher(line);
            if (matcher.matches()) {
                String bagColour = matcher.group(1);
                Bag bag = new Bag(bagColour);
                String contains = line.split("contain ")[1];

                Pattern containsBagColourPattern = Pattern.compile("^(\\d) (.+) bag.*");
                List<String> containsBagColours = Arrays.asList(contains.split(", "));
                Set<BagCount> bagCounts = containsBagColours.stream().map(containsBagColour -> {
                    Matcher matcher1 = containsBagColourPattern.matcher(containsBagColour);
                    if (matcher1.matches()) {
                        return new BagCount(new Bag(matcher1.group(2)), Integer.parseInt(matcher1.group(1)));
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toSet());

                bagRules.put(bag, bagCounts);
            }
        });

        Bag shinyGold = new Bag("shiny gold");
        Set<Bag> foundBags = bagsContaining(shinyGold).collect(Collectors.toSet());
        log.info(foundBags.size());
        log.info(numberOfBagsIn(shinyGold));
    }

    private static Stream<Bag> bagsContaining(Bag bag) {
        Set<Bag> containingBags = bagRules.entrySet().stream()
                .filter(entry -> entry.getValue().stream().map(BagCount::getBag).anyMatch(b -> b.equals(bag)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        return Stream.concat(containingBags.stream(), containingBags.stream().flatMap(Day07::bagsContaining));
    }

    private static int numberOfBagsIn(Bag bag) {
        return bagRules.get(bag).stream()
                .map(bagCount -> bagCount.getCount() + (bagCount.getCount() * numberOfBagsIn(bagCount.getBag())))
                .reduce(0, Integer::sum);
    }
}
