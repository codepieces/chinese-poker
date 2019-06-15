package assignment.poker.models;

import assignment.poker.models.CardPip;
import assignment.poker.models.CardSuit;

public class Card {
    private final CardSuit suit;
    private final CardPip pip;

    public Card (CardSuit suit, CardPip pip) {
        this.suit = suit;
        this.pip = pip;
    }

    public CardSuit getSuit() { return this.suit; }

    public CardPip getPip() { return this.pip; }
}