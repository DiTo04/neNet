/**
 * Created by David Tennander, 9 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.pride.tools.pkl_parser.PklFile;

/**
 *
 * @author David Tennander, 9 juni 2016
 */
public class SigmoidlearningTest {

    private PklFile mData;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
       File tSourceFile = new File("lib/neural-networks/data/mnist.pkl");
       System.out.println(tSourceFile.canRead());
       mData = new PklFile(tSourceFile);
       System.out.println(mData.getFileIndex() );
    }

    @Test
    public void test() {
        fail("Not yet implemented");
    }

}
