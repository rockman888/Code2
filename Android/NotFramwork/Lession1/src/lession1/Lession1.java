/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lession1;

import java.util.Date;



/**
 *
 * @author Administrator
 */
public class Lession1 {
    
    
    public static void ArrayTest()
    {
        String[] arr = new String[10];
        arr[0] = "eggplant";
        arr[1] = "egg";
        arr[2] = "ky";
        arr[3] = "Lien minh huyen thoia";
        
        for (int i =0; i<arr.length; i++)
            System.out.println(arr[i] + " ");
    }
    
    public static void TimeOutput()
    {
        Date d1,d2,d3;
        d1 = new Date();
        System.out.println("Date 1: " + d1);
        
        d2 = new Date(2013, 12, 31);
        System.out.println("Date 2: " + d2);
        
        d3 = new Date("December 12 2013 4:21 PM");
        System.out.println("Date 3: " + d3);
     }
    
    public static int compare(int bEnginee)
    {
        if (bEnginee == 1)
            return 1;
        
        else if (bEnginee == 2)
            return 2;
        
        return 3;
                    
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hello ViLH\n");
        System.out.println("Hello VNG!");
        
        
        System.out.println(" ------------------------ ");
        int b = 3;
        int a = 8;
        a = a >> 1;
                
        System.err.println("a = " + a);
        System.out.println("b = " + b);
        if (compare(b) == 1)
            System.out.println("true");
        else
            System.out.println("false");
        
        
        System.out.println(" ------------------------ ");
        TimeOutput();
        
        System.out.println(" ------------------------ ");
        ArrayTest();        
        
        System.out.println(" ------------------------ ");
    }    
}
