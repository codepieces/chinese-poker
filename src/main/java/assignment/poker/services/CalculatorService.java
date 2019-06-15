package assignment.poker.services;

import assignment.poker.models.PlayerHand;

import java.util.List;

public interface CalculatorService {
    int[] calcScore(List<PlayerHand> playerHands);
}