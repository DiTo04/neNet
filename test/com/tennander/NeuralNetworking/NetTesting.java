/**
 * Created by David Tennander, 8 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author David Tennander, 8 juni 2016
 */
public class NetTesting {
    
    
    /**
     * Testing XOR gate of structure: 
     * @see <a href="http://i.stack.imgur.com/nRZ6z.png">http://i.stack.imgur.com/nRZ6z.png</a>
     */
    @Test
    public void xorTest(){
        Neuron tXOR = new BinaryNeuron(1.5f);
        Neuron tUpper = new BinaryNeuron(0.5f);
        Neuron tLower = new BinaryNeuron(-1.5f);

        InputNeuron tUpperInput = new InputNeuron(0f);
        InputNeuron tLowerInput = new InputNeuron(0f);
        
        //Connections
        tXOR.connect(tUpper, 1);
        tXOR.connect(tLower, 1);
        tUpper.connect(tUpperInput, 1);
        tUpper.connect(tLowerInput, 1);
        tLower.connect(tUpperInput, -1);
        tLower.connect(tLowerInput, -1);
        
        assertFalse(tXOR.fire()==1);
        
        tUpperInput.setInput(1); //on
        tLowerInput.setInput(1);//on
        assertFalse(tXOR.fire()==1);
        
        tUpperInput.setInput(0); 
        tLowerInput.setInput(1);   
        assertTrue(tXOR.fire()==1);
        
        tUpperInput.setInput(1); 
        tLowerInput.setInput(0);    
        assertTrue(tXOR.fire()==1);
        
    }

}
