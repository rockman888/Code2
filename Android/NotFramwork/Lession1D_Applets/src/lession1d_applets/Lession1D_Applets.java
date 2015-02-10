/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lession1d_applets;


import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Event;

/**
 *
 * @author Administrator
 */
public class Lession1D_Applets extends Applet 
{
    final int MAXSPOTS = 10;
    int xspots[] = new int[MAXSPOTS];
    int yspots[] = new int[MAXSPOTS];
    
    int curspots = 0;
    
    public void init()
    {
        setBackground(Color.gray);
    }
    
    public void AddSpots(int x, int y)
    {
        xspots[curspots] = x;
        yspots[curspots] = y;
        
        curspots ++;
        
        // vẽ lại màn hình
        repaint();
    }
    
    public boolean mouseDown(Event evt, int x, int y)
    {
        if (curspots < MAXSPOTS)
            AddSpots(x, y);
        else
            System.out.println("Too many spots! :D");
        
        return true;
                    
    }
    
    

    public void paint (Graphics g)
    {
        g.setColor(Color.blue);
        for (int i=0; i<curspots; i++)
            g.fillOval(xspots[i] - 10, yspots[i] - 10, 20, 20);
    }
    
}
