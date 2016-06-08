/**
 * Created by David Tennander, 8 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import Jama.Matrix;

/**
 *
 * @author David Tennander, 8 juni 2016
 */
public class SigmoidNetwork {

    private List<Matrix> mWeightMatrexis;
    private List<Matrix> mBiasis;

    /**
     * Creates a neural network. Containing Sigmoid neutrons.
     * @param pLvlSizes
     */
    public SigmoidNetwork(int ... pLvlSizes ) {
        mWeightMatrexis = new ArrayList<Matrix>(pLvlSizes.length);
        mBiasis = new ArrayList<>(pLvlSizes.length);
        //Iterate and generate random matrixis for each lvl. No weights and biasis for first lvl input.
        for (int tI = 1; tI < pLvlSizes.length; tI++) {
            int tThisLvlSize = pLvlSizes[tI];
            int tLvlSizeOneLvlBefore = pLvlSizes[tI-1];
            mWeightMatrexis.add(Matrix.random(tThisLvlSize, tLvlSizeOneLvlBefore));
            mBiasis.add(Matrix.random(tThisLvlSize, 1));            
        }
    }

    public Matrix feedForward(Matrix a) {
        try{
            for (int tI = 0; tI < mWeightMatrexis.size(); tI++) {
                Matrix tW = mWeightMatrexis.get(tI);
                Matrix tB = mBiasis.get(tI);
                Matrix tZ = tW.times(a).plus(tB);
                a = sigmoid(tZ);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The input diemnsion need to match the input layer of the network.");
        }
        return a;
    }
    

    
    public List<List<Matrix>> backprop(Matrix pInput,Matrix pDesiredResult){
        int tL = mWeightMatrexis.size();
        List<Matrix> tNablaW = new ArrayList<>(tL);
        List<Matrix> tNablaB = new ArrayList<>(tL);
        List<List<Matrix>> tResultList = new ArrayList<>(2);
        tResultList.add(tNablaW);
        tResultList.add(tNablaB);
        
        List<Matrix> tActivations = new ArrayList<>(tL);
        List<Matrix> tWeigtedInputs = new ArrayList<>(tL);
        //FeedForward and save all data.
        Matrix a = pInput;
        tActivations.add(a);
        for (int tI = 0; tI < mWeightMatrexis.size(); tI++) {
            Matrix tW = mWeightMatrexis.get(tI);
            Matrix tB = mBiasis.get(tI);
            Matrix tZ = tW.times(a).plus(tB);
            tWeigtedInputs.add(tZ);
            a = sigmoid(tZ);
            tActivations.add(a);
        }
        
        //BackProp
        Matrix tZ = tWeigtedInputs.get(tL-1);
        Matrix tDelta = costfunctionDerivative(a, pDesiredResult).arrayTimes(sigmoidPrim(tZ));
        tNablaB.add(tDelta);
        tNablaW.add(tDelta.times(tActivations.get(tL-2).transpose()));
        for(int il = tL-1; il==1;il--){
            tZ = tWeigtedInputs.get(il-1);
            tDelta = mWeightMatrexis.get(il-1+1).transpose().times(tDelta).arrayTimes(sigmoidPrim(tZ));
            tNablaB.set(0, tDelta);
            tNablaW.set(0, tDelta.times(tActivations.get(il-1).transpose()));           
        }
        return tResultList;   
    }

    /**
     * Runs the sigmoid function on each element in the matrix.
     * @param pZ
     * @return
     */
    private Matrix sigmoid(Matrix pZ) {
        double[][] tArrayMatrix = pZ.getArray();
        double[][] tResult =new double[pZ.getRowDimension()][pZ.getColumnDimension()];
        for (int tI = 0; tI < tArrayMatrix.length; tI++) {
            double[] tRow = tArrayMatrix[tI];
            for (int tI2 = 0; tI2 < tRow.length; tI2++) {
                double tElement = tRow[tI2];
                tResult[tI][tI2] = 1/(1+Math.exp(-tElement));
            }
        }
        return new Matrix(tResult);
    }
    
    private Matrix sigmoidPrim(Matrix pZ) {
        Matrix ts = sigmoid(pZ);
        Matrix ones = new Matrix(ts.getRowDimension(),ts.getColumnDimension(),1);
        return ts.arrayTimes(ones.minus(ts));
    }
    
    private Matrix costfunctionDerivative(Matrix pOutput, Matrix pDesiredResult){
        return pOutput.minus(pDesiredResult);
    }




}
