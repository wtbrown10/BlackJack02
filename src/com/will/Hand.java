package com.will;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Hand {
    private List<String> hand;
    public List<String> placeHolder;
    public List<String> dealerHand;
    public List<String> splitHand;
    public boolean blackJackCheck;
    public boolean doubleD;
    public int bet;

    StandardDeck deck = new StandardDeck();
    Console console = new Console();
    Scanner scanner = new Scanner(System.in);
    Player player = new Player();
    Pattern pattern = Pattern.compile("AC");


    public Hand() {
        this.hand = new ArrayList<String>();
        this.splitHand = new ArrayList<String >();
        this.placeHolder = new ArrayList<String>();
        this.dealerHand = new ArrayList<String>();
        this.blackJackCheck = false;
        this.doubleD = false;
        this.bet = bet;

    }

    public String getPlayersLastCard() {
        return hand.get(hand.size() - 1);
    }

    public String getDealerLastCard() {
        return dealerHand.get(dealerHand.size() - 1);
    }

    public List<String> drawToHand() {
        for (int count = 0; count < 1; count++) {
            hand.add(deck.draw());
            System.out.println("player drew " + getPlayersLastCard());
            console.next();
            System.out.println(hand + " Player hand value: " + calculateHand());
            if (calculateHand() > 21) {
                System.out.println("You Bust Game Over!");
                System.out.println(bet + " Tokens lost!");
                console.next();
                endGame();
            } else if (calculateHand() == 21 && hand.size() == 2) {
                System.out.println("BlackJack You Win!!");
                System.out.println(bet * 3 + " BlackJack pays double!! Tokens added!");
                winMoneyBJ(bet);
                console.next();
                endGame();
            } else if (calculateHand() == 21) {
                System.out.println("21!");
            }
        }
        placeHolder.clear();
        System.out.println();
        return hand;
    }

    private void winMoneyBJ(int wagerAmount) {
        player.addTokens(wagerAmount * 3);
    }

    public void initialDraw() {
        bet = 0;
        player.addTokens(console.greeting());
        System.out.println("Tokens available: " + player.getTokensBalance());
        console.next();
        bet = console.makeBet();
        player.betTokens(bet);
        console.next();
        deck.shuffle();
        drawToHand();
        console.next();
        drawToDealerHand();
        console.next();
        drawToHand();
        console.next();
        dealerDrawHidden();
        if (blackJackCheck) {
            System.out.println("Checking to see if dealer has BlackJack");
        } else {
        }
        console.next();

        while (true) {
            if (calculateDealerHand() == 21 && calculateHand() == 21) {
                System.out.println("Push!! Draw!!");
                winMoney(bet);
            } else if (calculateDealerHand() == 21) {
                System.out.println(getDealerHand());
                System.out.println(calculateDealerHand());
                System.out.println("Dealer Has BlackJack!! Sorry You Lose!!");
                loseMoney(bet);
                console.next();
                endGame();
                break;
            }
            System.out.println("Dealer does not have blackJack");
            splitChecker();
            hitOrStand();
        }
    }

    public void reDraw() {
        bet = 0;
        System.out.println("Tokens available: " + player.getTokensBalance());
        console.next();
        bet = console.makeBet();
        player.betTokens(bet);
        console.next();
        deck.shuffle();
        drawToHand();
        console.next();
        drawToDealerHand();
        console.next();
        drawToHand();
        console.next();
        dealerDrawHidden();
        if (blackJackCheck) {
            System.out.println("Checking to see if dealer has BlackJack");
        } else {
        }
        console.next();

        while (true) {
            if (calculateDealerHand() == 21 && calculateHand() == 21) {
                System.out.println("Push!! Draw!!");
                winMoney(bet);
            } else if (calculateDealerHand() == 21) {
                System.out.println(getDealerHand());
                System.out.println(calculateDealerHand());
                System.out.println("Dealer Has BlackJack!! Sorry You Lose!!");
                loseMoney(bet);
                console.next();
                endGame();
                break;
            }
            System.out.println("Dealer does not have blackJack");
            splitChecker();
            hitOrStand();
        }
    }

    public void hitOrStand() {
        String value;

        while (true) {

            System.out.print("would you like to (HIT), (STAND), or (DOUBLEDOWN)? ");
            value = scanner.nextLine();
            if (value.toLowerCase().trim().equals("hit")) {
                hit();
                hitOrStand();
                break;
            } else if (value.toLowerCase().trim().equals("stand")) {
                checkForOtherHands();
                stand();
                break;
            } else if (value.toLowerCase().trim().equals("dd")) {
                doubleDown();
                doubleD = true;
                System.out.println("You double downed and increased your wager to " + bet * 2);
                break;
            } else {
                System.out.println("Type (hit) if you would like another card or (stand) if you wish to end turn or (dd) if your would like to double down!");
            }
        }
    }

    public void hitOrStandSplit() {
        String value;

        while (true) {

            System.out.print("would you like to (HIT), (STAND), or (DOUBLEDOWN)? ");
            value = scanner.nextLine();
            if (value.toLowerCase().trim().equals("hit")) {
                hit();
                hitOrStandSplit();
                break;
            } else if (value.toLowerCase().trim().equals("stand")) {
                stand();
                break;
            } else if (value.toLowerCase().trim().equals("dd")) {
                doubleDown();
                doubleD = true;
                System.out.println("You double downed and increased your wager to " + bet * 2);
                break;
            } else {
                System.out.println("Type (hit) if you would like another card or (stand) if you wish to end turn or (dd) if your would like to double down!");
            }
        }
    }

    public void endGame() {
        playAgain();
    }

    public void playAgain() {
        String value;
        System.out.println("would you like to play again (YES) or (NO)? ");
        value = scanner.nextLine();
        if (value.trim().equals("yes")) {
            hand.clear();
            dealerHand.clear();
            deck = new StandardDeck();
            reDraw();
        } else if (value.trim().equals("no")) {
            System.out.println("goodbye!");
            System.exit(1);
            return;
        }
        return;
    }

    public void checkDealerBlackJack() {
        if (calculateDealerHand() == 21) {
            System.out.println("Dealers hidden card was a " + getDealerLastCard());
            System.out.println("Dealer Has BlackJack!");
            System.out.println(bet + " Tokens lost!");
            console.next();
        } else {
        }
    }

    public List<String> drawToDealerHand() {
        dealerHand.add(deck.draw());
        getDealerLastCard();
        System.out.println("Dealer drew " + getDealerLastCard());
        console.next();
        System.out.println(dealerHand + " Dealer Hand value: " + calculateDealerHand());
        if (calculateDealerHand() <= 10) {
            blackJackCheck = true;
        }
        return dealerHand;
    }

    public List<String> getDealerHand() {
        return dealerHand;
    }

    public List<String> getFirstIndex() {

        for (int count = 0; count < hand.size(); count++) {
            placeHolder.add(hand.get(count).substring(0, 2).trim());
        }
        return placeHolder;
    }

    public List<String> getFirstIndexSplit() {

        for (int count = 0; count < splitHand.size(); count++) {
            placeHolder.add(splitHand.get(count).substring(0, 2).trim());
        }
        return placeHolder;
    }

    public List<String> getFirstIndexDealer() {

        for (int count = 0; count < dealerHand.size(); count++) {
            placeHolder.add(dealerHand.get(count).substring(0, 2).trim());
        }
        return placeHolder;
    }

//    public void calcal() {
//        Matcher matcher = pattern.matcher(getPlayersLastCard());
//        boolean matchFound = matcher.find();
//
//        if (calculateHand() > 21) {
//            if (matchFound) {
//
//            }
//
//        }
//    }

    public int calculateHand() {
        int add = 0;
        placeHolder.clear();
        for (int count = 0; count < getFirstIndex().size(); count++) {
            if ("2".equals(getFirstIndex().get(count))) {
                add += 2;
            } else if ("3".equals(getFirstIndex().get(count))) {
                add += 3;
            } else if ("4".equals(getFirstIndex().get(count))) {
                add += 4;
            } else if ("5".equals(getFirstIndex().get(count))) {
                add += 5;
            } else if ("6".equals(getFirstIndex().get(count))) {
                add += 6;
            } else if ("7".equals(getFirstIndex().get(count))) {
                add += 7;
            } else if ("8".equals(getFirstIndex().get(count))) {
                add += 8;
            } else if ("9".equals(getFirstIndex().get(count))) {
                add += 9;
            } else if ("10".equals(getFirstIndex().get(count)) || "JA".equals(getFirstIndex().get(count)) || "QU".equals(getFirstIndex().get(count)) || "KI".equals(getFirstIndex().get(count))) {
                add += 10;
            } else if ("AC".equals(getFirstIndex().get(count)) && add + 11 > 21) {
                add += 1;
            } else if ("AC".equals(getFirstIndex().get(count))) {
                add += 11 ;
            }

            placeHolder.clear();
        }
        return add;
    }

    public int calculateDealerHand() {
        int add = 0;
        placeHolder.clear();
        for (int count = 0; count < getFirstIndexDealer().size(); count++) {
            if ("2".equals(getFirstIndexDealer().get(count))) {
                add += 2;
            } else if ("3".equals(getFirstIndexDealer().get(count))) {
                add += 3;
            } else if ("4".equals(getFirstIndexDealer().get(count))) {
                add += 4;
            } else if ("5".equals(getFirstIndexDealer().get(count))) {
                add += 5;
            } else if ("6".equals(getFirstIndexDealer().get(count))) {
                add += 6;
            } else if ("7".equals(getFirstIndexDealer().get(count))) {
                add += 7;
            } else if ("8".equals(getFirstIndexDealer().get(count))) {
                add += 8;
            } else if ("9".equals(getFirstIndexDealer().get(count))) {
                add += 9;
            } else if ("10".equals(getFirstIndexDealer().get(count)) || "JA".equals(getFirstIndexDealer().get(count)) || "QU".equals(getFirstIndexDealer().get(count)) || "KI".equals(getFirstIndexDealer().get(count))) {
                add += 10;
            } else if ("AC".equals(getFirstIndexDealer().get(count)) && add + 11 > 21)  {
                add += 11;
            } else if ("AC".equals(getFirstIndex().get(count))) {
            add += 11 ;
        }
            placeHolder.clear();
        }
        return add;
    }

    public int calculateHandSplit() {
        int add = 0;
        placeHolder.clear();
        for (int count = 0; count < getFirstIndexSplit().size(); count++) {
            if ("2".equals(getFirstIndexSplit().get(count))) {
                add += 2;
            } else if ("3".equals(getFirstIndexSplit().get(count))) {
                add += 3;
            } else if ("4".equals(getFirstIndexSplit().get(count))) {
                add += 4;
            } else if ("5".equals(getFirstIndexSplit().get(count))) {
                add += 5;
            } else if ("6".equals(getFirstIndexSplit().get(count))) {
                add += 6;
            } else if ("7".equals(getFirstIndexSplit().get(count))) {
                add += 7;
            } else if ("8".equals(getFirstIndexSplit().get(count))) {
                add += 8;
            } else if ("9".equals(getFirstIndexSplit().get(count))) {
                add += 9;
            } else if ("10".equals(getFirstIndexSplit().get(count)) || "JA".equals(getFirstIndex().get(count)) || "QU".equals(getFirstIndex().get(count)) || "KI".equals(getFirstIndex().get(count))) {
                add += 10;
            } else if ("AC".equals(getFirstIndexSplit().get(count)) && add + 11 > 21) {
                add += 1;
            } else if ("AC".equals(getFirstIndexSplit().get(count))) {
                add += 11 ;
            }

            placeHolder.clear();
        }
        return add;
    }

    public void splitChecker() {
        if (hand.get(0).substring(0, 2).equals(hand.get(1).substring(0, 2)) || calculateHand() == 20) {
            String value;

            while (true) {

                System.out.print("would you like to (SPLIT)? YES or NO ");
                value = scanner.nextLine();
                if (value.toLowerCase().trim().equals("yes")) {
                    player.betTokens(bet);
                    splitHand.add(hand.get(1));
                    hand.remove(1);
                    System.out.println("hand 1: " + hand + calculateHand());
                    System.out.println("hand 2: " + splitHand + calculateHandSplit());
                    break;
                } else if (value.toLowerCase().trim().equals("no")) {
                    break;
                } else {
                    System.out.println("Type (yes) if you would like to split (stand) or (no) if you wish to continue with your hand!");
                }
            }
        }
    }
//    public void splitDeal() {
//        drawToHand();
//        String value;
//
//        while (true) {
//
//            System.out.print("would you like to (HIT), (STAND), or (DOUBLEDOWN)? ");
//            value = scanner.nextLine();
//            if (value.toLowerCase().trim().equals("hit")) {
//                hit();
//                hitOrStand();
//                break;
//            } else if (value.toLowerCase().trim().equals("stand")) {
//                stand();
//                break;
//            } else if (value.toLowerCase().trim().equals("dd")) {
//                doubleDown();
//                doubleD = true;
//                System.out.println("You double downed and increased your wager to " + bet * 2);
//                break;
//            } else {
//                System.out.println("Type (hit) if you would like another card or (stand) if you wish to end turn or (dd) if your would like to double down!");
//            }
//        }
//    }
    public void checkWinner() {
        if (calculateHand() == calculateDealerHand()) {
            System.out.println("You Push!! Draw!!");
            System.out.println(bet + " Tokens returned!");
            winMoneyDraw(bet);
            console.next();
            checkForOtherHands();
            endGame();
        } else if (calculateHand() > calculateDealerHand()) {
            System.out.println("You Win " + calculateHand() + " Beats " + calculateDealerHand());
            System.out.println(bet * 2 + " Tokens added!");
            winMoney(bet);
            console.next();
            checkForOtherHands();
            endGame();
        }
        System.out.println("Dealer Wins " + calculateDealerHand() + " Beats " + calculateHand());
        System.out.println(bet + " Tokens lost!");
        console.next();
        checkForOtherHands();
        endGame();
    }

    public void dealerDrawHidden() {
        dealerHand.add(deck.draw());
        for (int count = 0; count < 1; count++) {
            System.out.println(dealerHand.get(0) + ", [card face down]");
        }
    }

    public void dealerHitOrStay() {
        if (calculateDealerHand() > 21) {
            System.out.println("Dealer Bust!! You Win!!");
            System.out.println(bet * 2 + " Tokens added");
            winMoney(bet);
            console.next();
            endGame();
        } else if (calculateDealerHand() <= 15) {
            drawToDealerHand();
            console.next();
            dealerHitOrStay();
        } else {
            dealerStand();
            console.next();
            checkWinner();
        }
    }

    public void dealersTurn() {
        System.out.println(getDealerHand());
        System.out.println(calculateDealerHand());
        console.next();
        dealerHitOrStay();
    }

    public void hit() {
        drawToHand();
    }

    public void doubleDown() {
        player.betTokens(bet);
        drawToHand();
        checkForOtherHands();
        stand();
    }

    public void dealerStand() {
        System.out.println("Dealer stands");
    }

    public void stand() {
        System.out.println("Dealer will play now");
        console.next();
        dealersTurn();
    }

    public void loseMoney(int wagerAmount) {
        if (doubleD) {
            player.loseTokens(wagerAmount * 4);
        } else {
            player.loseTokens(wagerAmount * 2);
        }
    }

    public void winMoneyDraw(int wagerAmount) {
        if (doubleD) {
            player.addTokens(wagerAmount * 2);
        } else {
            player.addTokens(wagerAmount);
        }
    }

    public void winMoney(int wagerAmount) {
        if (doubleD) {
            player.addTokens(wagerAmount * 4);
        } else {
            player.addTokens(wagerAmount * 2);
        }
    }

    public List<String> drawToSplitHand() {
        for (int count = 0; count < 1; count++) {
            splitHand.add(deck.draw());
            System.out.println("player drew " + getPlayersLastCard());
            console.next();
            System.out.println(splitHand + " Player hand value: " + calculateHandSplit());
            if (calculateHandSplit() > 21) {
                System.out.println("You Bust Game Over!");
                System.out.println(bet + " Tokens lost!");
                console.next();
                dealersTurn();
            } else if (calculateHandSplit() == 21 && splitHand.size() == 2) {
                System.out.println("BlackJack You Win!!");
                System.out.println(bet * 3 + " BlackJack pays double!! Tokens added!");
                winMoneyBJ(bet);
                console.next();
                dealersTurn();
            } else if (calculateHandSplit() == 21) {
                System.out.println("21!");
            }
                hitOrStandSplit();
        }
        placeHolder.clear();
        return splitHand;
    }

    public void checkForOtherHands() {
        if(splitHand.size() >= 1) {
            drawToSplitHand();
        }
    }
    }


