package org.keglez;

import javax.swing.*;
import java.awt.*;

public class GameGraphics extends Canvas
{
    public void paint(Graphics x) {
        //adding the string to graphics
        x.drawString("Hello", 40, 40);
        //setting background color as white
        setBackground(Color.WHITE);
        //Drawing rectangle shape
        x.fillRect(130, 30, 100, 80);
        x.drawOval(30, 130, 50, 60);
        setForeground(Color.RED);
        x.fillOval(130, 130, 50, 60);
        x.drawArc(30, 200, 40, 50, 90, 60);
        x.fillArc(30, 130, 40, 50, 180, 40);
    }
}
