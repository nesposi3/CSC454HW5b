package com.nesposi3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Network<Input,Output>{
    public TimePair globalTime;
    public EventPriorityQueue eventPriorityQueue;
    private Model<Input,?> firstChild;
    private Model<?, Output> finalChild;
    private List<Model<?,?>> childList;
    private boolean debug;
    public Network(boolean debugFlag){
        this.childList = new ArrayList<>();
        this.debug = debugFlag;
        this.globalTime = new TimePair(0.0);
        this.eventPriorityQueue = new EventPriorityQueue();
    }

    public void addModel(Model<?,?> m){
        this.childList.add(m);
    }
    public void initializeQueue(Map<String,Input> inputs){
        for(String s:inputs.keySet()){
            double time = Double.parseDouble(s);
            Input num = inputs.get(s);
            Event<Input> e = new Event<>(this.firstChild,new TimePair(time),EventType.DELTAEXT,num);
            eventPriorityQueue.add(e);
        }
        System.out.println(eventPriorityQueue.toString());
    }
    public void simulate(){
        while(!eventPriorityQueue.isEmpty()){
            Event<?> e = eventPriorityQueue.take();
            globalTime= e.getTimePair();
            if(e.getEventType()==EventType.DELTAEXT){
                //Check if confluent
                Event next = eventPriorityQueue.peek();
                if(e.isConfluent(next)){
                    //Confluent case
                } else{
                    //Not confluent, do deltaExt, remove previous internal, add new internal
                    e.getModel().deltaExt(e.getInput());
                    e.getModel().setTimeOfLastDeltaExt(e.getTimePair());
                }
            }else if(e.getEventType()==EventType.DELTAINT){
                // Preform deltaInternal on model, create a new deltaExternal for
                Model<?,?> model = e.getModel();
                //If model is our output model, print its output
                if((model.equals(this.finalChild)) || debug){
                    System.out.println(model.lambda());
                }
                model.deltaInt();
                TimePair nextExternalTime = (e.getTimePair().advanceBy(0));
                // Go through all the pipes of a model, and check to see if deltaExternal events need to be made
                for (Pipe pipe:model.getPipes()){
                    pipe.shiftVal(model.lambda());
                    Model next = pipe.getNextModel();
                    // Model side check if all input ports have been set, if yes, new deltaExternal
                    if(next.recievedAllInput()){
                        Event newExternal = new Event(next,nextExternalTime,EventType.DELTAEXT,model.lambda());
                        eventPriorityQueue.add(newExternal);
                    }
                }

            }
        }

    }

    /**
     * Tell the network which child to give its input to
     * @param m
     */
    public void setFirstChild(Model<Input,?> m){
        this.firstChild = m;
    }

    /**
     * Tell the network which child o get its output from
     * @param m
     */
    public void setFinalChild(Model<?,Output> m){
        this.finalChild = m;
    }

}
