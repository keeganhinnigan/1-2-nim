package org.keglez;

/**
 *  This class represents a modulo strategy, whereby the current pile count is
 *  modded by 2 to produce an output.
 *
 *  @author Keegan Hinnigan
 *  @since 23/01/2025
 *  @version 1.0
 */
public class ModuloStrategy implements MoveStrategy
{
    /**
     * This strategy works by performing a modulo on the current pile count. the modulo
     * will return a 1 or a 0. Adding 1 allows the output to fit into the game rules.
     *
     * @param currentPile The amount of matchsticks left.
     * @return Number to remove.
     */
    @Override
    public int NextMove(int currentPile)
    {
        // Make sure the computer can't remove more match sticks than there are in the pile.
        if (currentPile == 1)
        {
            return 1;
        }
        else
        {
            try
            {
                return (currentPile % 2) + 1;
            }
            catch (Exception e)
            {
                System.out.println("An error occurred while trying to calculate the match sticks to remove:");
                throw new RuntimeException(e);
            }
        }
    }
}

