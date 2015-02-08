/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lession1c_applets;

import java.applet.Applet;
import java.util.Date;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Administrator
 */
public class Lession1C_applets extends Applet implements Runnable 
{
    Font font = new Font("Verdena", Font.BOLD, 28);
    Date date;
    Thread runner;
    
    // do implements Runable thì phải có method run
    public void run()
    {
        while (true)
        {
            date = new Date();
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
        if (runner == null)
        {
            runner = new Thread(this);
            runner.start();
        }
    }
    
    public void stop()
    {
        if (runner != null)
        {
            runner.stop();
            runner = null;
        }
    }
    
    public void init()
    {
        
    }
  
    public void paint(Graphics g)
    {
        g.setFont(font);
        g.drawString(date.toString(), 10, 100);
    }
}
