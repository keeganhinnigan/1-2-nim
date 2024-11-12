package org.keglez;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class provides all the graphics for the 1-2 nim game.
 *
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


        // Draw winner text.
        if(this.matchSticks.isEmpty())
        {
            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font("Arial", Font.BOLD, 30));
            graphics.drawString("Human is the winner!", (width/4)-30, height/2);
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
}
