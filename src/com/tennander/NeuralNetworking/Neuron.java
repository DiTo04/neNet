/**
 * Created by David Tennander, 8 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David Tennander, 8 juni 2016
 */
public class Neuron {
    
    private float mWeight;
    private float mThreshHold;
    private List<Neuron> mConnections;
    
    /**
     * Creates a Neuron with specified weight and threshold.
     * @param pThreshold
     * @param pWeight
     */
    public Neuron(float pThreshold, float pWeight) {
        mThreshHold = pThreshold;
        mWeight = pWeight;
        mConnections = new ArrayList<Neuron>();
    }

    /**
     * Creates a Neuron with specified threshold and
     * setting weight to 1.
     * @param pThreshhold
     */
    public Neuron(float pThreshold){
        this(pThreshold, 1f);
    }

    /**
     * Creates a Neuron with weight and threshold of  1.
     */
    public Neuron() {
        this(1f);
    }

  

    /**
     * Set the weight of the neuron.
     * @param pWight
     */
    public void setWeight(float pWight) {
        mWeight = pWight;
    }

    /**
     * Returns the Weight of the neuron.
     * @return weight - float
     */
    public float getWeight() {
        return mWeight;
    }

    /**
     * Set the weight as boolean.
     * @param pWight
     */
    public void setWeight(boolean pWight) {
        mWeight = pWight? 1f:0f;        
    }

    /**
     * @return
     */
    public boolean isFired() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * 
     */
    public float fire() {
        // TODO Auto-generated method stub
        return 1;
    }

    /**
     * @param pNeurons
     */
    public void connect(Neuron ... pNeurons) {
        for(Neuron iNeuron:pNeurons) mConnections.add(iNeuron);
        
    }

    /**
     * Returns the Threshold.
     * @return Threshold - float
     */
    public float getThreshold() {
        return mThreshHold;
    }


}
