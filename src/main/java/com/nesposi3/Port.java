package com.nesposi3;

public class Port<T> {
    private T t;
    public void setVal(T t){
        this.t =t;
    }
    public T getVal(){
        return this.t;
    }
    public Port(T t){
        this.t = t;
    }
}
