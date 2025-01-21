package org.keglez;

/**
 *  This interface acts as a base class for each move strategy
 *  in the nim game. As there will be many move strategies,
 *  invoking reusability allows for clean code.
 *
 *  @author Keegan Hinngain
 *  @since 23/01/2025
 *  @version 1.0
 */
public interface MoveStrategy
{
    /**
     * This method allows for the computer or human to make the next
     * move.
     *
     * @param currentPile The total number of match sticks left.
     * @return The number of match sticks removed.
     */
    int NextMove(int currentPile);
}
