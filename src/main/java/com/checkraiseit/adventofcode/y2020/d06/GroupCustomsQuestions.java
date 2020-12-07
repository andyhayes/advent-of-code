package com.checkraiseit.adventofcode.y2020.d06;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

public class GroupCustomsQuestions {

    private final List<Set<String>> questionsAnsweredByEachPassenger = newArrayList();

    public void addPassengerAnswers(String answers) {
        questionsAnsweredByEachPassenger.add(newHashSet(answers.split("")));
    }

    public int numberOfQuestionsAnsweredByAnyoneInGroup() {
        return questionsAnsweredByEachPassenger.stream()
                .reduce(newHashSet(), Sets::union).size();
    }

    public int numberOfQuestionsAnsweredByEveryoneInGroup() {
        Set<String> answeredByFirstPassenger = questionsAnsweredByEachPassenger.get(0);
        return questionsAnsweredByEachPassenger.stream()
                .reduce(answeredByFirstPassenger, Sets::intersection).size();
    }
}
