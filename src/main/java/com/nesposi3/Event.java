package com.nesposi3;

public class Event<Input> implements Comparable<Event> {
    private Model model;

    private TimePair timePair;
    private EventType eventType;
    private Input input;

    public Event(Model m, TimePair t, EventType e,Input input){
        this.model = m;
        this.timePair = t;
        this.eventType = e;
        this.input = input;
    }
    public Event(Model m,TimePair t,EventType e){
        this.model = m;
        this.timePair = t;
        this.eventType = e;
        this.input = null;
    }

    public TimePair getTimePair() {
        return timePair;
    }

    public EventType getEventType() {
        return eventType;
    }


    @Override
    public int compareTo(Event event) {
        if(this.timePair.compareTo(event.timePair)!=0){
            return this.timePair.compareTo(event.timePair);
        }
        if(!(this.model.equals(event.model))){
            return this.model.compareTo(event.model);
        }
        if(!(this.eventType==event.eventType)){
            return this.eventType.compareTo(event.eventType);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "{ "+model.name + " " +eventType + " " + timePair.toString() + " }";
    }

    public Input getInput() {
        return input;
    }

    public Model getModel() {
        return model;
    }
    public boolean isConfluent(Event other){
        return ((this.model== other.model) && (this.timePair.equals(other.timePair))
                && (this.eventType==EventType.DELTAEXT) && (other.eventType== EventType.DELTAINT));
    }

    public void setTimePair(TimePair timePair) {
        this.timePair = timePair;
    }
}
