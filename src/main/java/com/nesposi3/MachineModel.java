package com.nesposi3;


import java.util.ArrayList;
import java.util.Objects;

public class MachineModel extends Model<Pair<Integer,Double>,Pair<Integer,Double>> {
    private double timeLeft;
    private int numParts;
    private int t;
    private Pair<Integer,Double> defaultPortVal;
    @Override
    public Pair<Integer,Double> lambda() {
        //TODO Get the correct time elapsed for output
        return new Pair<>(1,0.0);
    }

    public MachineModel(int timeTocomplete, String name,Pair<Integer,Double> defaultPortVal){
        this.t = timeTocomplete;
        this.name = name;
        this.defaultPortVal = defaultPortVal;
        this.inputPorts = new ArrayList<>();
        this.pipeList = new ArrayList<>();
    }
    @Override
    public void deltaExt(Pair<Integer,Double> pair) {
        int q = pair.getA();
        double e = pair.getB();
        if(numParts>0){
            this.numParts += q;
            this.timeLeft -= e;
        }else{
            this.numParts += q;
            this.timeLeft = t;
        }
        // Clear ports for next external
        for (Port<Pair<Integer,Double>> p: inputPorts) {
            p.setVal(defaultPortVal);
        }
    }

    @Override
    public void deltaInt() {
        this.numParts--;
    }

    @Override
    public void deltaConf(Pair<Integer,Double> pair) {
        this.numParts += (pair.getA()-1);
    }

    @Override
    public double timeAdvance() {
        if(numParts>0) return timeLeft;
        return Network.INFINITY;
    }

    @Override
    public boolean recievedAllInput() {
        boolean ready = true;
        for (Port<Pair<Integer,Double>> p: inputPorts) {
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
