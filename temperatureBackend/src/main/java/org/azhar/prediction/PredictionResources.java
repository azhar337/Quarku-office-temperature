package org.azhar.prediction;

import org.azhar.prediction.training.MainPrediction;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class PredictionResources {

    private static final Logger log = LoggerFactory.getLogger(PredictionResources.class);
    private static final Logger trainingLog = LoggerFactory.getLogger(MainPrediction.class);

    public static String makePrediction(String file, String date) throws IOException {

        String[] fileName = file.split("/");
        String[] cleanName = fileName[4].split("\\.");


        File modelSave =  new File("src/main/resources/model/"+cleanName[0]);
        System.out.println(modelSave);
        if(!modelSave.exists())
        {
            return "Model does not exist, re Upload file";
        }

        MultiLayerNetwork model1 = ModelSerializer.restoreMultiLayerNetwork(modelSave);

        double[][] matrixDouble = new double[][]{
                {Double.parseDouble(date)}};
        INDArray rowVector = Nd4j.create(matrixDouble);
        INDArray prediction =  model1.output(rowVector);
        double nextVal = prediction.getDouble(0);

        return String.valueOf(nextVal);
    }

    public static String training(String file) {


        int batchSize = 100;

        MainPrediction prediction = new MainPrediction(file,batchSize);
        Thread training = new Thread(prediction);

        if(!training.isAlive()){
            training.start();
            log.info(String.valueOf(training.getId()));
            trainingLog.info(String.valueOf(training.getId()));
        }
        return "Training Start";
    }

}
