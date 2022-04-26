package org.azhar.prediction;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

public class PredictionResources {


    public static String makePrediction(String file, String date) throws IOException {

        String[] arr = file.split("/");
        String[] idArr = arr[3].split("\\.");

        File modelSave =  new File("src/main/resources/model/"+idArr[0]+".zip");

        if(!modelSave.exists())
        {
            System.out.println("Model not exist. Abort");
            return "false";
        }
//        File modelSave1 =  new File("src/main/resources/test.zip");
//        MultiLayerNetwork model1 = ModelSerializer.restoreMultiLayerNetwork(modelSave);
//
//        double[][] matrixDouble = new double[][]{
//                {Double.parseDouble(date)}};
//        INDArray rowVector = Nd4j.create(matrixDouble);
//        System.out.println(model1.output(rowVector));
//
        return idArr[0];
    }

    private static Boolean training(String file){

        return true;
    }
}
