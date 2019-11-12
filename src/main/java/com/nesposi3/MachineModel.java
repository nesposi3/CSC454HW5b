package com.nesposi3;


public class MachineModel extends Model<Pair<Integer,Double>,Integer> {
    private double timeLeft;
    private int numParts;
    private int t;
    @Override
    public Integer lambda() {
        return 1;
    }

    public MachineModel(int timeTocomplete){
        this.t = timeTocomplete;
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
}
