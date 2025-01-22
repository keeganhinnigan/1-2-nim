package org.keglez;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *  This class provides all the game graphics for 1-2 nim
 *  canvas that can be initialized anywhere.
 *
 *  @author Keegan Hinnigan
 *  @since 23/01/2025
 *  @version 1.0
 */
public class NimCanvas extends JComponent
{
    // Class attributes.
    private int width, height;
    private String winner = "";
    private Graphics2D graphics;
    private ArrayList<MatchStick> matchSticks;


    /**
     * Constructor method for NimCanvas.
     *
     * @param width The preferred width of the canvas.
     * @param height The preferred height of the canvas.
     */
    public NimCanvas(int width, int height)
    {
        try
        {
            // Set class attributes.
            this.width = width;
            this.height = height;

            // Generate match sticks.
            generateMatchSticks();

            // Set the size of the nim canvas.
            setPreferredSize(new Dimension(width, height));
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while constructing the nim canvas:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This will generate match sticks.
     */
    public void generateMatchSticks()
    {
        try
        {
            // Create a new ArrayList of match sticks.
            this.matchSticks = new ArrayList<>();

            // Number of rows and spacing settings for the pyramid
            // For 10 match sticks use 4 rows.
            // For 15 match sticks 5 rows.
            int totalRows = 4;
            int matchstickWidth = 6;
            int matchstickHeight = 60;

            // Spacing between the match sticks.
            int verticalSpacing = 10;
            int horizontalSpacing = 20;

            // Loop to create each row of matchsticks.
            for (int row = 0; row < totalRows; row++) {

                int sticksInRow = totalRows - row;  // Decrease the number of matchsticks in each row
                int rowY = (row * (-matchstickHeight + -verticalSpacing)) + (totalRows * matchstickHeight);  // Calculate y position for the row

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
        catch (Exception error)
        {
            System.out.println("An error occurred while generating the match sticks:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This method will render the nim game in its current state.
     *
     *  @param g The graphics object to draw.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        try
        {
            // Cast swing graphics to graphics 2D.
            super.paintComponent(g);
            this.graphics = (Graphics2D) g;

            // Draw nim game background.
            Rectangle bounds = new Rectangle(0, 0, getWidth(), getHeight());
            graphics.setColor(new Color(0, 150, 0));
            graphics.fill(bounds);

            // Draw game text.
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.BOLD, 20));
            graphics.drawString("Matchsticks: " + this.matchSticks.size(), 10, 30);

            // Check if a winner has been found.
            if (!this.winner.isEmpty()) {
                this.graphics.setColor(Color.YELLOW);
                this.graphics.setFont(new Font("Arial", Font.BOLD, 30));
                this.graphics.drawString(this.winner + " is the winner!", (width / 4) - 30, height / 2);
            }

            // Draw all match sticks in the match stick list.
            for (MatchStick stick : this.matchSticks) {
                drawMatchStick(stick.getXPosition(), stick.getYPosition());
            }
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while painting the canvas:");
            throw new RuntimeException(error);
        }
    }


    /**
     * This method removes a specified quantity of match sticks
     * from the pile.
     *
     * @param quantity The number of match sticks to remove.
     */
    public void removeMatchStick(int quantity)
    {
        try
        {
            // Remove the specified quantity of match sticks.
            for (int loop = 0; loop < quantity; loop++)
            {
                this.matchSticks.removeFirst();
            }

            // Update the canvas.
            repaint();
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while removing a match stick:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This method draws a match stick on the nim game canvas to a
     *  specified position.
     *
     *  @param xPosition The X position of the match on the screen.
     *  @param yPosition The Y position of the match on the screen.
     */
    public void drawMatchStick(int xPosition, int yPosition)
    {
        try
        {
            // Top of the match.
            this.graphics.setColor(new Color(150, 0, 0)); // Green
            this.graphics.fillOval(xPosition - 2, yPosition, 10, 10);

            // Bottom of the match.
            this.graphics.setColor(new Color(255, 255, 185)); // Beige
            this.graphics.fillRect(xPosition, yPosition + 10, 6, 50);
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while drawing a match stick:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  Return the graphics match sticks ArrayList.
     *
     * @return Match sticks ArrayList.
     */
    public ArrayList<MatchStick> getMatchSticks()
    {
        return this.matchSticks;
    }


    /**
     *  This method sets up the canvas after a saved came has been
     *  chosen to be loaded. It will not change the difficulty
     *  of the game, as this is a user preference and may be changed
     *  manually.
     *
     *  @param matchsticks The total match sticks left in the game
     */
    public void load(int matchsticks)
    {
        try
        {
            // Generate new set of matchsticks.
            generateMatchSticks();

            // Remove matchsticks.
            removeMatchStick(10 - matchsticks);

            // Repaint the canvas.
            repaint();
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while setting up the loaded game:");
            throw new RuntimeException(error);
        }
    }


    /**
     * This method gets the current width of the nim canvas.
     *
     * @return The current width of the canvas.
     */
    public int getWidth()
    {
        try
        {
            return width;
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while getting the canvas width:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This method set the width of the canvas.
     *
     *  @param width The new width of the canvas.
     */
    public void setWidth(int width)
    {
        try
        {
            this.width = width;
        }
        catch (Exception e)
        {
            System.out.println("An error occurred while setting the canvas width:");
            throw new RuntimeException(e);
        }
    }


    /**
     *  This method gets the current height of the canvas.
     *
     *  @return Current height of the canvas.
     */
    public int getHeight()
    {
        try
        {
            return height;
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while getting the canvas height:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This method sets the height of the nim canvas.
     *
     *  @param height The new height of the canvas.
     */
    public void setHeight(int height)
    {
        try
        {
            this.height = height;
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while setting the canvas height:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  When a winner has been chosen in the nim game, this method can be
     *  called to display their name on the screen.
     *
     *  @param name The name of the winner.
     */
    public void setWinner(String name)
    {
        try
        {
            this.winner = name;
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while setting the winner:");
            throw new RuntimeException(error);
        }
    }


    /**
     *  This method will return the winner's name.
     *
     *  @return Winners name.
     */
    public String getWinner()
    {
        try
        {
            return this.winner;
        }
        catch (Exception error)
        {
            System.out.println("An error occurred while getting the winner:");
            throw new RuntimeException(error);
        }
    }


    /**
     * This inner class represents a graphical match stick for the 1-2 nim game. It
     * holds the coordinate information of the match stick so it can be
     * stored in a list and rendered where required.
     *
     * @author Keegan Hinnigan
     * @since 23/01/2025
     * @version 1.0
     */
    private static class MatchStick
    {
        // Class attributes.
        private int xPosition;
        private int yPosition;


        /**
         * Constructor for a match stick in the 1-2 nim game.
         *
         * @param xPosition x position on the screen.
         * @param yPosition y position on the screen.
         */
        public MatchStick(int xPosition, int yPosition)
        {
            try
            {
                this.xPosition = xPosition;
                this.yPosition = yPosition;
            }
            catch (Exception error)
            {
                System.out.println("An error occurred while trying to construct a match stick:");
                throw new RuntimeException(error);
            }
        }


        /**
         * This method gets the x position of the match stick.
         *
         * @return X position of the match sticks.
         */
        public int getXPosition()
        {
            try
            {
                return xPosition;
            }
            catch (Exception e)
            {
                System.out.println("An error occurred while trying to get the X position of a match stick:");
                throw new RuntimeException(e);
            }
        }


        /**
         * This method sets the x position of the match stick.
         *
         * @param xPosition Sets new X position.
         */
        public void setXPosition(int xPosition)
        {
            try
            {
                this.xPosition = xPosition;
            }
            catch (Exception error)
            {
                System.out.println("An error occurred while trying to set the X position of a match stick:");
                throw new RuntimeException(error);
            }
        }


        /**
         * This method gets the y position of the match stick.
         *
         * @return Y position of the match sticks.
         */
        public int getYPosition()
        {
            try
            {
                return yPosition;
            }
            catch (Exception error)
            {
                System.out.println("An error occurred while trying to get the Y position of a match stick:");
                throw new RuntimeException(error);
            }
        }


        /**
         * This method sets the y position of the match stick.
         *
         * @param yPosition Sets new Y position.
         */
        public void setYPosition(int yPosition)
        {
            try
            {
                this.yPosition = yPosition;
            }
            catch (Exception error)
            {
                System.out.println("An error occurred while trying to set the Y position of a match stick:");
                throw new RuntimeException(error);
            }
        }
    }
}