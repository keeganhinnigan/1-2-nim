package org.keglez;

import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.FileWriter;


public class SaveHandler
{
    ArrayList<String[]> saveData;
    String filepath;
    String[] headers;


    /**
     * Save handler constructor method.
     * @param filepath The location of the save data.
     */
    public SaveHandler(String filepath) throws IOException
    {
        this.filepath = filepath;
        this.headers = new String[]{"ID", "Date", "Marble Size", "Human Turn", "Moves"};
        checkSaveDataExists();
    }


    /**
     * Checks if the save data exists. If there is no save data
     * present, the file will be created and the headings will
     * be added.
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
     * save does not exist, an empty ArrayList<> will be returned.
     * @return game data as an ArrayList<String>.
     */
    public String[] loadGame(int id)
    {
        return this.saveData.get(id);
    }


    /**
     * Gets the save data from the save file and stores each row
     * in an ArrayList.
     */
    public void getSaveData() throws IOException
    {
        // Initialise save list and open the file.
        saveData = new ArrayList<>();
        File file = new File(this.filepath);


        // Listen to the file using Scanner.
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");


        // Get each row and add it to the ArrayList.
        while(scanner.hasNext())
        {
            String[] row = scanner.nextLine().split(","); // Split each line into columns
            this.saveData.add(row);
        }

        // Finished.
        scanner.close();
    }


    /**
     * Displays the save data in the console for the user to read.
     * @throws IOException An issue reading the file.
     */
    public void displaySaveData() throws IOException
    {
        // Get latest save data.
        getSaveData();

        // Display save data in the console.
        System.out.println("Saved Game Data:");

        for (String[] row : saveData)
        {
            // Format each cell
            for (String cell : row) System.out.printf("%-25s", cell);

            // Start new row.
            System.out.println();
        }

        System.out.print("\nChoose an option: ");
        System.out.print("\n[E] Exit ");
        System.out.print("\n[ID] Choose a game ID ");
    }


    /**
     * Counts the number of lines/saves in the save file.
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
     * @throws IOException If there is an issue appending to the file.
     */
    public void append(String[] saveData) throws IOException
    {
        // Load the save file.
        File file = new File(this.filepath);

        // Get the date.
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileDate = formatter.format(date);

        // Format the save data.
        String[] data = new String[5];

        data[0] = countLines(file).toString(); // Id.
        data[1] = fileDate; // Set a date.
        data[2] = saveData[0]; // Marble size.
        data[3] = saveData[1]; // Human turn.
        data[4] = saveData[2]; // Moves.


        // Write the data, split with delimiter.
        try (FileWriter writer = new FileWriter(file, true))
        {
            writer.write(String.join(",", data) + "\n");
        }
    }
}
