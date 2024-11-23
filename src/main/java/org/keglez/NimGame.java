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
    private SaveHandler saveData;

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
    public void saveGame() throws IOException
    {
        saveData.appendSaveData();
    }


    /**
     * Loads a game from the save data based on the user input.
     * @throws IOException An issue occurred reading the save file.
     */
    public void loadGame() throws IOException
    {
        Scanner reader = new Scanner(System.in);
        saveData.displaySaveData();

        System.out.println("\nChoose an ID: ");
        int id = reader.nextInt();

        String[] game = saveData.loadGame(id); // :3
        game[4] = game[4].replaceAll("/[^a-zA-Z0-9 ]/g", "");
        System.out.println("Chosen Game: " + Arrays.toString(game) + "\n");

        int marbles = Integer.parseInt(game[2]);
        boolean human = Boolean.parseBoolean(game[3]);
        int[] moves = new int[game[4].length()];

        for (int i = 0; i < game[4].length(); i++)
        {
            System.out.println(game[4].charAt(i));
        }

        for(int i = 0; i < moves.length; i++)
        {
            moves[i] = Integer.parseInt("" + game[4].charAt(i));
        }


        System.out.println();
        System.out.println("Marbles: " + marbles);
        System.out.println("Human: " + human);
        System.out.println("Moves: " + Arrays.toString(moves));

    }


    /**
     * Resets the game to its default state.
     */
    public void resetGame()
    {
        this.marbleSize = 10;
        this.isHumanTurn = true;
        this.moves.clear();
    }


    /**
     * Undo the last move the user took.
     */
    public void undoLastMove()
    {
        System.out.println("Code not yet implemented \n");
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