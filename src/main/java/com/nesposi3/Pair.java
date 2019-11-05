package com.nesposi3;

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

}
