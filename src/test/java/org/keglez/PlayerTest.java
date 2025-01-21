package org.keglez;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest
{
    /**
     *  Ensure that a player's strategy can be retrieved.
     */
    @Test
    void testGettingPlayerStrategy()
    {
        Player player = new Player("Human", new HumanUserStrategy());
        Class<HumanUserStrategy> strategy = HumanUserStrategy.class;
        assertInstanceOf(strategy, player.getStrategy());
    }


    /**
     * Ensure that a player's strategy can be set/changed.
     */
    @Test
    void testSettingPlayerStrategy()
    {
        Player player = new Player("Computer", new RandomStrategy());

        // Set the new strategy.
        player.setStrategy(new PreDefinedStrategy());
        Class<PreDefinedStrategy> strategy = PreDefinedStrategy.class;
        assertInstanceOf(strategy, player.getStrategy());
    }


    /**
     *  Ensure that a player's name can be retrieved.
     */
    @Test
    void testGettingPlayerName()
    {
        Player player = new Player("Human", new HumanUserStrategy());
        assertEquals("Human", player.getName());
    }


    /**
     *  Ensure that a player's name can be set/changed.
     */
    @Test
    void testSettingPlayerName()
    {
        Player player = new Player("Human", new HumanUserStrategy());
        player.setName("Computer");

        assertEquals("Computer", player.getName());
    }


    /**
     *  Ensure that a player's is handled correctly when changed to null.
     */
    @Test
    void testSettingNullPlayerName()
    {
        Player player = new Player("Human", new HumanUserStrategy());
        player.setName(null);

        assertEquals("Player", player.getName());
    }
}