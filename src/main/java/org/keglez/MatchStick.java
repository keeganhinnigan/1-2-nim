package org.keglez;

public class MatchStick
{
    private int xPosition;
    private int yPosition;

    public MatchStick(int xPosition, int yPosition)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public int getXPosition()
    {
        return xPosition;
    }
    public void setXPosition(int xPosition)
    {
        this.xPosition = xPosition;
    }

    public int getYPosition()
    {
        return yPosition;
    }
    public void setYPosition(int yPosition)
    {
        this.yPosition = yPosition;
    }
}
