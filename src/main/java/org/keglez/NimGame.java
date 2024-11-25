package org.keglez;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class controls the functions of the Nim game.
 */
public class NimGame
{
    public SaveHandler saveData;

    private Player humanPlayer;
    private Player computerPlayer;

    private boolean isHumanTurn;
    private int marbleSize;
    private ArrayList<Integer> moves;


    /**
     * Blah blah
     * @param humanPlayer
     * @param computerPlayer
     */
    public NimGame(Player humanPlayer, Player computerPlayer)
    {
        this.marbleSize = 10;
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
        this.isHumanTurn = true;
        this.moves = new ArrayList<Integer>();

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


    public void assignMove(int removeAmount)
    {
        moves.add(removeAmount);
        marbleSize -= removeAmount;
        isHumanTurn = !isHumanTurn;
    }


    /**
     * This will check the winner of the game.
     * @return marbleSize
     */
    public boolean checkWinner()
    {
        return marbleSize <= 0;
    }


    /**
     * Saves the game into the save file based on its current
     * state into the save file.
     * @throws IOException File error occurred.
     */
    public String saveGame() throws IOException
    {
        // Cast attributes to string object.
        String marble = String.valueOf(this.marbleSize);
        String human  = String.valueOf(this.isHumanTurn);

        // Use StringBuilder for efficiency.
        StringBuilder move = new StringBuilder();
        for (Integer integer : this.moves) move.append(integer);

        // Append save data. Parse as a string array.
        saveData.append(new String[]{marble, human, move.toString()});

        return "Game saved successfully!";
    }


    /**
     * Loads a game from the save data based on the user input.
     * @throws IOException An issue occurred reading the save file.
     */
    public String loadGame(int id) throws IOException
    {
        // Make sure save data exists.
        saveData.getSaveData();

        // Load the game from the ID.
        String[] game = saveData.loadGame(id); // :3

        // Re-Initialize Game Variables.
        this.marbleSize = Integer.parseInt(game[2]);
        this.isHumanTurn = Boolean.parseBoolean(game[3]);

        // Add all the moves to the moves list.
        this.moves.clear();

        for (int i = 0; i < game[4].length(); i++)
        {
            Integer move = Character.getNumericValue(game[4].charAt(i));
            this.moves.add(move);
        }

        return "Game loaded successfully!";
    }


    /**
     * Resets the game to its default state.
     */
    public String resetGame()
    {
        this.marbleSize = 10;
        this.isHumanTurn = true;
        this.moves.clear();

        return "Game has been reset!";
    }


    /**
     * Undo the last move the user took.
     */
    public String undoLastMove()
    {
        for (int i = 0; i < 2; i++)
        {
            this.marbleSize += this.moves.getLast();
            this.moves.removeLast();
            this.isHumanTurn = true;
        }

        return "Last move has been erased!";
    }



    /*
        Getter Methods
     */

    public Player getHumanPlayer()
    {
        return humanPlayer;
    }

    public Player getComputerPlayer()
    {
        return computerPlayer;
    }

    public boolean getIsHumanTurn()
    {
        return isHumanTurn;
    }

    public int getMarbleSize()
    {
        return marbleSize;
    }

    public boolean isHumanTurn()
    {
        return isHumanTurn;
    }

    public void setHumanTurn(boolean set)
    {
        this.isHumanTurn = set;
    }

    /**
     * Gets the name of the current player.
     * @return The players name.
     */
    public String getCurrentPlayerName()
    {
        if (!getIsHumanTurn())
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
 * Enumerator defines the available game modes.
 */
enum GameMode
{
    EASY,
    MEDIUM,
    HARD
}