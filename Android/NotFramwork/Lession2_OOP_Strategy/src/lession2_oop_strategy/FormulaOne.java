/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lession2_oop_strategy;

import lession2_oop_strategy.Interface.GoByDrving;

/**
 *
 * @author Administrator
 */
public class FormulaOne extends Vehicle
{
    public FormulaOne()
    {
        SetAlgorithm(new GoByDrving());
    }  
    
}
