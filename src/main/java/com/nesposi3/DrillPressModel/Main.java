package com.nesposi3.DrillPressModel;

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
            HashMap<String,Integer> map= new HashMap<>();
            while (sc.hasNext()){
                String line = sc.nextLine();
                String time = line.split(",")[0];
                String num = line.split(",")[1];
                int p = Integer.parseInt(num);
                map.put(time,p);
            }
            int defPair = -1;
            MachineModel press = new MachineModel(1,"press",defPair);
            MachineModel drill = new MachineModel(2,"drill",defPair);
            Network<Integer,Integer> network = new Network<>(debug);
            Port<Integer> port  = new Port<>(defPair,drill);
            Port<Integer> pressPort = new Port<>(defPair,press);
            press.addPort(pressPort);
            network.addInputPipe(pressPort);
            drill.addPort(port);
            press.addPipe(port,defPair);
            Port<Integer> networkOutputPort;
            network.setFirstChild(press);
            network.setFinalChild(drill);
            network.initializeQueue(map);
            network.simulate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
