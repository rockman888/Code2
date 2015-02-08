/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lession2_oop_strategy;

import lession2_oop_strategy.Interface.IGoAlgorithm;

/**
 *
 * @author Administrator
 */
public abstract class Vehicle
{
    private IGoAlgorithm goAlgorithm;
    
    public Vehicle()
    {
        
    }
    
    public void SetAlgorithm(IGoAlgorithm algo)
    {
        goAlgorithm = algo;
    }
    
    public void Go()
    {
        goAlgorithm.Go();
    }
    
}
