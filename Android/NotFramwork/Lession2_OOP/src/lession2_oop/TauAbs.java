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
public abstract class TauAbs
{
    private String name;
    
    public TauAbs(String n)
    {
        name = n;
    }
    
    public abstract void Function();

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
