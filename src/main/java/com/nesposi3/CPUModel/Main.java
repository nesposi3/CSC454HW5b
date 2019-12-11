package com.nesposi3.CPUModel;

import com.nesposi3.CPUModel.atomicModels.*;
import com.nesposi3.NetworkUtils.Model;
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

            And and = new And();
            Port<String> andPort = new Port<>("",and);

            Modulo modulo = new Modulo();
            Port<String> modPort = new Port<>("",modulo);

            Or or = new Or();
            Port<String> orPort = new Port<>("",or);

            Multiply multiply = new Multiply();
            Port<String> multPort = new Port<>("",multiply);

            Xor xor = new Xor();
            Port<String> xorPort = new Port<>("",xor);

            BinaryConverter binOut = new BinaryConverter(false);
            Port<String> binOutPort = new Port<>("",binOut);

            Add add = new Add();
            Port<String> addPort = new Port<>("",add);

            binIn.addPipe(andPort,"");
            binIn.addPipe(modPort,"");
            binIn.addPipe(multPort,"");
            binIn.addPipe(orPort,"");
            binIn.addPipe(xorPort,"");
            binIn.addPipe(addPort,"");

            and.addPipe(binOutPort,"");
            modulo.addPipe(binOutPort,"");
            multiply.addPipe(binOutPort,"");
            or.addPipe(binOutPort,"");
            xor.addPipe(binOutPort,"");
            add.addPipe(binOutPort,"");

            binOut.setNetworkOutput(true);
            network.initializeQueue(map);
            network.simulate();
        }catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        }
    }
}
