/**
 * Created by David Tennander, 9 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Jama.Matrix;

/**
 *
 * @author David Tennander, 9 juni 2016
 */
public class MNISTDataHandlerTest {


    private static final String TRAIN_LABELS_STRING = "lib/mnistData/train-labels.idx1-ubyte";
    private static final String TRAIN_IMAGES = "lib/mnistData/train-images.idx3-ubyte";

    private MNISTDataHandler mDataHandler;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        mDataHandler = new MNISTDataHandler(TRAIN_IMAGES, TRAIN_LABELS_STRING);

    }

    @Test
    public void testMatrixConversion(){
        int[][] tTestMatrix = new int[][]{{2, 2},{2,2},{2,2}};
        Matrix tExpectedResult = new Matrix(new double[][]{{1,1},{1,1},{1,1}});
        int tScaleFactor = 2;
        Matrix tResult = mDataHandler.convertToMatrix(tTestMatrix, tScaleFactor);
        assertArrayEquals(tExpectedResult.getArray(), tResult.getArray());
    }

    @Test
    public void canGetListOfMatrix() throws IOException {
        List<Matrix> tList = mDataHandler.getListOfImages();
        assertFalse(tList.isEmpty());

    }

}
