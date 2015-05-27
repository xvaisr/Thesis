/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */

package RTreeAlgorithm;

import java.util.Objects;

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
