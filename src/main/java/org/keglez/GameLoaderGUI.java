package org.keglez;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  This class handles the GUI components for the SaveHandler
 *  class. Allows user to view saved games, the game data, and
 *  load saved games.
 *
 * @author Keegan Hinnigan
 * @since 23/01/2025
 * @version 1.0
 */
public class GameLoaderGUI extends JDialog
{
    // Class attributes.
    public SaveHandler saveData;
    private int save = 0;


    /**
     * Construct a new game loader GUI.
     */
    public GameLoaderGUI()
    {
        try
        {
            // Initialize the save handler.
            this.saveData = new SaveHandler("src/main/resources/saves.csv");

            // Set up the frame options.
            setTitle("Game Loader");
            setPreferredSize(new Dimension(400, 300));
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setResizable(false);

            // Don't allow the user to access the main application.
            setModalityType(ModalityType.APPLICATION_MODAL);

            // Add the GUI content.
            add(generateHeader(), BorderLayout.NORTH);
            add(generateSaveData(), BorderLayout.CENTER);

            // Display the frame.
            pack();
            setVisible(true);
        }
        catch (IOException error)
        {
            System.out.println("An error occurred while initializing the game loader:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This method generates the header for the game loader GUI.
     *  Allows the user to recognise what each row means.
     *
     *  @return GUI header.
     */
    private JPanel generateHeader()
    {
        try
        {
            // Setup panel and font.
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            Font font = new Font("Arial", Font.BOLD, 14);

            // Create title.
            JLabel title = new JLabel("Load Game");
            title.setFont(font);

            // Return the panel.
            panel.add(title);
            return panel;
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while generating the loader header GUI:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This method generates all the save data information into a GUI display,
     *  so that the user is able to choose which game they want to load.
     *
     *  @return Save data GUI.
     */
    private JPanel generateSaveData()
    {
        try
        {
            // Create an ArrayList to iterate through.
            ArrayList<String[]> data = saveData.returnSaveData();

            // Setup new panel and layout.
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(data.size(), 5));

            // Set headings from save file.
            panel.add(new JLabel(data.getFirst()[1]));
            panel.add(new JLabel(data.getFirst()[2]));
            panel.add(new JLabel(data.getFirst()[3]));
            panel.add(new JLabel(data.getFirst()[4]));
            panel.add(new JLabel("")); // No label for load game.

            // Generate all save data items.
            for (int i = 1; i < data.size(); i++) {
                panel.add(new JLabel(data.get(i)[1]));
                panel.add(new JLabel(data.get(i)[2]));
                panel.add(new JLabel(data.get(i)[3]));
                panel.add(new JLabel(data.get(i)[4]));

                int id = Integer.parseInt(data.get(i)[0]);
                JButton button = new JButton("Load");
                button.addActionListener((ActionEvent event) -> setSave(id));

                panel.add(button);
            }

            // Return the panel.
            return panel;
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while generating the loader save data GUI:");
            throw new RuntimeException(error);
        }
    }


    /**
     * This method gives each save an ID, so that an action listener can refer to it
     * when a user tries to load the save.
     *
     * @param id The identity of the save.
     */
    private void setSave(int id)
    {
        try
        {
            this.save = id;
            setVisible(false);
        }
        catch (Exception error)
        {
            throw new RuntimeException(error);
        }
    }


    /**
     * Gets the save ID that the user has chosen.
     *
     * @return Save number.
     */
    public int getSave()
    {
        try
        {
            return this.save;
        }
        catch (Exception e)
        {
            System.out.println();
            throw new RuntimeException(e);
        }
    }
}
