package com.nesposi3;

public class Main {
    public static void main(String[] args){
        TimePair a = new TimePair(.6);
        TimePair b = new TimePair(2.3);
        TimePair c = new TimePair(.4);
        MachineModel p = new MachineModel();
        MachineModel l = new MachineModel();
        MachineModel m = new MachineModel();
        MachineModel n = new MachineModel();

        Event eA = new Event(p,a,EventType.DELTAEXT);
        Event eB = new Event(l,b,EventType.DELTAEXT);
        Event eC = new Event(m,c,EventType.DELTAEXT);
        Event eD = new Event(n,a,EventType.DELTAEXT);
        EventPriorityQueue queue = new EventPriorityQueue();
        queue.add(eA);
        queue.add(eB);
        queue.add(eC);
        queue.add(eD);
        System.out.println(queue.toString());
    }

}
