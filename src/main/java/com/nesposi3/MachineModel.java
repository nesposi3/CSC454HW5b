package com.nesposi3;


import java.util.ArrayList;
import java.util.Objects;

public class MachineModel extends Model<Integer,Integer> {
    private double timeLeft;
    private int numParts;
    private final int t;
    private Integer defaultPortVal;
    @Override
    public Integer lambda() {
        return 1;
    }

    public MachineModel(int timeTocomplete, String name,Integer defaultPortVal){
        this.t = timeTocomplete;
        this.name = name;
        this.defaultPortVal = defaultPortVal;
        this.inputPorts = new ArrayList<>();
        this.pipeList = new ArrayList<>();
    }
    @Override
    public void deltaExt(Integer q,double elapsed) {
        //System.out.println(e);
        //System.out.println(this.name + " " + timeElapsed);
        if(this.numParts>0){
            this.numParts += q;
            this.timeLeft -= elapsed;
        }else{
            this.numParts += q;
            this.timeLeft = t;
        }
        //System.out.println("Time left in machine " + timeLeft);
        // Clear ports for next external
        for (Port<Integer> p: inputPorts) {
            p.setVal(defaultPortVal);
        }
    }

    @Override
    public void deltaInt() {
        this.numParts--;
    }

    @Override
    public void deltaConf(Integer numParts,double elapsed) {
        this.numParts += (numParts-1);
    }

    @Override
    public double timeAdvance() {
        if(numParts>0) return timeLeft;
        return Network.INFINITY;
    }

    @Override
    public boolean recievedAllInput() {
        boolean ready = true;
        for (Port<Integer> p: inputPorts) {
            if(p.getVal().equals(defaultPortVal)){
                ready = false;
            }
        }
        return ready;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MachineModel)){
            return false;
        }else{
            MachineModel otherModel = (MachineModel) obj;
            return ((this.name==otherModel.name) && (this.t==otherModel.t));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name,this.t);
    }
}
