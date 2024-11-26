package org.keglez.nimgui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This inner class provides all the game graphics for 1-2 nim as a canvas.
 * @author Keegan
 * @version 11/11/2024
 */
public class NimCanvas extends JComponent
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
        try
        {
            for (int loop = 0; loop < quantity; loop++)
            {
                this.matchSticks.removeFirst();
            }

            repaint();
        }
        catch (Exception error)
        {
            System.out.println("Error removing match stick:");
            System.out.println(error.getMessage());
        }
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