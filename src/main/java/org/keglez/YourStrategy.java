package org.keglez;

/**
 * @author Keegan Hinnigan
 * @version 24/11/2024
 */
public class YourStrategy implements MoveStrategy
{

    /**
     * This strategy works by performing a modulo on
     * the current pile count and adding 1. the modulo
     * will return a 1 or a 0. We add 1 to fit the output
     * into the game rules.
     * @param currentPileCount The amount of matchsticks left.
     * @return number to remove.
     */
    @Override
    public int NextMove(int currentPileCount)
    {
        return switch (currentPileCount) {
            case  1 -> 1;
            case  2 -> 2;
            case  3 -> 1;
            case  4 -> 1;
            case  5 -> 2;
            case  6 -> 1;
            case  7 -> 2;
            case  8 -> 1;
            case  9 -> 2;
            case 10 -> 1;
            default -> throw new IllegalStateException("Unexpected value: " + currentPileCount);
        };
    }
    
}
