/**
 * Created by David Tennander, 9 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import Jama.Matrix;
import mnist.tools.MnistImageFile;
import mnist.tools.MnistLabelFile;
import mnist.tools.MnistManager;

/**
 *
 * @author David Tennander, 9 juni 2016
 */
public class  MNISTDataHandler extends MnistManager implements Serializable{


    private static final int EIGHT_BIT_VALUE = 255;
    private ArrayList<Matrix> mImageList;
    private ArrayList<Integer> mLabelList;

    /**
     * @param pString
     * @param pString2
     * @throws IOException 
     */
    public MNISTDataHandler(String pImageString, String pLabelString) throws IOException {
        super(pImageString, pLabelString);
    }

    public List<Matrix> getListOfImages() throws IOException {
        if(mImageList == null) {
            MnistImageFile tImages = this.getImages();
            ArrayList<Matrix> tResultList = new ArrayList<>();

            for (int tI = 0; tI < tImages.getCount(); tI++) {
                int[][] tImage = tImages.readImage();
                Matrix tMatrix = convertToMatrix(tImage, EIGHT_BIT_VALUE);
                tResultList.add(tMatrix);
            }
            mImageList = tResultList;
        }


        return mImageList;

    }
    public List<Integer> getListOfLabels() throws IOException {
        if(mLabelList == null) {
            MnistLabelFile tLabels = this.getLabels();
            ArrayList<Integer> tResultList = new ArrayList<>();
            for (int tI = 0; tI < tLabels.getCount(); tI++) {
                int tLabel = tLabels.readLabel();
                tResultList.add(tLabel);
            }
            mLabelList = tResultList;
        }
        return mLabelList;
    }
    /**
     * Creates a matrix representation of the given image.
     * @param pImage
     * @return
     */
    public Matrix convertToMatrix(int[][] pImage, int pScaleFactor) {
        LinkedList<Double> tList = new LinkedList<>();
        for (int[] tRow : pImage) {
            for (int tElement : tRow) {
                double td = (double)tElement/(double)pScaleFactor;
                tList.add(td);
            }
        }
        double[] tArray = tList.stream().mapToDouble(Double::doubleValue).toArray();
        return (new Matrix(new double[][]{ tArray})).transpose();
    }

}
