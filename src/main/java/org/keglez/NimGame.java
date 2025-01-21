package org.keglez;

import java.io.IOException;
import java.util.ArrayList;

/**
 *  This class controls the functions of the 1-2 nim game. CLI and
 *  the GUI version of the game will talk to this class for main
 *  functionality.
 *
 *  @author Keegan Hinnigan
 *  @since 23/01/2025
 *  @version 1.0
 */
public class NimGame
{
    // Class attributes.
    public SaveHandler saveData; // Public as it needs to be accessible and modifiable.

    private Player humanPlayer;
    private Player computerPlayer;
    private boolean isHumanTurn;
    private int matchStickSize;
    private ArrayList<Integer> moves;


    /**
     * This is the constructor method for the NimGame class.
     *
     * @param humanPlayer The human strategy.
     * @param computerPlayer The computer strategy.
     */
    public NimGame(Player humanPlayer, Player computerPlayer)
    {
        this.matchStickSize = 10;
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
        this.isHumanTurn = true;
        this.moves = new ArrayList<>();

        // Generate new save handler.
        try
        {
            this.saveData = new SaveHandler("src/main/resources/saves.csv");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method logs a move, then makes/assigns the move.
     *
     * @param removeAmount The number of match sticks to log/remove.
     */
    public void assignMove(int removeAmount)
    {
        // Make sure a negative move cannot be made.
        if (matchStickSize - removeAmount == -1)
        {
            removeAmount = 1;
        }

        moves.add(removeAmount);
        matchStickSize -= removeAmount;
        isHumanTurn = !isHumanTurn;
    }


    /**
     *  This method will check the winner of the game.
     *
     *  @return <code>True</code> or <code>False</code>, based on
     *  whether a winner has been found.
     */
    public boolean checkWinner()
    {
        return matchStickSize <= 0;
    }


    /**
     *  This method uses SaveHandler to save the game into the save file
     *  based on its current state.
     *
     *  @throws IOException File error occurred.
     *  @return Output message.
     */
    public String saveGame() throws IOException
    {
        try
        {
            // Cast attributes to string object.
            String marble = String.valueOf(this.matchStickSize);
            String human = String.valueOf(this.isHumanTurn);

            // Use StringBuilder for efficiency.
            StringBuilder move = new StringBuilder();
            for (Integer integer : this.moves) move.append(integer);

            // Append save data. Parse as a string array.
            saveData.append(new String[]{marble, human, move.toString()});

            return "Game saved successfully!";
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while trying to save the game:");
            throw new RuntimeException(error);
        }
    }


    /**
     * This method loads a game from the save data based on the user's choice.
     *
     * @throws IOException An issue occurred reading the save file.
     * @return Output message.
     */
    public String loadGame(int id) throws IOException
    {
        try {
            // Make sure save data exists.
            saveData.getSaveData();

            // Load the game from the ID.
            String[] game = saveData.loadGame(id); // :3

            // Re-Initialize Game Variables.
            this.matchStickSize = Integer.parseInt(game[2]);
            this.isHumanTurn = Boolean.parseBoolean(game[3]);

            // Add all the moves to the moves list.
            this.moves.clear();

            for (int i = 0; i < game[4].length(); i++) {
                Integer move = Character.getNumericValue(game[4].charAt(i));
                this.moves.add(move);
            }

            return "Game loaded successfully!";
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while trying to load the game:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This method resets the game to it's initial state.
     *
     *  @return Output message.
     */
    public String resetGame()
    {
        try
        {
            this.matchStickSize = 10;
            this.isHumanTurn = true;
            this.moves.clear();

            return "Game has been reset!";
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while resetting the game:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This method will undo the last move the user and computer took.
     *
     *  @return Output message.
     */
    public String undoLastMove()
    {
        try
        {
            for (int i = 0; i < 2; i++)
            {
                this.matchStickSize += this.moves.getLast();
                this.moves.removeLast();
                this.isHumanTurn = true;
            }

            return "Last move has been erased!";
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while trying to undo the last move:");
            throw new RuntimeException(error);
        }
    }


    /**
     * Get the human player.
     *
     * @return Human player
     */
    public Player getHumanPlayer()
    {
        try
        {
            return humanPlayer;
        }
        catch (Exception error)
        {
            System.out.println("An error occurred getting the human player:");
            throw new RuntimeException(error);
        }
    }


    /**
     * Get the computer player.
     *
     * @return Computer player.
     */
    public Player getComputerPlayer()
    {
        try
        {
            return computerPlayer;
        }
        catch (Exception error)
        {
            System.out.println("An error occurred getting the computer player:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  Set the computer player strategy.
     *  @param strategy <code>MoveStrategy</code> to set.
     */
    public void setComputerPlayerStrategy(MoveStrategy strategy)
    {
        try
        {
            this.computerPlayer.setStrategy(strategy);
        }
        catch (Exception error)
        {
            System.out.println("An error occurred setting the computer strategy:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  Return whether it is the human's turn.
     *
     *  @return <code>true</code> or <code>false</code>
     */
    public boolean getIsHumanTurn()
    {
        try
        {
            return isHumanTurn;
        }
        catch (Exception error)
        {
            System.out.println("An error occurred getting the turn:");
            throw new RuntimeException(error);
        }
    }


    /**
     * Set whether it is the human's turn
     * @param set <code>true</code> or <code>false</code>
     */
    public void setIsHumanTurn(boolean set)
    {
        try
        {
            this.isHumanTurn = set;
        }
        catch (Exception error)
        {
            System.out.println("An error occurred setting the turn:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This will get the total size of the match stick pile.
     *
     *  @return Match stick size.
     */
    public int getMatchStickSize()
    {
        return matchStickSize;
    }


    /**
     * Gets the name of the current player.
     *
     * @return The players name.
     */
    public String getCurrentPlayerName()
    {
        if (getIsHumanTurn())
        {
            return "Human";
        }
        else
        {
            return "Computer";
        }
    }

}


/**
 *  This enumerator defines the available game modes.
 */
enum GameMode
{
    EASY,
    MEDIUM,
    HARD
}