package org.keglez;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NimGameTest
{
    Player player1 = new Player("Human", new HumanUserStrategy());
    Player player2 = new Player("Computer", new RandomStrategy()); // Set game mode.


    /**
     *  Ensure that moves are correctly assigned
     */
    @Test
    void testAssignMove()
    {
        NimGame game = new NimGame(player1, player2);

        game.assignMove(2);
        int matchSticks = game.getMatchStickSize();

        assertEquals(8, matchSticks);
    }

    /**
     *  Ensure that moves are correctly assigned
     */
    @Test
    void testNegativeMove()
    {
        // Setup environment.
        NimGame game = new NimGame(player1, player2);
        game.assignMove(9); // Simulate 1 match stick left

        // Attempt to remove 2 match sticks.
        game.assignMove(2);
        assertEquals(0, game.getMatchStickSize());
    }


    /**
     * Ensure that the human can win the game.
     */
    @Test
    void testHumanCanWin()
    {
        // Setup environment.
        NimGame game = new NimGame(player1, player2);
        game.assignMove(9); // Simulate 1 match stick left

        // No winner
        assertFalse(game.checkWinner());

        // Simulate human final move.
        game.setIsHumanTurn(true);
        game.assignMove(1);

        // Gather results.
        boolean winner = game.checkWinner();
        boolean isHuman = !game.getIsHumanTurn(); // Opposite as assignMove method flips the player when called.

        // Test if the human wins.
        assertTrue(winner && isHuman);
    }

    /**
     * Ensure that the computer can win the game.
     */
    @Test
    void testComputerCanWin()
    {
        // Setup environment.
        NimGame game = new NimGame(player1, player2);
        game.assignMove(9); // Simulate 1 match stick left

        // No winner
        assertFalse(game.checkWinner());

        // Simulate computer final move.
        game.setIsHumanTurn(false);
        game.assignMove(player2.getMove(1));

        // Gather results.
        boolean winner = game.checkWinner();
        boolean isComputer = game.getIsHumanTurn(); // Opposite as assignMove method flips the player when called.

        // Test if the human wins.
        assertTrue(winner && isComputer);
    }


    /**
     *  Ensure that the game can be reset.
     */
    @Test
    void testResettingGame()
    {
        // Setup environment.
        NimGame game = new NimGame(player1, player2);
        game.assignMove(4); // Simulate 4 match stick left

        // Reset the game.
        game.resetGame();
        int moves = game.getMatchStickSize(); // Should equal 10.
        assertEquals(10, moves);
    }


    /**
     *  Ensure that the last move can be undone.
     */
    @Test
    void testUndoingLastMove()
    {
        // Setup environment.
        NimGame game = new NimGame(player1, player2);
        game.assignMove(2); // Human move.
        game.assignMove(2); // Computer move.

        // Total match sticks should equal 6.
        assertEquals(6, game.getMatchStickSize());

        // Undo the last moves (will erase computer and human move)
        game.undoLastMove();
        assertEquals(10, game.getMatchStickSize()); // Match sticks should equal 10 again.
    }


    /**
     *  Ensure that the human player can be retrieved.
     */
    @Test
    void testGettingHumanPlayer()
    {
        // Setup environment.
        NimGame game = new NimGame(player1, player2);

        // Check that the player name is "Human".
        Player human = game.getHumanPlayer();
        assertEquals("Human", human.getName());
    }


    /**
     *  Ensure that the computer player can be retrieved.
     */
    @Test
    void testGettingComputerPlayer()
    {
        // Setup environment.
        NimGame game = new NimGame(player1, player2);

        // Check that the player name is "Human".
        Player computer = game.getComputerPlayer();
        assertEquals("Computer", computer.getName());
    }


    /**
     *  Ensure that the program correctly returns whether it's the human's turn.
     */
    @Test
    void testGettingIsHumanTurn()
    {
        // Setup environment.
        NimGame game = new NimGame(player1, player2);

        // testing human turn when set to true.
        game.setIsHumanTurn(true);
        assertTrue(game.getIsHumanTurn());

        // testing human turn when set to false.
        game.setIsHumanTurn(false);
        assertFalse(game.getIsHumanTurn());
    }

    /**
     *  Ensure that the program correctly sets whether it's the human's turn.
     */
    @Test
    void setIsHumanTurn()
    {
        // Setup environment.
        NimGame game = new NimGame(player1, player2);

        // Testing human turn when set to true.
        game.setIsHumanTurn(true);
        assertTrue(game.getIsHumanTurn());

        // Testing human turn when set to false.
        game.setIsHumanTurn(false);
        assertFalse(game.getIsHumanTurn());
    }


    /**
     *  Ensure that the match stick size is correct.
     */
    @Test
    void getMatchStickSize()
    {
        // Setup environment.
        NimGame game = new NimGame(player1, player2);

        // Simulate removing match sticks.
        game.assignMove(2);
        assertEquals(8, game.getMatchStickSize());
    }


    /**
     *  Test that the program can correctly get the current player's name.
     */
    @Test
    void getCurrentPlayerName()
    {
        // Setup environment.
        NimGame game = new NimGame(player1, player2);
        game.setIsHumanTurn(true);

        // Check for human.
        String name = game.getCurrentPlayerName();
        assertEquals("Human", name);

        // Check for computer.
        game.assignMove(2);
        name = game.getCurrentPlayerName();
        assertEquals("Computer", name);
    }
}