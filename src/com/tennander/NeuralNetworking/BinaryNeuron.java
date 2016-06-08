/**
 * Created by David Tennander, 8 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

/**
 *
 * @author David Tennander, 8 juni 2016
 */
public class BinaryNeuron extends Neuron {

    private float mThreshold;

    /**
     * Creates a Neuron with specified weight and threshold.
     * @param pThreshold
     */
    public BinaryNeuron(float pThreshold) {
        super();
        mThreshold = pThreshold;
    }

    /**
     * 
     */
    @Override
    public float fire() {
        float tInputSum = getInputSum();
        return tInputSum>=mThreshold? 1:0;
    }



    /**
     * @return the threshold
     */
    public float getThreshold() {
        return mThreshold;
    }

    /**
     * @param pThreshold the threshold to set
     */
    public void setThreshold(float pThreshold) {
        mThreshold = pThreshold;
    }

    /* (non-Javadoc)
     * @see com.tennander.NeuralNetworking.Neuron#derivativeOfActivation(float)
     */
    @Override
    float derivativeOfActivation(float pOutput) {
        return 1f; //Approximation of derivative.
    }

}
