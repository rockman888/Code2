/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lession2_oop;

/**
 *
 * @author Administrator
 */
public class TauChoHangAbs extends TauAbs
{
    public TauChoHangAbs(String n)
    {
        super(n);
    }
    
    public void Function()
    {
        System.out.println("Tàu: " + getName() + " Dùng để chở hàng!");
    }    
}
