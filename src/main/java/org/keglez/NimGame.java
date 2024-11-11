package org.keglez;


/**
 * This class controls the functions of the Nim game.
 */
public class NimGame
{
    private Player humanPlayer;
    private Player computerPlayer;
    private boolean isHumanTurn;
    private int marbleSize;

    public NimGame(Player humanPlayer, Player computerPlayer) {
        this.marbleSize = 10;
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
        this.isHumanTurn = true;
    }

    public void assignMove(int removeAmount) {
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


    public void saveGame() {
        System.out.println("Code not yet implemented\n");
    }

    public void loadGame() {
        System.out.println("Code not yet implemented \n");
    }

    public void undoLastMove() {
        System.out.println("Code not yet implemented \n");
    }

    public void resetGame() {
        System.out.println("Code not yet implemented \n");
    }

    public Player getHumanPlayer() {
        return humanPlayer;
    }

    public Player getComputerPlayer(){
        return computerPlayer;
    }

    public int getMarbleSize() {
        return marbleSize;
    }

    public boolean isHumanTurn() {
        return isHumanTurn;
    }

}
