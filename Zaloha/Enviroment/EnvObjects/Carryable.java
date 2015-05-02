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
public interface Carryable {
    public boolean getIsCarryable();
    public boolean getIsCarried();
    public CanCarry getIsCarriedBy();
    public boolean setCarrier(CanCarry carrier);
    public boolean removeCarrier(CanCarry carrier);
}
