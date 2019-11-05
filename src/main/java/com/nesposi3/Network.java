package com.nesposi3;

import java.util.ArrayList;
import java.util.List;

public class Network<Input,Output> extends Model<Input,Output> {
    public TimePair globalTime;
    public EventPriorityQueue eventPriorityQueue;
    private Model<Input,Output> firstChild;
    private Model<Input, Output> finalChild;
    private List<Model<Input,Output>> childList;
    public Network(boolean debugFlag){
        this.childList = new ArrayList<>();
        this.debug = debugFlag;
        this.globalTime = new TimePair(0.0);
        this.eventPriorityQueue = new EventPriorityQueue();
    }

    public void addModel(Model<Input,Output> m){
        this.childList.add(m);
    }
    public void simulate(){

    }
    @Override
    public Output lambda() {
        Output firstOutput = this.firstChild.lambda();
        this.firstChild.getOutputPort().setVal(firstOutput);
        for (Pipe<Output> p :this.firstChild.getPipes()
             ) {
            p.shiftVal(firstOutput);
        }


        for (Model<Input,Output> m: this.childList
             ) {
            Output output = m.lambda();
            m.getOutputPort().setVal(output);
            for(Pipe<Output> p: m.getPipes()){
                p.shiftVal(output);
            }
        }
        Output finalOutput = this.finalChild.lambda();
        for (Pipe<Output> p :this.finalChild.getPipes()) {
            p.shiftVal(finalOutput);

        }
        return finalOutput;
    }

    @Override
    public void deltaExt(Input input) {
        this.firstChild.deltaExt(input);
        for (Model<Input,Output> m:this.childList) {
            List<Port<Input>> inputPorts = m.getInputPorts();
            ArrayList<Input> deltaInputs = new ArrayList<>();
            for (Port<Input> p:inputPorts) {
                deltaInputs.add(p.getVal());
            }
            m.deltaExt(deltaInputs);
        }

        List<Port<Input>> inputPorts = finalChild.getInputPorts();
        ArrayList<Input> deltaInputs = new ArrayList<>();
        for (Port<Input> p:inputPorts) {
            deltaInputs.add(p.getVal());
        }
        finalChild.deltaExt(deltaInputs);

    }

    @Override
    public void deltaInt() {

    }

    @Override
    public void deltaConf(Input input) {

    }

    @Override
    public double timeAdvance() {
        return Integer.MAX_VALUE;
    }

    /**
     * Tell the network which child to give its input to
     * @param m
     */
    public void setFirstChild(Model<Input,Output> m){
        this.firstChild = m;
    }

    /**
     * Tell the network which child o get its output from
     * @param m
     */
    public void setFinalChild(Model<Input,Output> m){
        this.finalChild = m;
    }

}
