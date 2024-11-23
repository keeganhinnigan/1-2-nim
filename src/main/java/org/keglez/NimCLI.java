package org.keglez;

import java.io.IOException;
import java.util.Scanner;

public class NimCLI
{
    // Global Attributes
    private NimGame game;
    private Scanner reader;


    /**
     *  Constructor method for the NimCLI.
     */
    public NimCLI() throws IOException
    {
        // Choose game mode/strategy.
        String gameMode = chooseStrategy();


        // Setup players.
        Player player1 = new Player("Human", new HumanUserStrategy());
        Player player2 = new Player("Computer", setGameMode(gameMode));


        // Initialize the game.
        this.game = new NimGame(player1, player2);
        startGame();
    }


    /**
     * CLI menu to choose the strategy of the game.
     * @return chosen strategy.
     */
    private String chooseStrategy()
    {
        reader = new Scanner(System.in);

        System.out.println("The Game of 1-2 Nim Assessment!");
        System.out.println("------------------------------");
        System.out.println("Choose a computer strategy:");
        System.out.println("[R] Random\n" + "[Y] Your Strategy");

        return reader.nextLine().toUpperCase();
    }


    /**
     * Set the computer strategy/game mode based on user's chosen option.
     * @param gameMode the strategy the user has chosen.
     * @return the strategy object.
     */
    private MoveStrategy setGameMode(String gameMode)
    {
        MoveStrategy computerStrategy;


        // Select the game mode.
        switch (gameMode)
        {
            case "R":
                computerStrategy = new RandomStrategy();
                System.out.println("You selected Random Computer strategy.");
                break;
            case "Y":
                computerStrategy = new YourStrategy();
                System.out.println("You selected Your Computer strategy.");
                break;
            default:
                computerStrategy = null;
                System.out.println("Invalid option. Exiting.");
                System.exit(0);
        }

        return computerStrategy;
    }


    /**
     *  <p>Manages the different steps of the game.</p>
     *  <ul>Steps:</ul>
     *  <ul>
     *      <li>Display the initial number of marbles.</li>
     *      <li>Loop the game until there is a winner.</li>
     *      <li>When there is a winner, announce the winner.</li>
     *  </ul>
     */
    private void startGame() throws IOException
    {
        System.out.println("\nInitial number of marbles: " + game.getMarbleSize());
        displayMarbles();


        // While there is no winner, display the menu.
        while (!game.checkWinner())
        {
            displayMenu();
        }


        // Announce the winner.
        announceWinner();
    }


    /**
     * Display the game menu and allow user to choose options.
     */
    private void displayMenu() throws IOException
    {
        System.out.println("\nChoose an option: \n"
            + "[M] Make a move\n"
            + "[S] Save game\n"
            + "[L] Load saved game\n"
            + "[U] Undo move\n"
            + "[C] Clear game\n"
            + "[Q] Quit game\n");

        String choice = reader.nextLine().toUpperCase();
        switch (choice) {
            case "M":
                makeMove();
                break;
            case "S":
                game.saveGame();
                break;
            case "L":
                game.loadGame();
                displayMarbles();
                break;
            case "U":
                game.undoLastMove();
                displayMarbles();
                break;
            case "C":
                game.resetGame();
                displayMarbles();
                break;
            case "Q":
                System.out.println("Thank you for playing! Exiting game...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please select again.");
        }
    }


    /**
     * Handle making a move for the human or computer player
     */ 
    private void makeMove()
    {
        if (game.isHumanTurn())
        {
            assignMoveFrom(game.getHumanPlayer()); //human move

            if (!game.checkWinner())
            {
                assignMoveFrom(game.getComputerPlayer()); // computer move
            }
        }
        else
        {
            assignMoveFrom(game.getComputerPlayer());

            if (!game.checkWinner())
            {
                assignMoveFrom(game.getHumanPlayer());
            }
        }
    }


    /**
     * The next player to take marbles.
     * @param player - the player who turn it is next.
     */
    private void assignMoveFrom(Player player)
    {
        System.out.println("\nIt is " + player.getName() + "'s turn to play.");

        int move = player.getMove(game.getMarbleSize());
        game.assignMove(move);

        System.out.println(player.getName() + " takes " + move + " marbles.");

        displayMarbles();
    }


    /**
     *  Display the total number of marbles to the console.
     */
    private void displayMarbles()
    {
        System.out.println("Current number of marbles: " + game.getMarbleSize());


        for (int i = 0; i < game.getMarbleSize(); i++)
        {
            System.out.print("@ ");
            if ((i + 1) % 10 == 0)
            {
                System.out.println();
            }
        }


        System.out.println();
    }


    /**
     * <p>Announce the winner at the end of the game.</p>
     * <u>Method:</u>
     * <ul>
     *     <li>If it is the humans turn, the computer wins.</li>
     *     <li>if it is the computers turn, the human wins.</li>
     * </ul>
     */
    private void announceWinner()
    {
        String winnerName;


        // Winner method.
        if (game.isHumanTurn())
        {
            winnerName = game.getComputerPlayer().getName(); //Computer Wins
        }
        else
        {
            winnerName = game.getHumanPlayer().getName(); //Human Wins
        }


        // Announce the winner in the console.
        System.out.println("*** " + winnerName + " is the winner! ***");
    }


    /**
     * <p>Starts the CLI nim game.</p>
     * @param args program parameters.
     */
    public static void main(String[] args) throws IOException
    {
        NimCLI textUi = new NimCLI();
    }
}
