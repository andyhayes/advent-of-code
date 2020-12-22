package com.checkraiseit.adventofcode.y2020.d19;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.extern.log4j.Log4j2;
import one.util.streamex.StreamEx;

import java.io.IOException;
import java.util.List;

@Log4j2
public class Day19 {

    public static void main(String[] args) throws IOException {
        List<String> ruleDefinitions = Resources.readLines(Resources.getResource("y2020/day19-rules.txt"), Charsets.UTF_8);
        List<String> messages = Resources.readLines(Resources.getResource("y2020/day19-messages.txt"), Charsets.UTF_8);
        log.info("messages: {}", messages.size());

        Rules rules = new Rules(ruleDefinitions, false);
        long count = StreamEx.of(messages).filter(rules::matchRuleZero).count();
        log.info("part 1: {}", count);

        rules = new Rules(ruleDefinitions, true);
        count = StreamEx.of(messages).filter(rules::matchRuleZero).count();
        log.info("part 2: {}", count);
    }
}
