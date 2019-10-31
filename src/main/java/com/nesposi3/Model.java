package com.nesposi3;

import java.util.List;

public abstract class Model<Input, Output> {
    public abstract Output lambda();
    public abstract void delta(Input input);
    public Port<Output> getOutputPort(){
        return this.outputPort;
    }
    public List<Port<Input>> getInputPorts(){
        return this.inputPorts;
    }
    public List<Pipe<Output>> getPipes(){
        return this.pipeList;
    }
    public void addPort(Port<Input> p ){
        this.inputPorts.add(p);
    }
    public void addPipe(Port<Output> nextIn ){
        Pipe<Output> p = new Pipe<>(this.outputPort,nextIn);
        this.pipeList.add(p);
    }
    protected boolean debug;
    protected List<Port<Input>> inputPorts;
    protected Port<Output> outputPort;
    protected List<Pipe<Output>> pipeList;
    public String name;
}
