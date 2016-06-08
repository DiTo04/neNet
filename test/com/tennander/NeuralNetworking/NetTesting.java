/**
 * Created by David Tennander, 8 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import static org.junit.Assert.*;

import java.util.Map.Entry;
import java.util.Set;

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

    @Test
    public void learingTheXORgate(){
        float[][][] tPatterns =  {{{0f,1f},{0f}},
                                  {{1f,1f},{1f}},
        };
        
        Neuron tXOR = new SigmoidNeuron();
        Neuron tUpper = new SigmoidNeuron();
        Neuron tLower = new SigmoidNeuron();
        InputNeuron tUpperInput = new InputNeuron(0f);
        InputNeuron tLowerInput = new InputNeuron(0f);
        
        //connections
        tXOR.connect(tUpper, 1f);
        tXOR.connect(tLower, 0f);
        tUpper.connect(tUpperInput, 0f);
        tUpper.connect(tLowerInput, 1f);
        tLower.connect(tUpperInput, 0f);
        tLower.connect(tLowerInput, 0f);
        
        
        while(true){
            float tTotaltError = 0;
            for (float[][] tPattern : tPatterns) {
                float[] tInput = tPattern[0];
                float tExpectedResult = tPattern[1][0];
                System.out.format("Input: %f + %f -> %f%n",tInput[0], tInput[1],tExpectedResult);
                
                tUpperInput.setInput(tInput[1]);
                tLowerInput.setInput(tInput[0]);
                float tOutput = tXOR.fire();
                float tError = tExpectedResult-tOutput;
                tXOR.updateAllConnectedWeights(tError, 0.25f);
                tXOR.getConnections().stream().mapToDouble(e ->(double) e.getValue()).forEachOrdered(e -> {
                    System.out.format("%f, ", e);
                });
                System.out.println();
                
                tTotaltError += Math.pow(tError, 2);
                System.out.format("Output: %f%n", tOutput);
            }
            if(tTotaltError < 0.0001){
                break;
            }
        }
    }
}


