/**
 * Created by David Tennander, 8 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

/**
 *Test class for the Neuron class.
 * @author David Tennander, 8 juni 2016
 */
public class NeuronTest {

    private BinaryNeuron mNeuronA;
    private BinaryNeuron mNeuronB;
    private BinaryNeuron mNeuronC;
    private Neuron mNeuron;
    private static float sThreshold = 1;

    /**
     * Setting up three Neurons for testing. 
     */
    @Before
    public void creatingNeurons(){
        mNeuronA = new BinaryNeuron(sThreshold);
        mNeuronB = new BinaryNeuron(sThreshold);
        mNeuronC = new BinaryNeuron(sThreshold);
        mNeuron = new BinaryNeuron(sThreshold);
        assertNotNull(mNeuronA);
        assertNotNull(mNeuronB);
        assertNotNull(mNeuronC);
    }
    @Test
    public void constructorTestBinary(){
        float tThreshold = 0.6f;
        BinaryNeuron tNeuron = new BinaryNeuron(tThreshold);
        assertEquals(tThreshold, tNeuron.getThreshold(), 0);
    }
    
    @Test
    public void constructorTestInput(){
        float tInput = 0.4f;
        InputNeuron tNeuron = new InputNeuron(tInput);
        assertEquals(tInput, tNeuron.fire(), 0);
    }
    
    @Test
    public void connectionTest(){
        float tWeight = 0.5f;
        Set<Neuron> tExpectedSet = new HashSet<>();
        tExpectedSet.add(mNeuronB);
        tExpectedSet.add(mNeuronC);
        for (Neuron tNeuron : tExpectedSet) {
            mNeuronA.connect(tNeuron,tWeight);
        }
        Set<Neuron> tSet = mNeuronA.getConnections()
                             .stream()
                             .map(Entry::getKey)
                             .collect(Collectors.toCollection(HashSet::new));
        assertEquals(tExpectedSet, tSet);
        
    }
    
    @Test
    public void connectionLinkTest(){
        mNeuronA.setThreshold(1);
        InputNeuron tInput = new InputNeuron(1);
        mNeuronA.connect(tInput, 1);
        boolean tResult = mNeuronA.fire()==1;
        assertTrue(tResult);
        
        tInput.setInput(0);
        tResult = mNeuronA.fire() == 1;
        assertFalse(tResult);
    }

    @Test
    public void updatingWeights(){
        float tLearningRate = 0.25f;
        InputNeuron tInput1 = new InputNeuron(1);
        InputNeuron tInput2 = new InputNeuron(1);
        mNeuron.connect(tInput1, 0.5f);
        mNeuron.connect(tInput2, 0.5f);
        
        float tOutput = mNeuron.fire();
        float tError = 0-tOutput;
        mNeuron.updateAllConnectedWeights(tError, tLearningRate);
        //Assert weights have changed.
        assertNotEquals(tOutput, mNeuron.fire(), 0);
    }
}
