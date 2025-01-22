package org.keglez;

import java.io.IOException;
import java.util.Scanner;

/**
 *  This class allows for the 1-2 nim game to played in a console/terminal.
 *  As this is the case, it has its own <code>static void main(String[] args)</code>
 *  method, as it should run independently to the GUI.
 *
 *  @author Keegan Hinnigan
 *  @since 23/01/2025
 *  @version 1.0
 */
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
        try
        {
            // Choose game mode/strategy.
            String gameMode = chooseStrategyMenu();

            // Setup players.
            Player player1 = new Player("Human", new HumanUserStrategy());
            Player player2 = new Player("Computer", setStrategy(gameMode)); // Set game mode.

            // Initialize the game.
            this.game = new NimGame(player1, player2);
            startGame();
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while constructing the CLI version of NIM:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This method provides a CLI menu to choose the strategy of the game.
     *
     *  @return Chosen strategy.
     */
    private String chooseStrategyMenu()
    {
        try
        {
            reader = new Scanner(System.in);

            System.out.println("The Game of 1-2 Nim Assessment!");
            System.out.println("------------------------------");
            System.out.println("Choose a computer strategy:");
            System.out.println("[R] Random\n" + "[Y] Your Strategy");

            return reader.nextLine().toUpperCase();
        }
        catch (Exception e)
        {
            System.out.println("An error occurred while choosing a strategy for the game:");
            throw new RuntimeException(e);
        }
    }


    /**
     *  This method sets the computer strategy/game mode based on user's
     *  previously chosen option.
     *
     *  @param gameMode The strategy the user has chosen.
     *  @return The strategy object.
     */
    private MoveStrategy setStrategy(String gameMode)
    {
        try {
            // Instantiate a new computer strategy.
            MoveStrategy computerStrategy;

            // Select the game mode.
            switch (gameMode) {
                case "R":
                    computerStrategy = new RandomStrategy();
                    System.out.println("You selected Random Computer strategy.");
                    break;
                case "Y":
                    computerStrategy = new PreDefinedStrategy();
                    System.out.println("You selected Your Computer strategy.");
                    break;
                default:
                    computerStrategy = null;
                    System.out.println("Invalid option. Exiting.");
                    System.exit(0);
            }

            // This will return the set strategy.
            return computerStrategy;
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while setting the strategy: ");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This method starts the game and manages the games different steps.
     *
     *  <ul>Steps:</ul>
     *  <ul>
     *      <li>Display the initial number of marbles.</li>
     *      <li>Loop the game until there is a winner.</li>
     *      <li>When there is a winner, announce the winner.</li>
     *  </ul>
     */
    public void startGame()
    {
        System.out.println("\nInitial number of marbles: " + game.getMatchStickSize());
        displayMatchSticks();

        // While there is no winner, display the menu.
        try
        {
            while (!game.checkWinner())
            {
                displayGameMenu();
            }
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while continuing the game:");
            throw new RuntimeException(e);
        }

        // Announce the winner.
        announceWinner();
    }


    /**
     *  This method displays the game menu and allow user to choose the
     *  options that they desire.
     */
    private void displayGameMenu() throws IOException
    {
        try
        {
            System.out.println("\nChoose an option: \n"
                + "[M] Make a move\n"
                + "[S] Save game\n"
                + "[L] Load saved game\n"
                + "[U] Undo move\n"
                + "[C] Clear game\n"
                + "[Q] Quit game\n");

            String choice = reader.nextLine().toUpperCase();

            switch (choice)
            {
                case "M":
                    makeMove();
                    break;
                case "S":
                    System.out.println(game.saveGame());
                    break;
                case "L":
                    // Display the console menu.
                    game.saveData.displaySaveData();

                    // Get the user input and load the game.
                    int scanId = new Scanner(System.in).nextInt();
                    System.out.println(game.loadGame(scanId));

                    // Display number of marbles.
                    System.out.println();
                    displayMatchSticks();
                    break;
                case "U":
                    System.out.println(game.undoLastMove());
                    displayMatchSticks();
                    break;
                case "C":
                    System.out.println(game.resetGame());
                    displayMatchSticks();
                    break;
                case "Q":
                    System.out.println("Thank you for playing! Exiting game...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
        catch (Exception e)
        {
            System.out.println("An error occurred while choosing a game option:");
            throw new RuntimeException(e);
        }
    }


    /**
     *  Handle making a move for the human or computer player.<br>
     *
     *  Method:
     *  <ul>
     *      <li>If it's the human turn, allow the human to make a move</li>
     *      <li>If it's the computers turn, allow the computer to make a move</li>
     *      <li>Once a move has been made, switch to the next player.</li>
     *  </ul>
     */
    public void makeMove()
    {
        try
        {
            if (game.getIsHumanTurn())
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
        catch (Exception e)
        {
            System.out.println("An error occurred ");
            throw new RuntimeException(e);
        }
    }


    /**
     * This method assigns the next player to make a move/take a match stick.
     *
     * @param player The player who turn it is.
     */
    public void assignMoveFrom(Player player)
    {
        try
        {
            System.out.println("\nIt is " + player.getName() + "'s turn to play.");

            int move = player.getMove(game.getMatchStickSize());
            game.assignMove(move);

            System.out.println(player.getName() + " takes " + move + " marbles.");
        }
        catch (Exception e)
        {
            System.out.println("An error occurred while making a move:");
            throw new RuntimeException(e);
        }

        // Display the match sticks in the console.
        displayMatchSticks();
    }


    /**
     *  This method displays the total number of match sticks left to the console.
     */
    private void displayMatchSticks()
    {
        try
        {
            System.out.println("Current number of match sticks: " + game.getMatchStickSize());

            for (int i = 0; i < game.getMatchStickSize(); i++) {
                System.out.print("i ");

                if ((i + 1) % 10 == 0) {
                    System.out.println();
                }
            }

            System.out.println();
        }
        catch (Exception error)
        {
            System.out.println("An error occurred displaying the match sticks to the console:");
            throw new RuntimeException(error);
        }
    }


    /**
     * Announce the winner at the end of the game.
     * <u>Method:</u>
     * <ul>
     *     <li>If it is the humans turn, the computer wins.</li>
     *     <li>if it is the computers turn, the human wins.</li>
     * </ul>
     */
    public void announceWinner()
    {
        String winnerName;


        // Winner method.
        if (game.getIsHumanTurn())
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
    public static void main(String[] args)
    {
        try
        {
            NimCLI textUi = new NimCLI();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
