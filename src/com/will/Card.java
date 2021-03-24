package com.will;

public class Card {
    private String suit;
    private int value;
    private boolean isHidden = false;

    public Card(int value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public String displayFace() {
        String output = "";
        switch(value) {
            case 1:
                output = "AC";
                break;
            case 11:
                output = "JA";
                break;
            case 12:
                output = "QU";
                break;
            case 13:
                output = "KI";
                break;
            default:
                output = value == 10 ? Integer.toString(value) : " " + value;
        }
        return output + suit;
    }
}
