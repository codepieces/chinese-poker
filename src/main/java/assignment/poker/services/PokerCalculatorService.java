package assignment.poker.services;

import assignment.poker.models.PlayerHand;
import assignment.poker.services.CalculatorService;

import java.lang.UnsupportedOperationException;
import java.util.List;

public class PokerCalculatorService implements CalculatorService {
    @Override
    public int[] calcScore(List<PlayerHand> playerHands) {
        throw new UnsupportedOperationException("operation not supported");
    }
}