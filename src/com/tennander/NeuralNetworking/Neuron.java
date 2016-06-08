/**
 * Created by David Tennander, 8 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author David Tennander, 8 juni 2016
 */
public abstract class Neuron {

    private Map<Neuron, Float> mConnections;


    /**
     * 
     */
    public Neuron() {
        mConnections = new HashMap<>();
    }

    /**
     * A method returning the result after calculating 
     * the input and checking threshold.
     * @return result - float
     */
    public abstract float fire(); 

    
    /**
     * The derivative of the activation function.
     * @param pOutput
     * @return
     */
    abstract float derivativeOfActivation(float pOutput);

    /**
     * Creates a connection between this Neuron and a underlying one.
     * With specifed weight.
     * @param pNeuron
     * @param pWeight
     */
    public void connect(Neuron pNeuron, float pWeight) {
        mConnections.put(pNeuron, pWeight); 
    }

    protected float getInputSum() {
        float tInputSum = 0;
        for (Entry<Neuron, Float> tEntry : mConnections.entrySet()) {
            Float tWeight = tEntry.getValue();
            float tSignal = tEntry.getKey().fire();
            tInputSum += tSignal*tWeight;
        }
        return tInputSum;
    }

    /**
     * Returns the connected Neurons.
     * @return Neurons - Set<Neuron>
     */
    public Set<Entry<Neuron, Float>> getConnections() {
        return mConnections.entrySet();
    }

    /**
     * @param pError
     * @param pLearningRate 
     */
    public void updateAllConnectedWeights(float pError, float pLearningRate) {
        LinkedList<Neuron> tQueue = new LinkedList<>();
        tQueue.add(this);
        while(!tQueue.isEmpty()){
            Neuron tNeuron = tQueue.pop();
            tNeuron.updateWeightAndAddChildrenToQueue(pError,pLearningRate,tQueue);
        }    
    }

    /**
     * @param pError
     * @param pLearningRate
     * @param pQueue
     */
    private void updateWeightAndAddChildrenToQueue(float pError, float pLearningRate, LinkedList<Neuron> pQueue) {
        float tOutput = this.fire();
        for (Entry<Neuron, Float> tEntry : mConnections.entrySet()) {
            Neuron tChild = tEntry.getKey();
            float tWeight = tEntry.getValue();
            float tNewWeight = tWeight + pLearningRate*pError*tChild.fire()*derivativeOfActivation(tOutput);
            tEntry.setValue(tNewWeight);
            if(!pQueue.contains(tChild)) {
                pQueue.add(tChild);
            }
        }    
    }


}
