package org.keglez;

import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.FileWriter;


/**
 *  The save handler class controls all the saving and loading
 *  of the games.
 *
 *  @author Keegan Hinnigan
 *  @since 23/01/2025
 *  @version 1.0
 */
public class SaveHandler
{
    // Class attributes.
    ArrayList<String[]> saveData;
    String filepath;
    String[] headers;


    /**
     * Save handler constructor method.
     *
     * @param filepath The location of the save data.
     */
    public SaveHandler(String filepath) throws IOException
    {
        try
        {
            this.filepath = filepath;
            this.headers = new String[]{"ID", "Date", "Marble Size", "Human Turn", "Moves"};
            checkSaveDataExists();
        }
        catch (IOException e)
        {
            System.out.println("An issue occurred constructing the SaveHandler");
            throw new RuntimeException(e);
        }
    }


    /**
     * Checks if the save data exists. If there is no save data
     * present, the file will be created and the headings will
     * be added.
     *
     * @throws IOException An error occurred creating or accessing file.
     */
    public void checkSaveDataExists() throws IOException
    {
        // If the file does not exist, create it.
        File file = new File(this.filepath);
        boolean file_created = file.createNewFile();

        // If the file was created, generate the headers.
        if (file_created)
        {
            try (FileWriter writer = new FileWriter(file, true))
            {
                writer.write(String.join(",", headers) + "\n");
            }
        }
    }


    /**
     * Loads an array of the game data for the chosen game ID. If the game
     * save does not exist, an empty ArrayList will be returned.
     *
     * @return game data as an ArrayList.
     */
    public String[] loadGame(int id)
    {
        try
        {
            return this.saveData.get(id);
        }
        catch (Exception error)
        {
            System.out.println("An issue occurred loading the game:");
            throw new RuntimeException(error);
        }
    }


    /**
     * Returns the save data ArrayList
     *
     * @return ArrayList of save data.
     */
    public ArrayList<String[]> returnSaveData()
    {
        if (this.saveData == null)
        {
            try
            {
                getSaveData();
            }
            catch (IOException error)
            {
                System.out.println(error.getMessage());
            }
        }

        return this.saveData;
    }

    /**
     * Gets the save data from the save file and stores each row
     * in an ArrayList.
     *
     * @throws IOException An issue reading the file.
     */
    public void getSaveData() throws IOException
    {
        try
        {
            // Initialise save list and open the file.
            saveData = new ArrayList<>();
            File file = new File(this.filepath);

            // Listen to the file using Scanner.
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(",");

            // Get each row and add it to the ArrayList.
            while (scanner.hasNext()) {
                String[] row = scanner.nextLine().split(","); // Split each line into columns
                this.saveData.add(row);
            }

            // Finished.
            scanner.close();
        }
        catch (Exception e)
        {
            System.out.println("An error occurred getting the save data:");
            throw new RuntimeException(e);
        }
    }


    /**
     * Displays the save data in the console for the user to read.
     *
     * @throws IOException An issue reading the file.
     */
    public void displaySaveData() throws IOException
    {
        try
        {
            // Get latest save data.
            getSaveData();

            // Display save data in the console.
            System.out.println("Saved Game Data:");

            for (String[] row : saveData) {
                // Format each cell
                for (String cell : row) System.out.printf("%-25s", cell);

                // Start new row.
                System.out.println();
            }

            System.out.print("\nChoose an option: ");
            System.out.print("\n[E] Exit ");
            System.out.print("\n[ID] Choose a game ID ");
        }
        catch (Exception e)
        {
            System.out.println("An error occurred displaying the save data:");
            throw new RuntimeException(e);
        }
    }


    /**
     * Counts the number of lines/saves in the save file.
     *
     * @param file The file to count the lines
     * @return The line count.
     * @throws IOException An issue reading the file.
     */
    public Integer countLines(File file) throws IOException
    {
        int lineCount = 0;

        try(Scanner scanner = new Scanner(file);)
        {
            while (scanner.hasNextLine())
            {
                scanner.nextLine(); // Read the next line
                lineCount++;
            }
        }
        catch (FileNotFoundException e)
        {
            return e.hashCode();
        }

        return lineCount;
    }


    /**
     * Adds a new row of game data to the saves.csv file based on parameters given.
     *
     * @throws IOException If there is an issue appending to the file.
     */
    public void append(String[] saveData) throws IOException
    {
        // Format the save data.
        String[] data = new String[5];

        // Load the save file.
        File file = new File(this.filepath);

        try
        {
            // Get the date.
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String fileDate = formatter.format(date);

            data[0] = countLines(file).toString(); // Id.
            data[1] = fileDate; // Set a date.
            data[2] = saveData[0]; // Marble size.
            data[3] = saveData[1]; // Human turn.

            // Set the moves, if empty, set to 0.
            if (saveData[2].isEmpty())
            {
                data[4] = "0"; // Moves.
            }
            else
            {
                data[4] = saveData[2];
            }
        }
        catch (Exception error)
        {
            System.out.println("An error occurred parsing the data:");
            throw new RuntimeException(error);
        }

        // Write the data, split with delimiter.
        try (FileWriter writer = new FileWriter(file, true))
        {
            writer.write(String.join(",", data) + "\n");
        }
        catch (IOException error)
        {
            System.out.println("An error occurred writing the data:");
            throw new RuntimeException(error);
        }
    }
}
