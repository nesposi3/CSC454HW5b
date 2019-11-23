package com.nesposi3;

public class NetworkInputPipe <T> {
    private Port<T> input;
    public NetworkInputPipe(Port<T> port){
        input = port;
    }
    public void setInput(T t){
        input.setVal(t);
    }
    public T getVal(){
        return input.getVal();
    }
    public Model<T,?> getModel(){
        return input.getConnectedTo();
    }
}
