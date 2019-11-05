package com.nesposi3;

public class EventPriorityQueue {
    private Event[] events;
    private int count = 0;
    public EventPriorityQueue(){
        this.events = new Event[0];
    }
    public void add(Event e){
        int k = count++;
        // array not big enough, resize
        if(count > events.length){
            Event[] newArr = new Event[count];
            System.arraycopy(events, 0, newArr, 0, events.length);
            this.events = newArr;
        }
        events[k] = e;
        siftUp(k);
    }
    public Event take(){
        if(count==0){
            return null;
        }
        Event out = events[0];
        events[0] = events[--count];
        siftDown(0);
        return out;
    }
    private void siftDown(int k){
        while(k<count){
            int l = left(k);
            int r = right(k);
            int c;
            if(r>=count || events[l].compareTo(events[r])<0){
                c=l;
            }else c =r;
            if(events[k].compareTo(events[c])<0){
                k = c;
            }else break;
        }
    }
    private void siftUp(int k){
        while(k!=0){
            int p = parent(k);
            if(events[k].compareTo(events[p])<0){
                Event e = events[k];
                events[k] = events[p];
                events[p] = e;
            }else break;
        }
    }
    private int parent(int k){
        return (k-1) >>>1;
    }
    private int left(int k){
        return (k << 1) + 1;
    }
    private int right(int k){
        return (k << 1) + 2;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (Event e: events
             ) {
            builder.append(e.toString());
            builder.append(", ");
        }
        builder.append("]");
        return builder.toString();
    }
}
