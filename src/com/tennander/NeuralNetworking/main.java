/**
 * Created by David Tennander, 9 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import Jama.Matrix;
import fj.data.Array;
import fj.data.Zipper;

/**
 *
 * @author David Tennander, 9 juni 2016
 */
public class main {
 
    private static final String TRAIN_LABELS_STRING = "lib/mnistData/train-labels.idx1-ubyte";
    private static final String TRAIN_IMAGES = "lib/mnistData/train-images.idx3-ubyte";
    private static final int BATCH_SIZE = 30;

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Starting Learning phase...");
        
        List<Matrix> tImages = null;
        List<Integer> tLabels =null;
        SigmoidNetwork tNetwork = new SigmoidNetwork(tSize,300,10);
        try{
            MNISTDataHandler tDataHandler = new MNISTDataHandler(TRAIN_IMAGES,TRAIN_LABELS_STRING);
            tImages = tDataHandler.getListOfImages();
            tLabels = tDataHandler.getListOfLabels();
        } catch (IOException e) {
            System.out.println("Error reading mnistData... Exiting");
            return;
        }
        
        int tNrOfBatches = tImages.size()/BATCH_SIZE;
        IntStream.range(0, tNrOfBatches).map(i -> i*BATCH_SIZE)
                                        .mapToObj(i -> tImages.subList(i, i+BATCH_SIZE-1))
                                        .map(tNetwork.updateGivenSetOfData(pDataSeries, pEta);)
        
    }

}
