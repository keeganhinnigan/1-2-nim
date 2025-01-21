package org.keglez;

import java.util.Random;


/**
 *  This class interfaces with the move strategy, and generates
 *  and random number of piles for the computer to remove.
 */
public class RandomStrategy implements MoveStrategy
{
    private Random random = new Random();


    /**
     * Randomly remove match sticks on next move.
     *
     * @param currentPile The total number of match sticks left.
     * @return Number of match sticks to remove.
     */
    @Override
    public int NextMove(int currentPile)
    {
        // Randomly remove match sticks.
        if (currentPile > 1)
        {
            try
            {
                return random.nextInt(2) + 1;
            }
            catch (Exception error)
            {
                System.out.println("An error occurred while trying to calculate the match sticks to remove:");
                throw new RuntimeException(error);
            }
        }
        // Make sure the computer can't remove more match sticks than there are in the pile.
        else if (currentPile == 1)
        {
            return 1;
        }
        // Make sure the computer can't remove any match sticks if nothing exists in the pile.
        else
        {
            return 0;
        }
    }
}
