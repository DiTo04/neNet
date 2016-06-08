/**
 * Created by David Tennander, 8 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 *Test class for the Neuron class.
 * @author David Tennander, 8 juni 2016
 */
public class NeuronTest {

    private Neuron mNeuronA;
    private Neuron mNeuronB;
    private Neuron mNeuronC;

    /**
     * Setting up three Neurons for testing. 
     */
    @Before
    public void creatingNeurons(){
        mNeuronA = new Neuron();
        mNeuronB = new Neuron();
        mNeuronC = new Neuron();
        assertNotNull(mNeuronA);
        assertNotNull(mNeuronB);
        assertNotNull(mNeuronC);
    }
    @Test
    public void constructorTest1(){
        float tThreshold = 0.6f;
        Neuron tNeuron = new Neuron(tThreshold);
        assertEquals(tThreshold, tNeuron.getThreshold(), 0);
    }
    
    @Test
    public void constructorTest2(){
        float tThreshold = 0f;
        Neuron tNeuron = new Neuron();
        assertEquals(tThreshold, tNeuron.getThreshold(), 0);
    }
    
    @Test
    public void lifeCycleTest(){
        assertFalse(mNeuronA.isFired());
        mNeuronA.fire();
        assertTrue(mNeuronA.isFired());
    }
    
    
    /**
     * Testing XOR gate of structure: 
     * @see <a href="http://i.stack.imgur.com/nRZ6z.png">http://i.stack.imgur.com/nRZ6z.png</a>
     */
   // @Test
    public void xorTest(){
        Neuron tXOR = new Neuron(1.5f);
        Neuron tUpper = new Neuron(0.5f);
        Neuron tLower = new Neuron(-1.5f);

        Neuron tUpperInput = new Neuron(0f);
        Neuron tLowerInput = new Neuron(0f);
        
        //Connections
        tXOR.connect(tUpper, tLower);

        
        // 0 XOR 0 -> 0
        tUpperInput.setWeight(false);
        tLowerInput.setWeight(false);
        
        
        
    }


}
