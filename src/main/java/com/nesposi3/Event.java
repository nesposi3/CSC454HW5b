package com.nesposi3;

public class Event implements Comparable<Event> {
    private Model model;
    private TimePair timePair;
    private EventType eventType;

    public Event(Model m, TimePair t, EventType e){
        this.model = m;
        this.timePair = t;
        this.eventType = e;
    }

    public TimePair getTimePair() {
        return timePair;
    }

    public EventType getEventType() {
        return eventType;
    }


    @Override
    public int compareTo(Event event) {
        return this.timePair.compareTo(event.timePair);
    }

    @Override
    public String toString() {
        return "{ "+model.name + " " +eventType + " " + timePair.toString() + " }";
    }
}
