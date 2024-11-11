package org.keglez;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;


/**
 * This class generates a GUI version of the 1-2 Nim game.
 */
public class GUI
{
    private JFrame frame;
    private JPanel gamePanel;
    private Border padding;
    private TextArea gameLog;

    // ToolBar Features
    private JToolBar toolBar;
    private JButton newButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton undoButton;
    private JComboBox gameMode;


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
        this.gamePanel = new JPanel();
        this.gamePanel.setPreferredSize(new Dimension(500, 300));
        this.gamePanel.setBackground(Color.WHITE);
        this.gamePanel.setBorder(padding);

        // Setup game log.
        this.gameLog = new TextArea("Welcome to 1-2 nim!\n");
        this.gameLog.setPreferredSize(new Dimension(500, 100));
        this.gameLog.setEditable(false);

        // Generate the frame.
        frame = new JFrame("1-2 Nim Game");


        // Add components.
        createToolbarOptions();
        createGameModeSelector();

        // Frame components.
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(gamePanel, BorderLayout.CENTER);
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

        JLabel optionLabel = new JLabel("Options");
        optionLabel.setBorder(this.padding);


        // Generate buttons.
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(1,4));
        buttonContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.newButton = new JButton("New");
        this.loadButton = new JButton("Load");
        this.saveButton = new JButton("Save");
        this.undoButton = new JButton("Undo");

        buttonContainer.add(newButton);
        buttonContainer.add(loadButton);
        buttonContainer.add(saveButton);
        buttonContainer.add(undoButton);

        optionPanel.add(optionLabel);
        optionPanel.add(buttonContainer);

        // Add options to the toolbar.
        this.toolBar.add(optionPanel);


        // Action Listeners
        newButton.addActionListener(e -> exitFrame());
        loadButton.addActionListener(e -> exitFrame());
        saveButton.addActionListener(e -> exitFrame());
        undoButton.addActionListener(e -> exitFrame());
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
     * Display the game graphics.
     */
    private void gameView()
    {
        JPanel game = new JPanel();

        game.setPreferredSize(new Dimension(500, 500));
        game.setBorder(this.padding);

        this.frame.add(game, BorderLayout.CENTER);
    }

    /**
     * Exit the application.
     */
    public void exitFrame()
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