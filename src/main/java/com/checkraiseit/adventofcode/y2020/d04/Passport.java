package com.checkraiseit.adventofcode.y2020.d04;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class Passport {

    private Set<String> keys = Sets.newHashSet("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    private final Map<String, String> kayValuePairs = Maps.newHashMap();

    public void set(String key, String value) {
        kayValuePairs.put(key, value);
    }

    public boolean isValid() {
        return this.kayValuePairs.keySet().containsAll(keys) &&
                isValidBirthYear() &&
                isValidIssueYear() &&
                isValidExpirationYear() &&
                isValidHeight() &&
                isValidHairColour() &&
                isValidEyeColour() &&
                isValidPassportId();
    }

    private boolean isValidBirthYear() {
        int value = Integer.parseInt(this.kayValuePairs.get("byr"));
        return value >= 1920 && value <= 2002;
    }

    private boolean isValidIssueYear() {
        int value = Integer.parseInt(this.kayValuePairs.get("iyr"));
        return value >= 2010 && value <= 2020;
    }

    private boolean isValidExpirationYear() {
        int value = Integer.parseInt(this.kayValuePairs.get("eyr"));
        return value >= 2020 && value <= 2030;
    }

    private boolean isValidHeight() {
        String height = this.kayValuePairs.get("hgt");
        if (height.endsWith("cm")) {
            int value = Integer.parseInt(height.substring(0, height.indexOf("cm")));
            return value >= 150 && value <= 193;
        }
        if (height.endsWith("in")) {
            int value = Integer.parseInt(height.substring(0, height.indexOf("in")));
            return value >= 59 && value <= 76;
        }
        return false;
    }

    private boolean isValidHairColour() {
        String colour = this.kayValuePairs.get("hcl");
        return colour.matches("#[0-9a-f]{6}");
    }

    private boolean isValidEyeColour() {
        String colour = this.kayValuePairs.get("ecl");
        Set<String> valid = Sets.newHashSet("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        return valid.contains(colour);
    }

    private boolean isValidPassportId() {
        String colour = this.kayValuePairs.get("pid");
        return colour.matches("[0-9]{9}");
    }
}
