/**
 * Created by David Tennander, 9 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import Jama.Matrix;
import mnist.tools.MnistImageFile;
import mnist.tools.MnistLabelFile;
import mnist.tools.MnistManager;

/**
 *
 * @author David Tennander, 9 juni 2016
 */
public class SigmoidlearningTest {


    

    private MNISTDataHandler mManeger;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        mManeger = new MNISTDataHandler("lib/mnistData/train-images.idx3-ubyte", "lib/mnistData/train-labels.idx1-ubyte");
        
    }

    @Test
    public void test() {
       
    }

}
