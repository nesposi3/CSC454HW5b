package com.nesposi3.CPUModel.atomicModels;

import com.nesposi3.CPUModel.Utils;
import com.nesposi3.NetworkUtils.Model;
import com.nesposi3.NetworkUtils.Network;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Multiply extends Model<String,String> {
    private ArrayDeque<String> output;
    public Multiply(){
        output = new ArrayDeque<>();
        this.pipeList = new ArrayList<>();
        this.inputPorts = new ArrayList<>();
        this.name = "Multiply";
    }
    @Override
    public String lambda() {
        return output.peek();
    }

    @Override
    public void deltaExt(String s, double elapsed) {
        if(Utils.correctModel(Utils.BitTypes.MUL,s)){
            int[] ints = Utils.intsFromBits(s);
            int c = ints[0] * ints[1];
            String out = Utils.integerToBinary(c);
            output.add(out);
        }
    }

    @Override
    public void deltaInt() {
        output.poll();
    }

    @Override
    public void deltaConf(String s, double elapsed) {
        output.poll();
        if(Utils.correctModel(Utils.BitTypes.MUL,s)){
            int[] ints = Utils.intsFromBits(s);
            int c = ints[0] * ints[1];
            String out = Utils.integerToBinary(c);
            output.add(out);
        }
    }

    @Override
    public double timeAdvance() {
        if(output.size()>0){
            return 8.0;
        }else{
            return Network.INFINITY;
        }
    }

    @Override
    public boolean recievedAllInput() {
        return true;
    }
}
