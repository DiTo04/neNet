/**
 * Created by David Tennander, 8 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import Jama.Matrix;

/**
 *
 * @author David Tennander, 8 juni 2016
 */
public class Sigmoidtest {

    private static final String PATH_TO_SAVE_SETUP = "testData.neNet";
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
        assertNotEquals(new CompMatrix(mNetwork.feedForward(mGoodTestMatrix).getArrayCopy()), new CompMatrix(mGoodTestMatrix.getArrayCopy()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void feedforwardExceptionTest(){
        mNetwork.feedForward(mBadTestMatrix);
    }

    /**
     * The Network should be able to load setup from file and save it's setup to file.
     * @throws Exception
     */
    @Test
    public void saveAndLoadNeuralNetworkSetup() throws Exception {
        File tFile = new File(PATH_TO_SAVE_SETUP);
        mNetwork.saveSetupToFile(tFile);
        SigmoidNetwork tNewNetwork = new SigmoidNetwork(tFile);
        tFile.delete();
        List<CompMatrix> tNewWeights = tNewNetwork.getWeightMatrexis().stream()
                .map(e -> new CompMatrix(e.getArray()))
                .collect(Collectors.toList());
        List<CompMatrix> tNewBiasis = tNewNetwork.getBiasis().stream()
                .map(e -> new CompMatrix(e.getArray()))
                .collect(Collectors.toList());
        
        List<CompMatrix> tOldWeights = mNetwork.getWeightMatrexis().stream()
                .map(e -> new CompMatrix(e.getArray()))
                .collect(Collectors.toList());
        List<CompMatrix> tOldBiasis = mNetwork.getBiasis().stream()
                .map(e -> new CompMatrix(e.getArray()))
                .collect(Collectors.toList());
        
        assertEquals(tOldWeights, tNewWeights);
        assertEquals(tOldBiasis, tNewBiasis);

    }

    @Test
    public void updateGivenSetOfDataTest(){
        List<Matrix> tW = mNetwork.getWeightMatrexis();
        tW = tW.stream().map(e -> new CompMatrix(e.getArray())).collect(Collectors.toList());
        List<Matrix> tB = mNetwork.getBiasis();
        tB = tB.stream().map(e -> new CompMatrix(e.getArray())).collect(Collectors.toList());
        HashMap<Matrix, Matrix> tSeries = new HashMap<>();
        tSeries.put(mGoodTestMatrix, mResultMatrix);
        mNetwork.updateGivenSetOfData(tSeries.entrySet(), 0.5, true);
        List<Matrix> tWeightMatrexis = mNetwork.getWeightMatrexis().stream()
                .map(e -> new CompMatrix(e.getArray()))
                .collect(Collectors.toList());
        List<Matrix> tBiasis = mNetwork.getBiasis().stream()
                .map(e -> new CompMatrix(e.getArray()))
                .collect(Collectors.toList());
        
        assertNotEquals(tW, tWeightMatrexis);
        assertNotEquals(tB, tBiasis);

    }

}
