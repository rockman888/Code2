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
public class Tau
{
    private String name;
    
    public Tau(String n)
    {
        name = n;
    }
    
    public void Function()
    {
        
        System.out.println("Chức năng của tàu ... ");
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    
}
