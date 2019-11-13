package com.nesposi3;

import java.util.List;

public abstract class Model<Input, Output> {
    public abstract Output lambda();
    public abstract void deltaExt(Input input);
    public abstract void deltaInt();
    public abstract void deltaConf(Input input);
    public abstract double timeAdvance();
    public abstract boolean recievedAllInput();
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
    public void addPipe(Port<Output> nextIn,Output defVal ){
        this.outputPort = new Port<>(defVal,nextIn.getConnectedTo());
        Pipe<Output> p = new Pipe<>(this.outputPort,nextIn);
        this.pipeList.add(p);
    }
    protected boolean debug;
    protected List<Port<Input>> inputPorts;
    protected Port<Output> outputPort;
    protected List<Pipe<Output>> pipeList;
    protected TimePair timeOfLastDeltaExt;

    public void setTimeOfLastDeltaExt(TimePair timeOfLastDeltaExt) {
        this.timeOfLastDeltaExt = timeOfLastDeltaExt;
    }

    public String name;
}
