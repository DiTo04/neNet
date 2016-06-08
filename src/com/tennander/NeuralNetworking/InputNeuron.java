/**
 * Created by David Tennander, 8 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

/**
 *A neuron that always fires a specified value.
 * @author David Tennander, 8 juni 2016
 */
public class InputNeuron extends Neuron {
    private float mInput;

    /**
     * Creates a Inputneuron with special sending value.
     */
    public InputNeuron(float pValue) {
        super();
        mInput = pValue;
    }
    
    @Override
    public float fire() {
        return mInput;   
    }

    /**
     * @param pInput the input to set
     */
    public void setInput(float pInput) {
        mInput = pInput;
    }

    /* (non-Javadoc)
     * @see com.tennander.NeuralNetworking.Neuron#derivativeOfActivation(float)
     */
    @Override
    float derivativeOfActivation(float pOutput){
        return 0; //No need for derivative here as it is not used on inputs.
    }
    
    
}
