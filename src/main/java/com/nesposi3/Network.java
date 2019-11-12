package com.nesposi3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Network<Input,Output>{
    public TimePair globalTime;
    public EventPriorityQueue eventPriorityQueue;
    private Model<Input,?> firstChild;
    private Model<?, Output> finalChild;
    private List<Model<?,?>> childList;
    private boolean debug;
    public Network(boolean debugFlag){
        this.childList = new ArrayList<>();
        this.debug = debugFlag;
        this.globalTime = new TimePair(0.0);
        this.eventPriorityQueue = new EventPriorityQueue();
    }

    public void addModel(Model<?,?> m){
        this.childList.add(m);
    }
    public void initializeQueue(Map<String,Input> inputs){
        for(String s:inputs.keySet()){
            double time = Double.parseDouble(s);
            Input num = inputs.get(s);
            Event<Input> e = new Event<>(this.firstChild,new TimePair(time),EventType.DELTAEXT,num);
            eventPriorityQueue.add(e);
        }
        System.out.println(eventPriorityQueue.toString());
    }
    public void simulate(){

    }
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

    public void deltaExt(List<Input> input) {
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

    /**
     * Tell the network which child to give its input to
     * @param m
     */
    public void setFirstChild(Model<Input,?> m){
        this.firstChild = m;
    }

    /**
     * Tell the network which child o get its output from
     * @param m
     */
    public void setFinalChild(Model<?,Output> m){
        this.finalChild = m;
    }

}
