package com.nesposi3;


import java.util.Objects;

public class MachineModel extends Model<Pair<Integer,Double>,Integer> {
    private double timeLeft;
    private int numParts;
    private int t;
    private Pair<Integer,Double> defaultPortVal;
    @Override
    public Integer lambda() {
        return 1;
    }

    public MachineModel(int timeTocomplete, String name,Pair<Integer,Double> defaultPortVal){
        this.t = timeTocomplete;
        this.name = name;
        this.defaultPortVal = defaultPortVal;
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
        return Double.MAX_VALUE;
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
