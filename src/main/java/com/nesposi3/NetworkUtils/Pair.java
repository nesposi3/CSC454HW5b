package com.nesposi3.NetworkUtils;

import java.util.Objects;

public class Pair<A,B> {
    private A a;
    private B b;

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }
    public Pair(A a, B b){
        this.a =a;
        this.b = b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a,b);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Pair)){
            return false;
        }
        Pair other = (Pair) obj;
        return (other.a.equals(this.a)) && (other.b.equals(this.b));
    }

    @Override
    public String toString() {
        return "{"+a.toString()+" " + b.toString()+"}";
    }
}
