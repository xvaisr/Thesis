/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RTreeAlgorithm;

import java.util.Objects;

/**
 *
 * @author lennylinux
 * @param <A>
 * @param <B>
 */
public final class Pair <A, B> {
    private final A a;
    private final B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }
    
    public A getA() {
        return this.a;
    }
    
    public B getB() {
        return this.b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }        
        Pair<A, B> pair = this.getClass().cast(obj);        
        return this.a.equals(pair.getA()) && this.b.equals(pair.getB());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.a) + Objects.hashCode(this.b);
        return hash;
    }
}
