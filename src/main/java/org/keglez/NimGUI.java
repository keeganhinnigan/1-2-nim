package org.keglez;

import org.keglez.nimgui.GameLoaderGUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class generates a GUI version of the 1-2 Nim game.
 */
public class NimGUI extends JFrame
{
    private JPanel gamePanel;
    private final JFrame frame;
    private final Border padding;
    private TextArea gameLog;

    // ToolBar Features
    private JToolBar toolBar;
    private JButton newButton;
    private JButton saveButton;
    private JButton undoButton;
    private JButton removeOneButton;
    private JButton removeTwoButton;


    private NimCanvas   nim; // Game instance.
    private NimGame     game;


    /**
     * Append a new message to the game log.
     * @param message To be displayed on the game log.
     */
    public void setGameLog(String message)
    {
        this.gameLog.append("\n" + message);
    }

    private void startGame()
    {
        // Setup players.
        Player player1 = new Player("Human", new HumanUserStrategy());
        Player player2 = new Player("Computer", new RandomStrategy());

        // Initialize the game.
        this.game = new NimGame(player1, player2);
        game.setHumanTurn(true);
    }

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


        // Setup game panel.
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
     * Make a move.
     * @param amount Number of matchsticks to remove.
     */
    public void makeMove(int amount)
    {
        if (game.isHumanTurn())
        {
            game.setHumanTurn(true);

            game.assignMove(amount);
            nim.removeMatchStick(amount);

            this.gameLog.append("\n" + game.getCurrentPlayerName() + " takes " + amount + " marbles.");

            // Make sure that the user cannot remove two matchsticks
            // if there is one left.
            if(game.getMarbleSize() == 1)
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
            game.setHumanTurn(false);

            int move = game.getComputerPlayer().getMove(game.getMarbleSize());
            game.assignMove(move);

            // Delay the computer.

            nim.removeMatchStick(move);

            // Make sure that the user cannot remove two matchsticks
            // if there is one left.
            if (game.getMarbleSize() == 1)
            {
                this.removeTwoButton.setEnabled(false);
            }

            this.gameLog.append("\n" + game.getCurrentPlayerName() + " takes " + move + " marbles.");

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
     * This method starts a new game.
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
            gameLog.append("\nStarted new game!");

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
                int matchsticks = game.getMarbleSize();
                this.nim.load(matchsticks);

                // Notify of loaded save to the game log.
                this.gameLog.append("\nLoaded save " + id + ".");

                // Set the undo button to its correct state.
                this.setUndoButton();
            }
            else
            {
                // Notify that there was no save chosen to the game log.
                this.gameLog.append("\nNo save chosen.");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error loading game:");
            System.out.println(e.getMessage());
        }
    }


