package org.keglez;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.IOException;


/**
 * NimGUI class generates all user interface components for the NimGame class. Includes
 * the NimCanvas class, which processes nim game graphics.
 *
 * @author Keegan Hinnigan
 * @version 1.0
 * @since 23/01/2025
 */
public class NimGUI extends JFrame
{
    private final JFrame frame;
    private final Border padding;
    private final TextArea gameLog;

    // ToolBar Features
    private final JToolBar toolBar;
    private JButton undoButton;
    private JButton removeOneButton;
    private JButton removeTwoButton;


    private NimCanvas nim; // Game graphics
    private NimGame game;


    /**
     * This method will generate the main GUI for the game.
     */
    public NimGUI()
    {
        startGame();

        // Set default padding.
        this.padding = BorderFactory.createEmptyBorder(5,5,5,5);

        // Setup toolbar.
        this.toolBar = new JToolBar();
        this.toolBar.setFloatable(false);

        // Setup game graphics.
        nim = new NimCanvas(500, 400);
        nim.setBorder(padding);

        // Setup game log.
        this.gameLog = new TextArea("Welcome to 1-2 nim!");
        this.gameLog.setPreferredSize(new Dimension(500, 100));
        this.gameLog.setEditable(false);

        // Generate the frame.
        frame = new JFrame("1-2 Nim Game");

        // Add components.
        frame.setJMenuBar(createMenuBar());
        createToolbarOptions();
        createGameModeSelector();

        // Frame components.
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(nim, BorderLayout.CENTER);
        frame.add(gameLog, BorderLayout.SOUTH);

        // Disable the undo button on fresh launch.
        setUndoButton();

        // Set the default game mode to easy.
        setGameMode(GameMode.EASY);

        // Frame settings.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Start the game.
     */
    private void startGame()
    {
        // Setup players.
        Player player1 = new Player("Human", new HumanUserStrategy());
        Player player2 = new Player("Computer", new RandomStrategy());

        // Initialize the game.
        this.game = new NimGame(player1, player2);
        game.setIsHumanTurn(true);
    }


    /**
     *  Generates all the toolbar options for the game. Users
     *  can create new game, load a game, save a game, undo
     *  a move, and exit the game.
     */
    public void createToolbarOptions()
    {
        // Create panels and labels.
        JPanel optionPanel = new JPanel();
        optionPanel.setBorder(this.padding);
        optionPanel.setLayout(new GridLayout(2,1));
        optionPanel.setPreferredSize(new Dimension(350, 60));

        JLabel optionLabel = new JLabel("Game Options");
        optionLabel.setBorder(this.padding);


        // Generate buttons.
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(1,5));
        buttonContainer.setAlignmentX(Component.LEFT_ALIGNMENT);


        // Remove one match stick button.
        this.removeOneButton = new JButton("Remove 1");
        this.removeOneButton.addActionListener(e -> makeMove(1));
        buttonContainer.add(this.removeOneButton);


        // Remove two match sticks button.
        this.removeTwoButton = new JButton("Remove 2");
        this.removeTwoButton.addActionListener(e -> makeMove(2));
        buttonContainer.add(this.removeTwoButton);


        // Undo move button.
        this.undoButton = new JButton("Undo");
        this.undoButton.addActionListener(e -> undoLastMove());
        buttonContainer.add(this.undoButton);


        // Setup option panel & add to toolbar.
        optionPanel.add(optionLabel);
        optionPanel.add(buttonContainer);
        this.toolBar.add(optionPanel);
    }


