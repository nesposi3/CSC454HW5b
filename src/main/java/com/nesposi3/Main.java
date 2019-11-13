package com.nesposi3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
            HashMap<String,Pair<Integer,Double>> map= new HashMap<>();
            while (sc.hasNext()){
                String line = sc.nextLine();
                String time = line.split(",")[0];
                String num = line.split(",")[1];
                Pair<Integer,Double> p = new Pair<>(Integer.parseInt(num),0.0);
                map.put(time,p);
            }
            Pair<Integer,Double> defPair = new Pair<Integer, Double>(-1,-1.0);
            MachineModel press = new MachineModel(2,"press",defPair);
            MachineModel drill = new MachineModel(1,"drill",defPair);
            Network<Pair<Integer,Double>,Pair<Integer,Double>> network = new Network<>(debug);
            Port<Pair<Integer,Double>> port  = new Port<>(defPair,drill);
            drill.addPort(port);
            press.addPipe(port,defPair);
            network.setFirstChild(press);
            network.setFinalChild(drill);
            network.initializeQueue(map);
            network.simulate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