    /**
     * This method saves the current game.
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
     * If there are 10 piles, disable the undo button.
     * If there are less than 10 piles, enable the undo button.
     * If there are 0 piles, disable the undo button.
     */
    private void setUndoButton()
    {
        int pileSize = game.getMarbleSize();

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
     * Undo the last move made by the user and computer.
     */
    private void undoLastMove()
    {
        try
        {
            // Call undo function and get new matchstick size.
            game.undoLastMove();
            int pileSize = game.getMarbleSize();

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
     * Sets the game mode/computer strategy.
     */
    private void setGameMode(GameMode gameMode)
    {
        // Initialize a new strategy.
        MoveStrategy strategy;

        // Check game mode, and set appropriate strategy.
        switch (gameMode)
        {
            case GameMode.EASY:
                strategy = new YourStrategy();
                this.gameLog.append("\nSet game mode to easy.");
                break;

            case MEDIUM:
                strategy = new RandomStrategy();
                this.gameLog.append("\nSet game mode to medium.");
                break;

            case HARD:
                strategy = new RandomStrategy();
                this.gameLog.append("\nSet game mode to hard.");
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + gameMode);
        }

        // Set the new computer player strategy.
        game.setComputerPlayerStrategy(strategy);
    }


    /**
     * Exit the game.
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
     * Main function will run the base for the GUI.
     * @param args takes any arguments for main.
     */
    public static void main(String[] args)
    {
        NimGUI window = new NimGUI();
    }


    /**
     * This inner class provides all the game graphics for 1-2 nim as a canvas.
     * @author Keegan
     * @version 11/11/2024
     */
    private static class NimCanvas extends JComponent
    {
        // Attributes
        private int width, height;
        private Graphics2D graphics;
        private ArrayList<MatchStick> matchSticks;


        /**
         * Constructor method for NimCanvas.
         * @param width - the preferred width of the canvas.
         * @param height - the preferred height of the canvas.
         */
        public NimCanvas(int width, int height)
        {
            this.width = width;
            this.height = height;

            // Generate match sticks.
            generateMatchSticks();

            // Set the size of the nim canvas.
            setPreferredSize(new Dimension(width, height));
        }

        /**
         *  This will generate match sticks.
         */
        public void generateMatchSticks()
        {
            this.matchSticks = new ArrayList<MatchStick>();

            // Number of rows and spacing settings for the pyramid
            int totalRows = 4;  // for 10 match sticks: 4 rows. For 15 match sticks: 5 rows.
            int matchstickWidth = 6;
            int matchstickHeight = 60;
            int verticalSpacing = 10;
            int horizontalSpacing = 20;

            // Loop to create each row of matchsticks.
            for (int row = 0; row < totalRows; row++) {

                int sticksInRow = totalRows - row;  // Decrease the number of matchsticks in each row
                int rowY = (row * (-matchstickHeight + -verticalSpacing)) + (totalRows*matchstickHeight);  // Calculate y position for the row

                // Calculate starting x to center the row on the canvas
                int rowWidth = sticksInRow * matchstickWidth + (sticksInRow - 1) * horizontalSpacing;
                int startX = (this.width - rowWidth) / 2;

                // Draw matchsticks for the current row
                for (int stick = 0; stick < sticksInRow; stick++) {
                    int stickX = startX + stick * (matchstickWidth + horizontalSpacing);
                    this.matchSticks.add(new MatchStick(stickX, rowY));
                }
            }
        }
        private String winner = "";
        public void setWinner(String name)
        {
            this.winner = name;
        }

        /**
         * Renders the scene in its current state.
         * @param g the <code>Graphics</code> object to protect
         */
        @Override
        public void paintComponent(Graphics g)
        {
            // Cast swing graphics to graphics 2D.
            super.paintComponent(g);
            this.graphics = (Graphics2D) g;


            // Draw nim game background.
            Rectangle bounds = new Rectangle(0, 0, getWidth(), getHeight());
            graphics.setColor(new Color(0,150,0));
            graphics.fill(bounds);


            // Draw game text.
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.BOLD, 20));
            graphics.drawString("Matchsticks: " + this.matchSticks.size(), 10, 30);


            // Check if a winner has been found.
            if(!this.winner.isEmpty())
            {
                this.graphics.setColor(Color.YELLOW);
                this.graphics.setFont(new Font("Arial", Font.BOLD, 30));
                this.graphics.drawString(this.winner + " is the winner!", (width/4)-30, height/2);
            }

            // Draw all match sticks in the match stick list.
            for (MatchStick stick : this.matchSticks)
            {
                drawMatchStick(stick.getXPosition(), stick.getYPosition());
            }
        }


        /**
         * Removes a quantity of match sticks from the pile.
         * @param quantity - the number of match sticks to remove.
         */
        public void removeMatchStick(int quantity)
        {
            for (int loop = 0; loop < quantity; loop++)
            {
                this.matchSticks.removeFirst();
            }

            repaint();
        }


        /**
         * Draw a match stick on the nim game canvas to a specified position.
         * @param xPosition - The X position of the match on the screen.
         * @param yPosition - The Y position of the match on the screen.
         */
        public void drawMatchStick(int xPosition, int yPosition)
        {
            // Top of the match.
            this.graphics.setColor(new Color(150,0,0)); // Green
            this.graphics.fillOval(xPosition-2, yPosition, 10, 10);

            // Bottom of the match.
            this.graphics.setColor(new Color(255,255,185)); // Beige
            this.graphics.fillRect(xPosition, yPosition+10, 6, 50);
        }

        public void load(int matchsticks)
        {
            // Generate new set of matchsticks.
            generateMatchSticks();

            // Remove matchsticks.
            removeMatchStick(10 - matchsticks);

            // Repaint the canvas.
            repaint();
        }


        /**
         * Get the width of the canvas.
         * @return width of the canvas.
         */
        public int getWidth()
        {
            return width;
        }


        /**
         * Set the width of the canvas.
         * @param width of the canvas.
         */
        public void setWidth(int width)
        {
            this.width = width;
        }


        /**
         * Set height of the canvas.
         * @return height of the canvas.
         */
        public int getHeight()
        {
            return height;
        }


        /**
         * Set the height of the canvas.
         * @param height of the canvas.
         */
        public void setHeight(int height)
        {
            this.height = height;
        }


        /**
         * This inner class represents a match stick for the 1-2 nim game. It
         * holds the coordinate information of the match stick so it can be
         * stored in a list and rendered where required.
         *
         * @author Keegan Hinnigan
         * @version 12/11/2024
         */
        private static class MatchStick
        {
            // Local attributes.
            private int xPosition;
            private int yPosition;


            /**
             * This represents a match stick for the 1-2 nim game.
             * @param xPosition x position on the screen.
             * @param yPosition y position on the screen.
             */
            public MatchStick(int xPosition, int yPosition)
            {
                this.xPosition = xPosition;
                this.yPosition = yPosition;
            }


            /**
             * Get the x position of the match stick.
             * @return x position.
             */
            public int getXPosition()
            {
                return xPosition;
            }


            /**
             * Set the x position of the match stick.
             * @param xPosition sets new position.
             */
            public void setXPosition(int xPosition)
            {
                this.xPosition = xPosition;
            }


            /**
             * Get the y position of the match stick.
             * @return y position.
             */
            public int getYPosition()
            {
                return yPosition;
            }


            /**
             * Set the y position of the match stick.
             * @param yPosition sets new position.
             */
            public void setYPosition(int yPosition)
            {
                this.yPosition = yPosition;
            }
        }
    }
}