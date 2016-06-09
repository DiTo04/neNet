/**
 * Created by David Tennander, 9 juni 2016, all rights reserved.
 * Every one is allowed to use this source material for private use.
 * 
 */
package com.tennander.NeuralNetworking;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import Jama.Matrix;
import mnist.tools.MnistManager;

/**
 *
 * @author David Tennander, 9 juni 2016
 */
public class main {

    /**
     * 
     */
    private static final boolean SHOULD_PRINT_ERROR = true;
    /**
     * 
     */
    private static final int IMAGE_SIZE = 28;
    /**
     * 
     */
    private static final String DATA_SAVE_LOCATION = "MNISTData.david";
    private static final String TRAIN_LABELS_STRING = "lib/mnistData/t10k-labels.idx1-ubyte";
    private static final String TRAIN_IMAGES = "lib/mnistData/t10k-images.idx3-ubyte";
    private static final int BATCH_SIZE = 30;
    private static final double ETA = 3;

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Getting data from Disk...");

        HashMap<Matrix,Matrix> tData = getData();

        System.out.println("Creating Network...");
        int tRows = IMAGE_SIZE;
        int tColumns = IMAGE_SIZE;
        int tSize = tRows*tColumns;
        SigmoidNetwork tNetwork = new SigmoidNetwork(tSize,30,10);
        Iterator<Entry<Matrix, Matrix>> tDataIterator = tData.entrySet().iterator();
        for(int tIndex = 0; tIndex < tData.size();tIndex+=BATCH_SIZE) {
            System.out.format("Starting Batch: %d%n", tIndex/BATCH_SIZE);

            Set<Entry<Matrix, Matrix>> tBatch = new HashSet<>();
            for(int i = tIndex; i<tIndex+BATCH_SIZE;i++){
                if(tDataIterator.hasNext()){
                    tBatch.add(tDataIterator.next());
                } else {
                    break;
                }
            }
            tNetwork.updateGivenSetOfData(tBatch, ETA, SHOULD_PRINT_ERROR);

        }
        System.out.println("Trying to gess");
        MNISTDataHandler tManeger = new MNISTDataHandler(TRAIN_IMAGES, TRAIN_LABELS_STRING);
        int tMaxNr = tManeger.getImages().getCount();
        int tPicToGuess = 233;
        tManeger.setCurrent(tPicToGuess);
        int[][] tImage = tManeger.readImage();
        int tLabel = tManeger.readLabel();
        System.out.format("Should find a %d%n",tLabel);
        BufferedImage tPPMImage = ppm(IMAGE_SIZE, IMAGE_SIZE, 255, tImage);
        try{
            File outputfile = new File("GuessedNr.png");
            ImageIO.write(tPPMImage, "png", outputfile);
        } catch(IOException e){
            System.out.println("COuld not write picture... :(");
        }
        
        Matrix tInput = tManeger.convertToMatrix(tImage, 255);
        Matrix tOutput = tNetwork.feedForward(tInput);
        tOutput = tOutput.times(1/tOutput.norm1()).times(100);
        System.out.println("The system guesses is: ");
        for (double[] tRow : tOutput.getArrayCopy()) {
            System.out.format("[%s]%n",tRow[0]);
        }


    }

    /**
     * @param tDataHandler
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static HashMap<Matrix,Matrix> getData()  throws IOException, FileNotFoundException {
        HashMap<Matrix,Matrix> tData = new HashMap<>();
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_SAVE_LOCATION));
            tData = (HashMap<Matrix,Matrix>) ois.readObject(); // cast is needed.
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not find Data. Reparsing MNIST data. And saving it.");
            tData = readInMNISTData();
            System.out.println("Saving parsed data to file.");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_SAVE_LOCATION));
            oos.writeObject(tData);
            oos.close();
        }
        return tData;
    }

    /**
     * @param tDataHandler
     * @return
     * @throws IOException 
     */
    private static HashMap<Matrix,Matrix> readInMNISTData() throws IOException {
        System.out.println("Reading in Data...");
        MNISTDataHandler tDataHandler = new MNISTDataHandler(TRAIN_IMAGES,TRAIN_LABELS_STRING);
        List<Matrix> tImageList = tDataHandler.getListOfImages(); //Called to make it save it as list internally.
        List<Integer> tLabelList = tDataHandler.getListOfLabels(); //Called to make it save it as list internally.
        System.out.println("Sorting Data");
        HashMap<Matrix,Matrix> tData = new HashMap<>();
        for (int tIndex2 = 0;tIndex2 < tImageList.size(); tIndex2++) {
            Matrix tMatrix = tImageList.get(tIndex2);
            Integer tInteger = tLabelList.get(tIndex2);
            tData.put(tMatrix, getOutputVectorFromInteger(tInteger));

        }

        return tData;
    }

    private static Matrix getOutputVectorFromInteger(Integer pKey) {
        Matrix tVector = new Matrix(10, 1);
        tVector.set(pKey, 0, 1);
        return tVector;
    }

    static public BufferedImage ppm(int width, int height, int maxcolval, int[][] image){
        BufferedImage theImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y<height; y++){
            for(int x = 0; x<width; x++){
                int value = image[y][x] << 16 | image[y][x] << 8 | image[y][x];
                value = 255-value;
                theImage.setRGB(x, y, value);
            }
        }
        return theImage;
    }
}
