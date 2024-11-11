package org.keglez;

import java.util.Random;

public class RandomStrategy implements MoveStrategy {
    private Random random = new Random();

    @Override
    public int NextMove() {
        return random.nextInt(2) + 1; // Randomly remove 1 or 2 piles
    }
   
}
