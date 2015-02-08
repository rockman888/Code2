/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lession1b_applets;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Font;
import java.util.Date;
       

/**
 *
 * @author Administrator
 */
public class Lession1B_Applets extends Applet
{
     java.awt.Font f = new Font("TimesRoman", Font.BOLD, 36);
     String name;
     Date myClock;
    
    // the method that will be automatically called when the applet is start
    public void init()
    {
        // it is required but does not need anything
        
        this.name = getParameter("name");   // name (getParameter) nay nam trong file HTML
        if (this.name == null)
            this.name = "Laura";
        
        this.name = "Hello: " + this.name + "!";
    }
    
    // This method gets called when the applets is terminated
    // that's when the user goes to another page or exits the browser
    public void stop()
    {
        
    }
    
    public void PaintLampPlatform(Graphics g)
    {
        // the lamp platform
        g.fillRect(0,250,290,290);
        // the base of the lamp
        g.drawLine(125,250,125,160);
        g.drawLine(175,250,175,160);
        
        // the lamp shade, top and bottom edges
        g.drawArc(85,157,130,50,-65,312);
        g.drawArc(85,87,130,50,62,58);
        
        // lamp shade, sides
        g.drawLine(85,177,119,89);
        g.drawLine(215,177,181,89);
        
        // dots on the shade
        g.fillArc(78,120,40,40,63,-174);
        
        g.fillOval(120,96,40,40);
        g.fillArc(173,100,40,40,110,180);
    }

    
    public void DigitalClock()
    {
        
        while (true)
        {
            myClock = new Date();
            
            repaint();
            
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {                
                    
            }
        }
                
    }
    
    
    public void start()
    {
        DigitalClock();
    }
    
    // the standard method that you have to use to paint things on screen 
    // this overrides the empty Applet method, you can't called it "display" for example
    public void paint(Graphics g)
    {
        
        // cách 1: dùng int exes[]
        //int exes[] = { 39,94,97,142,53,58,26 };
        //int whys[] = { 33,74,36,70,108,80,106 };
        
        // cách 2: dùng int[] arr
        //int[] arr = {1,2,3};
        
       g.setFont(f);
       g.drawString(myClock.toString(), 10, 100);
    }

    private void WriteString(Graphics g)
    {
        // PaintLampPlatform
        
        g.setFont(f);
        g.setColor(Color.GREEN);
        // method to draw text on screen
        // String first, then x, y coordinate        
        g.drawString(this.name, 5, 50);
        g.drawLine(25, 25, 75, 75);
    }
}
