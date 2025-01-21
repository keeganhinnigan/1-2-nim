package org.keglez;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomStrategyTest
{
    RandomStrategy strategy = new RandomStrategy();

    /**
     *  This test should produce a random output between 1 and 2.
     */
    @Test
    void testFirstMove()
    {
        System.out.println("\nTesting First Move");
        int loop = 1;

        while (loop <= 5)
        {
            // Make a move.
            int move = strategy.NextMove(10);
            System.out.println("Run " + loop + ": " + move);

            // The random move should return 1 or 2.
            assertTrue(move == 1 || move == 2);

            // Iterate
            loop++;
        }
    }

    /**
     * If there is 1 match stick left, the strategy should only return 1
     * to avoid too many marbles being removed.
     */
    @Test
    void testLastMove()
    {
        System.out.println("\nTesting Last Move");
        int loop = 1;

        while (loop <= 5)
        {
            // Make a move.
            int move = strategy.NextMove(1);
            System.out.println("Run " + loop + ": " + move);

            // The random move should only return 1.
            assertEquals(1, move);

            // Iterate
            loop++;
        }
    }

    /**
     *  If there are zero match sticks in the pile, nothing should be returned.
     */
    @Test
    void testZeroMatchsticks()
    {
        System.out.println("\nTesting on Zero Matchsticks");
        int loop = 1;

        while (loop <= 5)
        {
            // Make a move.
            int move = strategy.NextMove(0);
            System.out.println("Run " + loop + ": " + move);

            // The random move should only return 1.
            assertEquals(0, move);

            // Iterate
            loop++;
        }
    }


    /**
     *  If there are negative match sticks in the pile, nothing should be returned.
     */
    @Test
    void testNegativeMatchsticks()
    {
        System.out.println("\nTesting on Negative Matchsticks");
        int loop = 1;

        while (loop <= 5)
        {
            // Make a move.
            int move = strategy.NextMove(-1);
            System.out.println("Run " + loop + ": " + move);

            // The random move should only return 1.
            assertEquals(0, move);

            // Iterate
            loop++;
        }
    }
}