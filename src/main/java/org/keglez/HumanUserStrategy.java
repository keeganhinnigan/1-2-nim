package org.keglez;

import java.util.Scanner;


/**
 *  This class allows the user to make their move during the
 *  nim game. Interfaces with the MoveStrategy class to share
 *  methods.
 *
 *  @author Keegan Hinnigan
 *  @since 23/01/2025
 *  @version 1.0
 */
public class HumanUserStrategy implements MoveStrategy
{
    // Class attributes.
    private Scanner reader;


    /**
     *  Construct a new human user strategy.
     */
    public HumanUserStrategy()
    {
        try
        {
            reader = new Scanner(System.in);
        }
        catch (Exception e)
        {
            System.out.println("An error occurred while constructing HumanUserStrategy class");
            throw new RuntimeException(e);
        }
    }


    /**
     *  This method allows the user to make a move. The user is able
     *  to choose between removing one or two match sticks from the pile.
     *
     *  @param currentPile Current number of match sticks in the pile.
     *  @return The users choice.
     */
    @Override
    public int NextMove(int currentPile)
    {
        // Make sure the user can't remove more match sticks than there are in the pile.
        if (currentPile == 1)
        {
            return 1;
        }

        try
        {
            System.out.print("How many piles do you want to remove? (1 or 2): ");
            return reader.nextInt();
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while trying to make an input:");
            throw new RuntimeException(error);
        }
    }
}
