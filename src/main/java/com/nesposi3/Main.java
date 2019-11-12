package com.nesposi3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        File f = new File("traj.txt");
        try {
            Scanner sc = new Scanner(f);
            HashMap<String,String> map= new HashMap<>();
            while (sc.hasNext()){
                String line = sc.nextLine();
                String time = line.split(",")[0];
                String num = line.split(",")[1];
                map.put(time,num);
            }
            Network<String,String> network = new Network<>(false);
            network.initializeQueue(map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
