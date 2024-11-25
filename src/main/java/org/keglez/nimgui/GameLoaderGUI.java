package org.keglez.nimgui;

import org.keglez.SaveHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

/**
 * This class manages the game loader GUI component.
 */
public class GameLoaderGUI extends JDialog
{
    SaveHandler saveData;


    /**
     * Construct a new game loader GUI component.
     */
    public GameLoaderGUI()
    {
        try
        {
            // Initialize the save data.
            this.saveData = new SaveHandler("src/main/resources/saves.csv");

            // Set up the frame.
            setTitle("Game Loader");
            setPreferredSize(new Dimension(400, 300));
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setResizable(true);

            setModalityType(ModalityType.APPLICATION_MODAL);
            setModal(true);

            add(generateHeader(), BorderLayout.NORTH);
            add(generateSaveData(), BorderLayout.CENTER);

            // Display the frame.
            pack();
            setVisible(true);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     * This method generates a header for the load menu.
     */
    private JPanel generateHeader()
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


    private JPanel generateSaveData()
    {
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
        for (int i = 1; i < data.size(); i++)
        {
            panel.add(new JLabel(data.get(i)[1]));
            panel.add(new JLabel(data.get(i)[2]));
            panel.add(new JLabel(data.get(i)[3]));
            panel.add(new JLabel(data.get(i)[4]));

            int id = Integer.parseInt(data.get(i)[0]);
            JButton button = new JButton("Load");
            button.addActionListener((ActionEvent e) -> setSave(id));

            panel.add(button);
        }

        return panel;
    }

    // Some stuff...
    private int save = 0;

    private void setSave(int id)
    {
        this.save = id;
        setVisible(false);
    }

    public int getSave()
    {
        return this.save;
    }


    /**
     * Listens for any even listeners to be activated.
     */
    public String[] listen()
    {
        return new String[0];
    }
}
