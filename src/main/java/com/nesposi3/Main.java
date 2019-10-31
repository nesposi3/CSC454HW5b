package com.nesposi3;

public class Main {
    public static void main(String[] args){
        TimePair a = new TimePair(.6);
        TimePair b = new TimePair(2.3);
        TimePair c = new TimePair(.4);
        PressModel p = new PressModel();
        PressModel l = new PressModel();
        PressModel m = new PressModel();
        PressModel n = new PressModel();

        Event eA = new Event(p,a,EventType.DELTAEXT);
        Event eB = new Event(l,b,EventType.DELTAEXT);
        Event eC = new Event(m,c,EventType.DELTAEXT);
        Event eD = new Event(n,a,EventType.DELTAEXT);
        EventPriorityQueue queue = new EventPriorityQueue();
        queue.enqueue(eA);
        queue.enqueue(eB);
        queue.enqueue(eC);
        queue.enqueue(eD);
        System.out.println(queue.toString());
    }

}
