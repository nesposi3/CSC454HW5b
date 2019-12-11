package com.nesposi3.NetworkUtils;

public class Port<T> {
    private T t;
    private Model<T,?> connectedTo;
    public void setVal(T t){
        this.t =t;
    }
    public T getVal(){
        return this.t;
    }
    public Port(T t,Model<T,?> m){
        this.t = t;
        this.connectedTo = m;
    }
    public Model<T,?> getConnectedTo(){
        return connectedTo;
    }
}
