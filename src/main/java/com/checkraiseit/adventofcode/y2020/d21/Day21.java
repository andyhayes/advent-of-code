package com.checkraiseit.adventofcode.y2020.d21;

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

@Log4j2
public class Day21 {

    public static void main(String[] args) throws IOException {
        List<Food> foods = StreamEx.of(Resources.readLines(Resources.getResource("y2020/day21.txt"), Charsets.UTF_8))
                .map(Food::new)
                .toList();
        Map<String, Set<String>> allergensAndPossibleIngredients = newHashMap();
        StreamEx.of(foods).forEach(food -> {
            for (String allergen : food.allergens) {
                Set<String> possibleIngredients = allergensAndPossibleIngredients.getOrDefault(allergen, food.ingredients);
                possibleIngredients = newHashSet(Sets.intersection(possibleIngredients, food.ingredients));
                allergensAndPossibleIngredients.put(allergen, possibleIngredients);
            }
        });
        part1(foods, allergensAndPossibleIngredients);
        part2(allergensAndPossibleIngredients);
    }

    private static void part1(List<Food> foods, Map<String, Set<String>> allergensAndPossibleIngredients) {
        Set<String> ingredientsWithoutAllergens = newHashSet();
        StreamEx.of(foods).forEach(food -> {
            Set<String> ingredientsWithPossibleAllergens = StreamEx.of(allergensAndPossibleIngredients.values()).flatCollection(i -> i).toSet();
            ingredientsWithoutAllergens.addAll(Sets.difference(food.ingredients, ingredientsWithPossibleAllergens));
        });
        long ingredientsWithoutAllergensAppearances = StreamEx.of(foods)
                .mapToLong(food -> StreamEx.of(Sets.intersection(ingredientsWithoutAllergens, food.ingredients)).count())
                .sum();
        log.info("part 1: {}", ingredientsWithoutAllergensAppearances);
    }

    private static void part2(Map<String, Set<String>> allergensAndPossibleIngredients) {
        while (someAllergensContainsMoreThanOneIngredient(allergensAndPossibleIngredients)) {
            Set<String> uniqueIngredients = StreamEx.of(allergensAndPossibleIngredients.values())
                    .filter(ingredients -> ingredients.size() == 1)
                    .flatCollection(i -> i)
                    .toSet();
            StreamEx.of(allergensAndPossibleIngredients.values())
                    .filter(possibleIngredients -> possibleIngredients.size() > 1)
                    .forEach(possibleIngredients -> possibleIngredients.removeAll(uniqueIngredients));
        }
        String ingredientsSortedByAllergen = EntryStream.of(allergensAndPossibleIngredients).sortedBy(Map.Entry::getKey).flatCollection(Map.Entry::getValue).joining(",");
        log.info("part 2: {}", ingredientsSortedByAllergen);
    }

    private static boolean someAllergensContainsMoreThanOneIngredient(Map<String, Set<String>> allergensAndPossibleIngredients) {
        return StreamEx.of(allergensAndPossibleIngredients.values()).anyMatch(ingredients -> ingredients.size() > 1);
    }

    @ToString
    private static class Food {
        private final Set<String> ingredients;
        private final Set<String> allergens;

        private Food(String ingredientsAndAllergens) {
            String ingredients = ingredientsAndAllergens.substring(0, ingredientsAndAllergens.indexOf('('));
            String allergens = ingredientsAndAllergens.substring(ingredientsAndAllergens.indexOf("(contains ") + 10, ingredientsAndAllergens.length() - 1);
            this.ingredients = StreamEx.split(ingredients, " ").toSet();
            this.allergens = StreamEx.split(allergens, ", ").toSet();
        }
    }
}
