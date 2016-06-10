/**
 * Created by David Tennander, 10 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Jama.Matrix;

/**
 *A helper class for {@link SigmoidNetwork} to help it to save and load setup data to disc.
 * @author David Tennander, 10 juni 2016
 */
public class SigmoidNetworkFileParser {

    private static final Object WEIGHTS_KEY = "THEWEIGHTS";
    private static final Object BIASIS_KEY = "THEBIASIS";

    /**
     * Returns a List of structure [Weights, Biasis].
     * @return
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    @SuppressWarnings("unchecked")
    public static List<List<Matrix>> getWeightAndBiasisFromFile(File pFile) throws ClassNotFoundException, IOException {
        InputStream tInputStream = new FileInputStream(pFile);
        ObjectInputStream tObjStream = new ObjectInputStream(tInputStream);
        Object tData =  tObjStream.readObject();
        tObjStream.close();
        if(!(tData instanceof Map<?,?>)     || !((Map<?, ?>) tData).containsKey(WEIGHTS_KEY)
                                            || !((Map<?, ?>) tData).containsKey(BIASIS_KEY)) {
            throw new ClassNotFoundException("Could not find MapObject");
        } 
        Map<?,?> tMap = (Map<?,?>) tData;
        Object tWeightObj = tMap.get(WEIGHTS_KEY);
        Object tBiasisObj = tMap.get(BIASIS_KEY);
        
        List<Matrix> tWeights = new ArrayList<>();
        if(tWeightObj instanceof List<?> && ((List<?>) tWeightObj).get(0) instanceof Matrix) {
            tWeights = (List<Matrix>) tWeightObj;            
        }
        List<Matrix> tBiasis = new ArrayList<>();
        if(tBiasisObj instanceof List<?> && ((List<?>) tBiasisObj).get(0) instanceof Matrix) {
            tBiasis = (List<Matrix>) tBiasisObj;            
        }
        
        List<List<Matrix>> tResult =  new LinkedList<List<Matrix>>();
        tResult.add(tWeights);
        tResult.add(tBiasis);        
        return tResult;
    }

    /**
     * Saves the given weights and biasis to file.
     * @param pWeightMatrexis
     * @param pBiasis
     * @param pFile
     * @throws IOException 
     */
    public static void saveWeightAndBiasis(List<Matrix> pWeightMatrexis, List<Matrix> pBiasis, File pFile) throws IOException {
        OutputStream tOutputStream = new FileOutputStream(pFile);
        ObjectOutputStream tObjStream = new ObjectOutputStream(tOutputStream);
        HashMap<Object, Object> tMap = new HashMap<>();
        tMap.put(WEIGHTS_KEY, pWeightMatrexis);
        tMap.put(BIASIS_KEY, pBiasis);
        tObjStream.writeObject(tMap);
        tObjStream.close();
    }

}
