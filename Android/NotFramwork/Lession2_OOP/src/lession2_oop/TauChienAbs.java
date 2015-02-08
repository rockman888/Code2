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
public class TauChienAbs extends TauAbs implements IChucNang
{
    public TauChienAbs(String n)
    {
        super(n);
    }
    
    public void Function()
    {                
        System.out.println("Tàu: " + getName() + " Dùng để chiến đấu!");
    }
    
    public void MachineGun()
    {
        System.out.println("Use for kill enemy, protect our land!");
    }
}
