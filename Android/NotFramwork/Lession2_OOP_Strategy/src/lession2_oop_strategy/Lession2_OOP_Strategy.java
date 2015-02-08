/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lession2_oop_strategy;

import lession2_oop_strategy.Interface.GoByDrving;
import lession2_oop_strategy.Interface.GoByFlying;
import lession2_oop_strategy.Interface.GoByFlyingFast;

/**
 *
 * @author Administrator
 */
public class Lession2_OOP_Strategy
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        
        StreetRacer sr = new StreetRacer();
        FormulaOne fo = new FormulaOne();
        Jet j = new Jet();
        Helicopter h = new Helicopter();
        
        sr.Go();
        fo.Go();
        
        
        System.out.println(" -------------- ");
        j.SetAlgorithm(new GoByDrving());
        j.Go();
        
        j.SetAlgorithm(new GoByFlying());
        j.Go();
        
        j.SetAlgorithm(new GoByFlyingFast());
        j.Go();
        System.out.println(" -------------- ");
        h.Go();
        
    }
    
}
