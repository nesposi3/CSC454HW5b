package com.nesposi3;

public class EventPriorityQueue {
    private Event[] events;
    public EventPriorityQueue(){
        this.events = new Event[0];
    }
    public void enqueue(Event event){
        Event[] newEvents = new Event[this.events.length+1];
        if(this.events.length==0){
            // First element added manually
            newEvents[0] = event;
            this.events = newEvents;
        }else{
            for (int i = 0; i <this.events.length; i++) {
                Event queueEvent = this.events[i];
                if(event.compareTo(queueEvent) > 0){
                    // If event is older than one on queue, put it in its place, bump everyone else up
                    newEvents[i] = event;
                    System.arraycopy(this.events, i, newEvents, i + 1, newEvents.length - (i + 1));
                    this.events = newEvents;
                    break;
                }else{
                    // If an event is newer or equal, just add old events
                    newEvents[i] = this.events[i];
                }
            }
        }

    }
    public Event dequeue(){
        if(this.events.length==0){
            //Queue empty
            return null;
        }
        Event eventOut = this.events[this.events.length-1];
        Event[] newEvents = new Event[this.events.length-1];
        // Copy all but the last event into new array
        for (int i = 0; i <newEvents.length ; i++) {
            newEvents[i] = this.events[i];
        }
        this.events = newEvents;
        return eventOut;
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
