package com.will;

import java.util.Scanner;

public class Console {
    Scanner scanner = new Scanner(System.in);



    public String getString(String query) {
        System.out.println(query);

        String value = scanner.nextLine();
        return value;
    }

    public int greeting() {
        System.out.println("Hello! Welcome to Java BlackJack!");
        next();
        System.out.println("How many tokens do you have? (10-10000)");
        int value = scanner.nextInt();
        return value;

    }

    public int makeBet() {
        System.out.println("Place your bets!");
        next();
        System.out.println("How many tokens would you like to bet?");
        int value = scanner.nextInt();
        System.out.println("Ok you bet " + value + " tokens!");
        return value;
    }

    public String askToSplit() {
        System.out.println("would you like to (HIT), (STAND), (DOUBLEDOWN), or (SPLIT)?  ");
        String value = scanner.nextLine();
        return value;
    }


    public void next() {
        String value;
        while(true) {
            value = scanner.nextLine();
            if(value.trim().equals("")) {
                break;
            }
        }
    }
}
