package org.keglez;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;


/**
 * This class generates a GUI version of the 1-2 Nim game.
 */
public class GUI extends JFrame
{
    private JFrame frame;
    private JPanel gamePanel;
    private Border padding;
    private TextArea gameLog;

    // ToolBar Features
    private JToolBar toolBar;
    private JButton newButton;
    private JButton saveButton;
    private JButton undoButton;
    private JComboBox gameMode;

    private NimCanvas game; // Game instance.

    /**
     * This method will generate the main GUI for the game.
     */
    public GUI()
    {
        // Set default padding.
        this.padding = BorderFactory.createEmptyBorder(5,5,5,5);

        // Setup toolbar.
        this.toolBar = new JToolBar();
        this.toolBar.setFloatable(false);


        // Setup game panel.
        game = new NimCanvas(500, 400);
        game.setBorder(padding);


        // Setup game log.
        this.gameLog = new TextArea("Welcome to 1-2 nim!\n");
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
        frame.add(game, BorderLayout.CENTER);
        frame.add(gameLog, BorderLayout.SOUTH);

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


        // Generate game option buttons.
        JButton removeOneButton = new JButton("Remove 1");
        buttonContainer.add(removeOneButton);

        JButton removeTwoButton = new JButton("Remove 2");
        buttonContainer.add(removeTwoButton);

        JButton undoButton = new JButton("Undo");
        buttonContainer.add(undoButton);


        // Setup option panel & add to toolbar.
        optionPanel.add(optionLabel);
        optionPanel.add(buttonContainer);
        this.toolBar.add(optionPanel);


        // Add action listeners.
        removeOneButton.addActionListener(e -> this.game.removeMatchStick(1));
        removeTwoButton.addActionListener(e -> this.game.removeMatchStick(2));
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

        String[] selection = {"Easy", "Hard"};
        this.gameMode = new JComboBox(selection);

        gameModeSelector.add(textPane);
        gameModeSelector.add(this.gameMode);

        this.toolBar.add(gameModeSelector);
    }


    /**
     * Creates the menu bar with "File" and "Options" menus.
     * @return the JMenuBar for the frame
     */
    private JMenuBar createMenuBar() {

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
        loadGameItem.addActionListener(e -> startNewGame());
        saveItem.addActionListener(e -> saveGame());
        exitItem.addActionListener(e -> exitGame());  // Exit the application

        // Add items to the file menu.
        fileMenu.add(newGameItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();  // Adds a separator line
        fileMenu.add(exitItem);


        // Create options menu.
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem settingsItem = new JMenuItem("Settings");
        JMenuItem helpItem = new JMenuItem("Help");

        // Add action listeners to each menu item.
        settingsItem.addActionListener(e -> openSettings());
        helpItem.addActionListener(e -> showHelp());

        // Add items to the options menu.
        optionsMenu.add(settingsItem);
        optionsMenu.add(helpItem);


        // Add the menus to the menu bar.
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);


        // Return the menu bar.
        return menuBar;
    }


    // Placeholder methods for menu actions
    private void startNewGame() {
        JOptionPane.showMessageDialog(this, "Starting a new game!");
    }


    private void saveGame() {
        JOptionPane.showMessageDialog(this, "Game saved successfully!");
    }


    private void openSettings() {
        JOptionPane.showMessageDialog(this, "Open settings dialog...");
    }


    private void showHelp() {
        JOptionPane.showMessageDialog(this, "Help dialog...");
    }


    /**
     * Exit the game.
     */
    public void exitGame()
    {
        frame.dispose();
    }


    /**
     * Main function will run the base for the GUI.
     * @param args takes any arguments for main.
     */
    public static void main(String[] args)
    {
        GUI window = new GUI();
    }
}