package com.will;

public class Player {
    Console console = new Console();
    int tokens = 0;
    String name;

    public Player() {
    }

    public int getTokensBalance() {
        return tokens;
    }

    public int addTokens(int num) {
        tokens = tokens + num;
        return tokens;
    }

    public int loseTokens(int num) {
        tokens = tokens - num;
        return tokens;
    }

    public int betTokens(int num) {
        if (tokens < num) {
            System.out.println("Sorry you do not have enough tokens to wager!!");
        } else {
            tokens = tokens - num;
        }
        return tokens;
    }
}
