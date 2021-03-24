package com.will;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StandardDeck implements Deck {
    private List<Card> cards;
    final private int[] VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    final private String[] SUITS = {"♠", "♥", "♣", "♦"};


    public StandardDeck() {
        cards = new ArrayList<>();
        for (var suit : SUITS) {
            for (var value: VALUES) {
                cards.add(new Card(value, suit));
            }
        }
    }

    public String draw() {

        return cards.remove(cards.size() - 1).displayFace();
    }

    public void shuffle() {
        Collections.shuffle(cards);
        Collections.shuffle(cards);

    }

    public ArrayList getDeck() {
        for (Card card : cards) {
            System.out.println(card.displayFace());

        }

        return (ArrayList) cards;
    }
}
