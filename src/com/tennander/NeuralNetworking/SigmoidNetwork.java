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
            Matrix ones = new Matrix(tThisLvlSize, tLvlSizeOneLvlBefore, 1);
            mWeightMatrexis.add(Matrix.random(tThisLvlSize, tLvlSizeOneLvlBefore).times(2).minus(ones));
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

    public void updateGivenSetOfData(Set<Entry<Matrix, Matrix>> pDataSeries, double pEta, boolean pShouldPrintError) {
        List<Matrix> wNablaSumList = null;
        List<Matrix> bNablaSumList = null;
        for (Entry<Matrix, Matrix> tEntry : pDataSeries) {
            List<List<Matrix>> nablas = backprop(tEntry.getKey(),tEntry.getValue());
            if(wNablaSumList == null) {
                wNablaSumList = nablas.get(0);
            } else {
                wNablaSumList = sumListOfMatrixis(wNablaSumList, nablas.get(0));
            }
            if(bNablaSumList == null){
                bNablaSumList = nablas.get(1);
            } else {
                bNablaSumList = sumListOfMatrixis(bNablaSumList, nablas.get(1));
            }
        }
        for(int i = 0; i< mBiasis.size(); i++){
            Matrix w = mWeightMatrexis.get(i);
            Matrix b = mBiasis.get(i);
            Matrix wNablaSum = wNablaSumList.get(i);
            Matrix bNablaSum = bNablaSumList.get(i);
            mWeightMatrexis.remove(i);
            mBiasis.remove(i);
            mWeightMatrexis.add(i,w.minus(wNablaSum.times(pEta/pDataSeries.size())));
            mBiasis.add(i,b.minus(bNablaSum.times(pEta/pDataSeries.size())));
        }
        if(pShouldPrintError){
            System.out.format("The delta has the norm: %s%n", wNablaSumList.get(0).normF());
        }
    }

    /**
     * @param pShouldPrintError 
     * @param pKey
     * @return
     */


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
        tNablaW.add(tDelta.times(tActivations.get(tL-1).transpose()));
        for(int il = tL-1; il==1;il--){
            tZ = tWeigtedInputs.get(il-1);
            tDelta = mWeightMatrexis.get(il-1+1).transpose().times(tDelta).arrayTimes(sigmoidPrim(tZ));
            tNablaB.add(0, tDelta);
            tNablaW.add(0, tDelta.times(tActivations.get(il-1).transpose()));           
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



    private List<Matrix> sumListOfMatrixis(List<Matrix> a,List<Matrix> b) {
        List<Matrix> tResult = new ArrayList<>();
        for (int tI = 0; tI < a.size(); tI++) {
            Matrix a_matrix = a.get(tI);
            Matrix b_matrix = b.get(tI);            
            tResult.add(a_matrix.plus(b_matrix));
        }
        return tResult;
    }

    /**
     * @return the weightMatrexis
     */
    public List<Matrix> getWeightMatrexis() {
        ArrayList<Matrix> tResult = new ArrayList<>();
        for (Matrix tMatrix : mWeightMatrexis) {
            tResult.add(tMatrix.copy());
        }
        return tResult;
    }

    /**
     * @param pWeightMatrexis the weightMatrexis to set
     */
    public void setWeightMatrexis(List<Matrix> pWeightMatrexis) {
        ArrayList<Matrix> tNewList = new ArrayList<>();
        for (Matrix tMatrix : pWeightMatrexis) {
            tNewList.add(tMatrix.copy());
        }
        mWeightMatrexis = tNewList;
    }

    /**
     * @return the biasis
     */
    public List<Matrix> getBiasis() {
        ArrayList<Matrix> tResult = new ArrayList<>();
        for (Matrix tMatrix : mBiasis) {
            tResult.add(tMatrix.copy());
        }
        return tResult;
    }

    /**
     * @param pBiasis the biasis to set
     */
    public void setBiasis(List<Matrix> pBiasis) {
        ArrayList<Matrix> tNewList = new ArrayList<>();
        for (Matrix tMatrix : pBiasis) {
            tNewList.add(tMatrix.copy());
        }
        mWeightMatrexis = tNewList;
    }

}
