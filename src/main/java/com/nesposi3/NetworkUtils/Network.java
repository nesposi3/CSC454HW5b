package com.nesposi3.NetworkUtils;

import com.nesposi3.EventUtils.Event;
import com.nesposi3.EventUtils.EventPriorityQueue;
import com.nesposi3.EventUtils.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Network<Input, Output> {
    public static final Double INFINITY = Double.MAX_VALUE;
    public TimePair globalTime;
    public EventPriorityQueue eventPriorityQueue;
    private Model<?, Output> finalChild;
    private boolean debug;
    private List<NetworkInputPipe<Input>> inputPipes;
    private List<Pipe<Output>> outputPipes;

    public Network(boolean debugFlag) {
        this.debug = debugFlag;
        this.globalTime = new TimePair(0.0);
        this.eventPriorityQueue = new EventPriorityQueue();
        this.inputPipes = new ArrayList<>();
        this.outputPipes = new ArrayList<>();
    }

    public void initializeQueue(Map<String, Input> inputs) {
        for (String s : inputs.keySet()) {
            double time = Double.parseDouble(s);
            Input num = inputs.get(s);
            for (NetworkInputPipe n: inputPipes
                 ) {
                Event<Input> e = new Event<>(n.getModel(), new TimePair(time), EventType.DELTAEXT, num);
                eventPriorityQueue.add(e);
            }
        }
    }

    public void simulate() {
        while (!eventPriorityQueue.isEmpty()) {
            Event<?> e = eventPriorityQueue.take();
            if (debug) {
                System.out.println(e);
            }
            globalTime = e.getTimePair();
            double elapsed = globalTime.getReal() - e.getModel().lastEventTime;
            if (e.getEventType() == EventType.DELTAEXT) {
                //Check if confluent
                Model model = e.getModel();
                if (!eventPriorityQueue.isEmpty()) {
                    Event next = eventPriorityQueue.peek();
                    if (e.isConfluent(next)) {
                        //Confluent case, remove old internal, combine and add back to queue

                        //Removes the internal event at the same time
                        eventPriorityQueue.take();
                        //Create new event and add it to queue
                        Event newConf = new Event(model, e.getTimePair(), EventType.DELTACONF, e.getInput());
                        eventPriorityQueue.add(newConf);
                    } else {
                        //Not confluent, do deltaExt, try to reweight internal, if not there, add;
                        model.deltaExt(e.getInput(), elapsed);
                        if(Double.compare(model.timeAdvance(),INFINITY)!=0){
                            TimePair nextInternal = e.getTimePair().advanceBy(model.timeAdvance());
                            eventPriorityQueue.deleteInternal(model);
                            Event<?> newInternal = new Event<>(model, nextInternal, EventType.DELTAINT);
                            eventPriorityQueue.add(newInternal);
                        }
                    }
                } else {
                    //Not confluent, do deltaExt, try to reweight internal, if not there, add;
                    model.deltaExt(e.getInput(), elapsed);
                    if(Double.compare(model.timeAdvance(),INFINITY)!=0){
                        TimePair nextInternal = e.getTimePair().advanceBy(model.timeAdvance());
                        eventPriorityQueue.deleteInternal(model);
                        Event<?> newInternal = new Event<>(model, nextInternal, EventType.DELTAINT);
                        eventPriorityQueue.add(newInternal);
                    }
                }
            } else if (e.getEventType() == EventType.DELTAINT) {
                // Preform deltaInternal on model, create a new deltaExternal for next model, create new delta internal if needed
                Model<?, ?> model = e.getModel();
                //If model is our output model, print its output
                if ((model.equals(this.finalChild)) || debug) {
                    System.out.println(e.getTimePair() + " " + model.name + ":" + model.lambda());
                }
                TimePair nextExternalTime = (e.getTimePair().advanceBy(0));
                // Go through all the pipes of a model, and check to see if deltaExternal events need to be made
                for (Pipe pipe : model.getPipes()) {
                    pipe.shiftVal(model.lambda());
                    Model next = pipe.getNextModel();
                    // Model side check if all input ports have been set, if yes, new deltaExternal
                    if (next.recievedAllInput()) {
                        Event newExternal = new Event(next, nextExternalTime, EventType.DELTAEXT, model.lambda());
                        eventPriorityQueue.add(newExternal);
                    }
                }
                model.deltaInt();
                //Create new deltaInt if needed
                if (Double.compare(model.timeAdvance(), INFINITY) != 0) {
                    TimePair nextInternal = e.getTimePair().advanceBy(model.timeAdvance());
                    eventPriorityQueue.deleteInternal(model);
                    Event<?> newInternal = new Event<>(model, nextInternal, EventType.DELTAINT);
                    eventPriorityQueue.add(newInternal);

                }

            } else if (e.getEventType() == EventType.DELTACONF) {
                Model model = e.getModel();
                if (model.equals(this.finalChild) || debug) {
                    System.out.println(e.getTimePair() + " " + model.name + ":" + model.lambda());
                }
                model.deltaConf(e.getInput(), elapsed);
                Model<?, ?> specificModel = e.getModel();
                TimePair nextExternalTime = (e.getTimePair().advanceBy(0));

                // Go through all the pipes of a model, and check to see if deltaExternal events need to be made
                for (Pipe pipe : specificModel.getPipes()) {
                    pipe.shiftVal(specificModel.lambda());
                    Model next = pipe.getNextModel();
                    // Model side check if all input ports have been set, if yes, new deltaExternal
                    if (next.recievedAllInput()) {
                        Event newExternal = new Event(next, nextExternalTime, EventType.DELTAEXT, specificModel.lambda());
                        eventPriorityQueue.add(newExternal);
                    }
                }
                //Create new deltaInt
                TimePair nextInternal = e.getTimePair().advanceBy(specificModel.timeAdvance());
                eventPriorityQueue.deleteInternal(model);
                Event<?> newInternal = new Event<>(model, nextInternal, EventType.DELTAINT);
                eventPriorityQueue.add(newInternal);
            }
            e.getModel().lastEventTime = globalTime.getReal();
        }
    }
    public void addInputPipe(Port<Input> port) {
        NetworkInputPipe<Input> pipe = new NetworkInputPipe<>(port);
        this.inputPipes.add(pipe);
    }

    /**
     * Tell the network which child o get its output from
     *
     * @param m
     */
    public void setFinalChild(Model<?, Output> m) {
        this.finalChild = m;
    }
    public void printOutput(){
        for (Pipe<Output> p :outputPipes) {

        }
    }
}
