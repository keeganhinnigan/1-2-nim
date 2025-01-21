package org.keglez;

/**
 *  This class represents a player, whereby, a player may be the user or
 *  the computer.
 */
public class Player
{
    // Class attributes.
    private String name;
    private MoveStrategy strategy;


    /**
     *  Constructor method for Player.
     *
     *  @param name Player name.
     *  @param strategy The move strategy.
     */
    public Player(String name, MoveStrategy strategy)
    {
        this.name = name;
        this.strategy = strategy;
    }


    /**
     *  Calculate the next move based on the player's strategy.
     *
     *  @param currentPileSize The total number of match sticks.
     *  @return Match sticks to remove.
     */
    public int getMove(int currentPileSize)
    {
        return strategy.NextMove(currentPileSize);
    }


    /**
     *  Get the player's name.
     *
     *  @return Player's name.
     */
    public String getName()
    {
        return name;
    }


    /**
     *  Set the name of the player.
     *
     *  @param name Name to set.
     */
    public void setName(String name)
    {
        // Make sure a null name isn't set.
        if (name == null)
        {
            name = "Player";
        }
        this.name = name;
    }


    /**
     *  Get the player's strategy.
     *
     *  @return Strategy.
     */
    public MoveStrategy getStrategy()
    {
        return strategy;
    }


    /**
     *  Set the player's strategy.
     *
     *  @param strategy The new strategy.
     */
    public void setStrategy(MoveStrategy strategy)
    {
        this.strategy = strategy;
    }
}
