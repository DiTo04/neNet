/**
 * Created by David Tennander, 8 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;

import Jama.Matrix;

/**
 *
 * @author David Tennander, 8 juni 2016
 */
public class Sigmoidtest {

    private SigmoidNetwork mNetwork;
    private Matrix mGoodTestMatrix;
    private Matrix mBadTestMatrix;
    private Matrix mResultMatrix;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        mNetwork = new SigmoidNetwork(2,3,1);
        mResultMatrix = new Matrix(1,1,1);
        mGoodTestMatrix = new Matrix(2, 1, 1);
        mBadTestMatrix = new Matrix(3,1,1);
    }

    @Test
    public void feedforwardTest() {
        assertNotEquals(mNetwork.feedForward(mGoodTestMatrix), mGoodTestMatrix);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void feedforwardExceptionTest(){
        mNetwork.feedForward(mBadTestMatrix);
    }
    
    @Test
    public void backPropTest(){
        mNetwork.backprop(mGoodTestMatrix, mResultMatrix);
    }

}