    /**
     * Generates a game mode combo box. Allows the user to choose
     * between different game modes.
     */
    public void createGameModeSelector()
    {
        // Setup panel.
        JPanel gameModeSelector = new JPanel();
        gameModeSelector.setLayout(new GridLayout(2,1));

        gameModeSelector.setBorder(this.padding);
        gameModeSelector.setPreferredSize(new Dimension(150, 60));

        // Game mode selection.
        JLabel textPane = new JLabel("Game Mode");
        textPane.setBorder(this.padding);

        // Setup Combo Box
        JComboBox<String> gameMode = new JComboBox<>();
        gameMode.addItem("Easy");
        gameMode.addItem("Hard");

        gameMode.addActionListener(e ->
        {
            String s = (String) gameMode.getSelectedItem();

            switch (s)
            {
                case "Easy":
                    setGameMode(GameMode.EASY);
                    break;
                case "Hard":
                    setGameMode(GameMode.HARD);
                    break;
                case null:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + s);
            }
        });

        gameModeSelector.add(textPane);
        gameModeSelector.add(gameMode);

        this.toolBar.add(gameModeSelector);
    }


    /**
     * Creates the menu bar with "File" and "Options" menus.
     * @return the JMenuBar for the frame
     */
    private JMenuBar createMenuBar()
    {
        // Create method attributes.
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(false);


        // Create file menu.
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem loadGameItem = new JMenuItem("Load Game");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");


        // Add action listeners to each menu item.
        newGameItem.addActionListener(e -> startNewGame());
        loadGameItem.addActionListener(e -> loadGame());
        saveItem.addActionListener(e -> saveGame());
        exitItem.addActionListener(e -> exitGame());  // Exit the application


        // Add items to the file menu.
        fileMenu.add(newGameItem);
        fileMenu.add(loadGameItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();  // Adds a separator line
        fileMenu.add(exitItem);

        // Add the menus to the menu bar.
        menuBar.add(fileMenu);

        // Return the menu bar.
        return menuBar;
    }


    /**
     *  Allow either the human or player to make a move.
     *
     *  @param amount Number of matchsticks to remove.
     */
    public void makeMove(int amount)
    {
        if (game.getIsHumanTurn())
        {
            game.setIsHumanTurn(true);

            game.assignMove(amount);
            nim.removeMatchStick(amount);

            setGameLog("\n" + game.getCurrentPlayerName() + " takes " + amount + " marbles.");

            // Make sure that the user cannot remove two matchsticks
            // if there is one left.
            if(game.getMatchStickSize() == 1)
            {
                this.removeTwoButton.setEnabled(false);
            }

            // Check if human is a winner.
            if(game.checkWinner())
            {
                nim.setWinner("Human");
                this.removeOneButton.setEnabled(false);
                this.removeTwoButton.setEnabled(false);
            }
            else
            {
                makeMove(0);
            }

        }
        else
        {
            game.setIsHumanTurn(false);

            int move = game.getComputerPlayer().getMove(game.getMatchStickSize());

            // Delay the computer.
            game.assignMove(move);
            nim.removeMatchStick(move);

            // Make sure that the user cannot remove two matchsticks
            // if there is one left.
            if (game.getMatchStickSize() == 1)
            {
                this.removeTwoButton.setEnabled(false);
            }

            setGameLog("\n" + game.getCurrentPlayerName() + " takes " + move + " marbles.");

            // Check if human is a winner.
            if (game.checkWinner())
            {
                nim.setWinner("Computer");
                this.removeOneButton.setEnabled(false);
                this.removeTwoButton.setEnabled(false);
            }
        }

        // Check if the undo button should be enabled after making move.
        setUndoButton();
    }


    /**
     *  This method starts a new game.
     */
    private void startNewGame()
    {
        try
        {
            // Reset the game and manage graphics.
            game.resetGame();
            setGameMode(GameMode.EASY);
            nim.load(10);
            nim.setWinner("");

            // Notify of new game to the game log.
            setGameLog("\nStarted new game!");

            // Set button states.
            this.removeOneButton.setEnabled(true);
            this.removeTwoButton.setEnabled(true);
            this.setUndoButton();
        }
        catch (Exception error)
        {
            System.out.println("Error starting new game:");
            System.out.println(error.getMessage());
        }
    }


    /**
     *  This method opens the load game popup menu.
     */
    private void loadGame()
    {
        try
        {
            // Initialize a new game loader menu.
            GameLoaderGUI loader = new GameLoaderGUI();

            // If a save was chosen, continue.
            if (loader.getSave() > 0)
            {
                // Load the game by its ID.
                int id = loader.getSave();
                this.game.loadGame(id);

                // Manage canvas.
                int matchsticks = game.getMatchStickSize();
                this.nim.load(matchsticks);

                // Notify of loaded save to the game log.
                setGameLog("\nLoaded save " + id + ".");

                // Set the undo button to its correct state.
                this.setUndoButton();
            }
            else
            {
                // Notify that there was no save chosen to the game log.
                setGameLog("\nNo save chosen.");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error loading game:");
            System.out.println(e.getMessage());
        }
    }


    /**
     *  This method saves the current game.
     */
    private void saveGame()
    {
        try
        {
            this.game.saveGame();
            JOptionPane.showMessageDialog(this, "Game saved successfully!");
        }
        catch (IOException error)
        {
            System.out.println("Error saving the game:");
            System.out.println(error.getMessage());
        }
    }

    /**
     * This method manages the undo button, and disables it where necessary.
     *
     * <ul>Method:</ul>
     * <ul>
     *     <li>If there are 10 piles, disable the undo button.</li>
     *     <li>If there are less than 10 piles, enable the undo button.</li>
     *     <li>If there are 0 piles, disable the undo button.</li>
     * </ul>
     */
    private void setUndoButton()
    {
        int pileSize = game.getMatchStickSize();

        switch (pileSize)
        {
            case 0, 10:
                this.undoButton.setEnabled(false);
                break;
            default:
                this.undoButton.setEnabled(true);
        }
    }

    /**
     *  This method undoes the last move made by the user and computer.
     */
    private void undoLastMove()
    {
        try
        {
            // Call undo function and get new matchstick size.
            game.undoLastMove();
            int pileSize = game.getMatchStickSize();

            // Enable the undo button if the pile size is less than 10.
            setUndoButton();

            // Update the game graphics.
            nim.load(pileSize);
        }
        catch (Exception error)
        {
            System.out.println("Error undoing last move:");
            System.out.println(error.getMessage());
        }
    }


    /**
     *  Sets the game mode/computer strategy based on user input.
     *
     *  @param gameMode The <code>GameMode</code> to set.
     */
    private void setGameMode(GameMode gameMode)
    {
        // Initialize a new strategy.
        MoveStrategy strategy;

        // Check game mode, and set appropriate strategy.
        switch (gameMode)
        {
            case GameMode.EASY:
                strategy = new RandomStrategy();
                setGameLog("\nSet game mode to easy. (Random Moves)");
                break;
            case MEDIUM:
                strategy = new ModuloStrategy();
                setGameLog("\nSet game mode to medium (Modulo).");
                break;
            case HARD:
                strategy = new PreDefinedStrategy();
                setGameLog("\nSet game mode to hard. (Pre-Defined Path)");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + gameMode);
        }

        // Set the new computer player strategy.
        game.setComputerPlayerStrategy(strategy);
    }

    /**
     * Append a new message to the game log.
     *
     * @param message Message to be displayed.
     */
    public void setGameLog(String message)
    {
        try
        {
            this.gameLog.append(message);
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while appending to the game log:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  Exit the game and close the window.
     */
    public void exitGame()
    {
        try
        {
            frame.dispose();
            System.exit(0);
        }
        catch (Exception error)
        {
            System.out.println("Error exiting game:");
            System.out.println(error.getMessage());
        }
    }


    /**
     * Main function will run the GUI version of nim.
     *
     * @param args takes any arguments for main.
     */
    public static void main(String[] args)
    {
        new NimGUI();
    }
}