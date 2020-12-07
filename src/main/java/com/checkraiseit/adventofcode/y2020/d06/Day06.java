package com.checkraiseit.adventofcode.y2020.d06;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.List;

public class Day06 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Resources.readLines(Resources.getResource("y2020/day06.txt"), Charsets.UTF_8);
        List<GroupCustomsQuestions> allGroupCustomsQuestions = Lists.newArrayList();
        GroupCustomsQuestions groupCustomsQuestions = new GroupCustomsQuestions();
        for (String line : lines) {
            if (line.equals("")) {
                allGroupCustomsQuestions.add(groupCustomsQuestions);
                groupCustomsQuestions = new GroupCustomsQuestions();
                continue;
            }
            groupCustomsQuestions.addPassengerAnswers(line);
        }
        allGroupCustomsQuestions.add(groupCustomsQuestions);
        part1(allGroupCustomsQuestions);
        part2(allGroupCustomsQuestions);
    }

    private static void part1(List<GroupCustomsQuestions> allGroupCustomsQuestions) {
        long count = allGroupCustomsQuestions.stream()
                .mapToInt(GroupCustomsQuestions::numberOfQuestionsAnsweredByAnyoneInGroup)
                .sum();
        System.out.println(count);
    }

    private static void part2(List<GroupCustomsQuestions> allGroupCustomsQuestions) {
        long count = allGroupCustomsQuestions.stream()
                .mapToInt(GroupCustomsQuestions::numberOfQuestionsAnsweredByEveryoneInGroup)
                .sum();
        System.out.println(count);
    }
}
