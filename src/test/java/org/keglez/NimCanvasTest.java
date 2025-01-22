package org.keglez;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NimCanvasTest
{
    /**
     * Ensure that the canvas width can be retrieved when required.
     */
    @Test
    void testGettingCanvasWidth()
    {
        // Setup canvas environment.
        NimCanvas canvas = new NimCanvas(500, 500);

        // Testing to see if the width changed.
        assertEquals(500, canvas.getWidth());
    }


    /**
     * Ensure that the canvas height can be retrieved when required.
     */
    @Test
    void testGettingCanvasHeight()
    {
        // Setup canvas environment.
        NimCanvas canvas = new NimCanvas(500, 500);

        // Testing to see if the width changed.
        assertEquals(500, canvas.getHeight());
    }


    /**
     * Ensure that the canvas width can be changed when required.
     */
    @Test
    void testSettingCanvasWidth()
    {
        // Setup canvas environment.
        NimCanvas canvas = new NimCanvas(500, 500);
        canvas.setWidth(700);

        // Testing to see if the width changed.
        assertEquals(700, canvas.getWidth());
    }

    /**
     * Ensure that the canvas height can be changed when required.
     */
    @Test
    void testSettingCanvasHeight()
    {
        // Setup canvas environment.
        NimCanvas canvas = new NimCanvas(500, 500);
        canvas.setHeight(700);

        // Testing to see if the width changed.
        assertEquals(700, canvas.getHeight());
    }

    /**
     *  Ensure that the correct number of match sticks are generated
     *  on the GUI.
     */
    @Test
    void testGeneratingMatchSticks()
    {
        // Setup canvas environment.
        NimCanvas canvas = new NimCanvas(500, 500);
        canvas.generateMatchSticks();

        // Check that 10 match sticks were generated.
        assertEquals(10, canvas.getMatchSticks().size());
    }


    /**
     *  Ensure that the correct number of matchsticks are removed
     *  from the GUI.
     */
    @Test
    void testRemovingMatchSticks()
    {
        // Setup canvas environment.
        NimCanvas canvas = new NimCanvas(500, 500);
        canvas.removeMatchStick(2);

        // Check that 8 match sticks were generated.
        assertEquals(8, canvas.getMatchSticks().size());
    }


    /**
     *  Ensure that the game is correctly loaded.
     */
    @Test
    void testLoadingGame()
    {
        // Setup canvas environment.
        NimCanvas canvas = new NimCanvas(500, 500);
        canvas.load(5);

        // Check that 5 match sticks were generated.
        assertEquals(5, canvas.getMatchSticks().size());
    }


    /**
     *  Ensure that the winner can be set in the GUI.
     */
    @Test
    void testSettingWinner()
    {
        // Setup canvas environment.
        NimCanvas canvas = new NimCanvas(500, 500);
        canvas.setWinner("Human");

        // Check that 5 match sticks were generated.
        assertEquals("Human", canvas.getWinner());
    }
}