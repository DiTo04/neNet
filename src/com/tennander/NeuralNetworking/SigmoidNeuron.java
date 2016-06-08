/**
 * Created by David Tennander, 8 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

/**
 *A Neuron using the Sigmoid activator function.
 * @author David Tennander, 8 juni 2016
 */
public class SigmoidNeuron extends Neuron {

    /* (non-Javadoc)
     * @see com.tennander.NeuralNetworking.Neuron#fire()
     */
    @Override
    public float fire() {
        float tSum = this.getInputSum();
        return (float) (1/(1+Math.exp(-tSum)));
    }

    /* (non-Javadoc)
     * @see com.tennander.NeuralNetworking.Neuron#derivativeOfActivation(float)
     */
    @Override
    float derivativeOfActivation(float pOutput) {
        float tOutput = fire();
        return tOutput*(1-tOutput);
    }

}
