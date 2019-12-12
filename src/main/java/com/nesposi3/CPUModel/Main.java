package com.nesposi3.CPUModel;

import com.nesposi3.CPUModel.atomicModels.*;
import com.nesposi3.NetworkUtils.Network;
import com.nesposi3.NetworkUtils.Port;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        File f = new File("traj.txt");
        boolean debug = false;
        for (int i = 0; i <args.length ; i++) {
            if(args[i].equals("-d")|| args[i].equals("--debug")){
                debug = true;
            }
        }
        try {
            Scanner sc = new Scanner(f);
            HashMap<String,String> map= new HashMap<>();
            while (sc.hasNext()){
                String line = sc.nextLine();
                String time = line.split(",")[0];
                String num = line.split(",")[1];
                map.put(time,num);
            }
            Network<String,String> network = new Network<>(debug);
            BinaryConverter binIn = new BinaryConverter(true);
            Port<String> binInPort = new Port<>("",binIn);
            network.addInputPipe(binInPort);

            AndChip andChip = new AndChip();
            Port<String> andPort = new Port<>("", andChip);

            ModuloChip moduloChip = new ModuloChip();
            Port<String> modPort = new Port<>("", moduloChip);

            OrChip orChip = new OrChip();
            Port<String> orPort = new Port<>("", orChip);

            MultiplyChip multiplyChip = new MultiplyChip();
            Port<String> multPort = new Port<>("", multiplyChip);

            XorChip xorChip = new XorChip();
            Port<String> xorPort = new Port<>("", xorChip);

            BinaryConverter binOut = new BinaryConverter(false);
            Port<String> binOutPort = new Port<>("",binOut);

            AddChip addChip = new AddChip();
            Port<String> addPort = new Port<>("", addChip);

            binIn.addPipe(andPort,"");
            binIn.addPipe(modPort,"");
            binIn.addPipe(multPort,"");
            binIn.addPipe(orPort,"");
            binIn.addPipe(xorPort,"");
            binIn.addPipe(addPort,"");

            andChip.addPipe(binOutPort,"");
            moduloChip.addPipe(binOutPort,"");
            multiplyChip.addPipe(binOutPort,"");
            orChip.addPipe(binOutPort,"");
            xorChip.addPipe(binOutPort,"");
            addChip.addPipe(binOutPort,"");

            binOut.setNetworkOutput(true);
            network.initializeQueue(map);
            network.simulate();
        }catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        }
    }
}
