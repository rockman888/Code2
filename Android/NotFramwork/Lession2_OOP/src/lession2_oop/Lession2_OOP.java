/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lession2_oop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
       

/**
 *
 * @author Administrator
 */
public class Lession2_OOP
{

    public static void Demo_Dont_Use_Abstract_Class()
    {
        Tau[] t = new Tau[3];
        t[0] = new TauChien("Tàu Chiến số 1");
        t[1] = new TauChien("Tàu Chiến số 2");
        t[2] = new TauChoHang("Tàu Chở hàng số 1");
        
        for (int i=0; i<t.length; i++)
        {
            System.err.println(t[i].getName());
            t[i].Function();
        }
        
        System.out.println(" ******************************* ");
        
        List<Tau> list = new ArrayList<Tau>();
        list.add(new TauChien("aaa"));
        list.add(new TauChien("bbb"));
        list.add(new TauChoHang("ccc"));
        
        for (int i=0; i<list.size(); i++)
            list.get(i).Function();
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Demo_Use_Abstract_Interface_Class_1();
    }

    /**
     * Cách 1:
     * phương pháp in và xuất trong abstract, interface class
     * Dùng list.get(i)
     * Chú ý dùng instanceof
     */
    private static void Demo_Use_Abstract_Interface_Class_1()
    {
        // TODO code application logic here
                
        
        TauAbs[] t = new TauChienAbs[2];
        t[0] = new TauChienAbs("A");
        t[1] = new TauChienAbs("B");
        for (int i=0; i<t.length; i++)
        {
            if (t == null)
                continue;

            t[i].Function();
        }
        
        System.out.println(" -------------------------------- ");
        List<TauAbs> list = new ArrayList<TauAbs>();
        list.add(new TauChienAbs("X"));                        
        list.add(new TauChoHangAbs("Y"));
        list.add(new TauChienAbs("Z"));
       
        for (int i=0; i<list.size(); i++)
        {
            list.get(i).Function();                  
            
            if (list.get(i) instanceof IChucNang)
                ((IChucNang)list.get(i)).MachineGun();
        }
    }
    
     /**
     * Cách 2:
     * phương pháp in và xuất trong abstract, interface class
     * Dùng ListInterator
     */
    private static void Demo_Use_Abstract_Interface_Class_2()
    {
        System.out.println(" -------------------------------- ");
        List<TauAbs> list = new ArrayList<TauAbs>();
        list.add(new TauChienAbs("X"));                        
        list.add(new TauChoHangAbs("Y"));
        list.add(new TauChienAbs("Z"));
       
       for (ListIterator iter = list.listIterator(); iter.hasNext();)
        {
            TauAbs t = (TauAbs) iter.next();
            t.Function();
            
            if (t instanceof IChucNang)
            {
                ((IChucNang)t).MachineGun();
            }
            
        }    
    }
    
    private static void Demo_Use_Abstract_Interface_Class()
    {
        System.out.println(" -------------------------------- ");
        List<TauAbs> list = new ArrayList<TauAbs>();
        list.add(new TauChienAbs("X"));                        
        list.add(new TauChoHangAbs("Y"));
        list.add(new TauChienAbs("Z"));
       
        for (int i=0; i<list.size(); i++)
        {
            list.get(i).Function();                  
            
            if (list.get(i) instanceof IChucNang)
                ((IChucNang)list.get(i)).MachineGun();
        }
    }
    
}
