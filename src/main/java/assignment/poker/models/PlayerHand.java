package assignment.poker.models;

import java.util.List;

public class PlayerHand {
    private final List<Card> cards;

    public PlayerHand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() { return this.cards; }
}