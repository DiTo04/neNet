/**
 * Created by David Tennander, 9 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import Jama.Matrix;
import mnist.tools.MnistImageFile;
import mnist.tools.MnistLabelFile;
import mnist.tools.MnistManager;

/**
 *
 * @author David Tennander, 9 juni 2016
 */
public class MNISTDataHandler {


    private static final int EIGHT_BIT_VALUE = 255;
    private MnistManager mManeger;

    /**
     * @param pString
     * @param pString2
     * @throws IOException 
     */
    public MNISTDataHandler(String pString, String pString2) throws IOException {
        mManeger = new MnistManager(pString, pString2);
    }

    public List<Matrix> getListOfImages() throws IOException {
        MnistImageFile tImages = mManeger.getImages();
        ArrayList<Matrix> tResultList = new ArrayList<>();

        for (int tI = 0; tI < tImages.getCount(); tI++) {
            int[][] tImage = tImages.readImage();
            Matrix tMatrix = convertToMatrix(tImage, EIGHT_BIT_VALUE);
            tResultList.add(tMatrix);
        }
        
        return tResultList;

    }
    public List<Integer> getListOfLabels() throws IOException {
        MnistLabelFile tLabels = mManeger.getLabels();
        ArrayList<Integer> tResultList = new ArrayList<>();
        for (int tI = 0; tI < tLabels.getCount(); tI++) {
            int tLabel = tLabels.readLabel();
            tResultList.add(tLabel);
        }
        return tResultList;
    }
    /**
     * Creates a matrix representation of the given image.
     * @param pImage
     * @return
     */
    public Matrix convertToMatrix(int[][] pImage, int pScaleFactor) {
        
        double[][] tScaledMatrix = Arrays.stream(pImage).map( row -> 
        IntStream.range(0, row.length)
        .mapToDouble(i -> row[i]/pScaleFactor)
        .toArray()
                ).toArray(double[][]::new);
        
        return new Matrix(tScaledMatrix);
    }

}
