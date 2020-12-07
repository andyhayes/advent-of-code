package com.checkraiseit.adventofcode.y2020.d02;

public class CorruptedPassword {
    private final PasswordPolicy passwordPolicy;
    private final String password;

    public CorruptedPassword(PasswordPolicy passwordPolicy, String password) {
        this.passwordPolicy = passwordPolicy;
        this.password = password;
    }

    public boolean isValid() {
        return passwordPolicy.meetsNewPolicy(password);
    }
}
