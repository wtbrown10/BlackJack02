package com.will;

public class Main {

    public static void main(String[] args) {
	// write your code here
        StandardDeck myDeck = new StandardDeck();
        Console console = new Console();
        Player player = new Player();
        Hand hand = new Hand();
        Table table = new Table();
        myDeck.shuffle();
//        System.out.println(myDeck.draw());
//        System.out.println(myDeck.draw());

//        System.out.println(hand.drawToHand());
//        System.out.println(hand.calculateHand());
//        System.out.println(hand.drawToHand());
////        System.out.println(hand.getFirstIndex());
//        System.out.println(hand.calculateHand());

            hand.initialDraw();
//
//        console.greeting();
//        System.out.println(player.getTokensBalance());

    }
}
