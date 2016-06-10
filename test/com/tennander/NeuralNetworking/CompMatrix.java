/**
 * Created by David Tennander, 10 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import java.util.Arrays;

import Jama.Matrix;

/**
 *A extension of {@link Matrix} that is comparable.
 * @author David Tennander, 10 juni 2016
 */
@SuppressWarnings("serial")
public class CompMatrix extends Matrix {

    /**
     * @See {@link #constructWithCopy(double[][])}
     * @param A
     */
    public CompMatrix(double[][] A) {
        super(A);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object pObj) {
        if(!(pObj instanceof Matrix) ){
            return false;
        } else {
            Matrix tMat = (Matrix) pObj;
            double[][] tInnerArray = tMat.getArray();
            return Arrays.deepEquals(tInnerArray, this.getArray());
        }
    }

}
