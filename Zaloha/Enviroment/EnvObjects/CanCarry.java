/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment.EnvObjects;

/**
 *
 * @author lennylinux
 */
public interface CanCarry {
    public boolean setCarrying(Carryable item);
    public boolean tryCarry(Carryable item);
    public boolean dropItem();
    public Carryable getItem();
}
