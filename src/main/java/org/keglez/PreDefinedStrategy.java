package org.keglez;

/**
 *  This class represents a pre-defined strategy, whereby a pre-defined number is
 *  chosen by the computer, based on the amount of match sticks that are left.
 *
 *  @author Keegan Hinnigan
 *  @since 23/01/2025
 *  @version 1.0
 */
public class PreDefinedStrategy implements MoveStrategy
{
    /**
     * This method is the pre-defined strategy.
     *
     * @param currentPileCount The amount of matchsticks left.
     * @return Number to remove.
     */
    @Override
    public int NextMove(int currentPileCount)
    {
        return switch (currentPileCount)
        {
            case  1 -> 1;
            case  2 -> 2;
            case  3 -> 1;
            case  4 -> 1;
            case  5 -> 2;
            case  6 -> 1;
            case  7 -> 2;
            case  8 -> 1;
            case  9 -> 1;
            case 10 -> 2;
            default -> 0;
        };
    }
}
