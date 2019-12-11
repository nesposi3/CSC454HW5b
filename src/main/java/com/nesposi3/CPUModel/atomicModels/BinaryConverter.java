package com.nesposi3.CPUModel.atomicModels;

import com.nesposi3.CPUModel.Utils;
import com.nesposi3.NetworkUtils.Model;
import com.nesposi3.NetworkUtils.Network;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class BinaryConverter extends Model<String,String> {
    private ArrayDeque<String> output;
    private boolean toBinary;
    private final double t = 10.0;
    public BinaryConverter(boolean fromBinary){
        this.toBinary = fromBinary;
        output = new ArrayDeque<>();
        this.pipeList = new ArrayList<>();
        this.inputPorts = new ArrayList<>();
        this.name = (toBinary?"ToBinaryConverter":"FromBinaryConverter");
    }
    @Override
    public String lambda() {
        return output.peek();
    }

    @Override
    public void deltaExt(String s, double elapsed) {
        if(s.equals(""))return;
        if(toBinary){
            String[] splits = s.split(" ");
            int a = Integer.parseInt(splits[0]);
            String bitstringA = Utils.integerToBinary(a);
            String type = splits[1];
            String bitTypeRep = Utils.getBitRep(type);
            int b = Integer.parseInt(splits[2]);
            String bitStringB = Utils.integerToBinary(b);
            String totalBitString = bitTypeRep + bitstringA + bitStringB;
            output.add(totalBitString);
        }else {
            int out = Integer.parseInt(s,2);
            String outString = out+"";
            output.add(outString);
        }
    }

    @Override
    public void deltaInt() {
        output.poll();
    }

    @Override
    public void deltaConf(String s, double elapsed) {
        output.poll();
        if(s.equals(""))return;
        if(toBinary){
            String[] splits = s.split(" ");
            int a = Integer.parseInt(splits[0]);
            String bitstringA = Utils.integerToBinary(a);
            String type = splits[1];
            String bitTypeRep = Utils.getBitRep(type);
            int b = Integer.parseInt(splits[2]);
            String bitStringB = Utils.integerToBinary(b);
            String totalBitString = bitTypeRep + bitstringA + bitStringB;
            output.add(totalBitString);
        }else {
            int out = Integer.parseInt(s,2);
            String outString = out+"";
            output.add(outString);
        }
    }

    @Override
    public double timeAdvance() {
        if(output.size()>0){
            return t;
        }else {
            return Network.INFINITY;
        }
    }

    /**
     * Always going to be true, as there is only one input to the binary converter
     * @return
     */
    @Override
    public boolean recievedAllInput() {
        return true;
    }
}
