package org.azhar.prediction.training;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.core.storage.StatsStorage;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.model.stats.StatsListener;
import org.deeplearning4j.ui.model.storage.InMemoryStatsStorage;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.evaluation.regression.RegressionEvaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.ViewIterator;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;


public class MainPrediction extends Thread {

    private static final int nEpochs = 1;

    public static RecordReader getData (String filePath) throws IOException, InterruptedException {

        File file = new File(filePath);
        RecordReader rr = new CSVRecordReader(0);
        rr.initialize(new FileSplit(file));

        return rr;
    }


//    public static void main(String[] args) throws IOException, InterruptedException {
//
//        MainPrediction thread = new MainPrediction();
//        thread.start();
//
//        File modelSave =  new File("src/main/resources/test");
//        MultiLayerNetwork model1 = ModelSerializer.restoreMultiLayerNetwork(modelSave);
//
//        double[][] matrixDouble = new double[][]{
//                {1635728264}};
//        INDArray rowVector = Nd4j.create(matrixDouble);
//        System.out.println(model1.output(rowVector));
//
//    }

    public void run() {
        try {
            DataProcessing.setData(getData("src/main/resources/temperature.csv"),100);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        ViewIterator trainIter = DataProcessing.trainIter;
        ViewIterator testIter = DataProcessing.testIter;

        MultiLayerNetwork model = RegressionModel.simpleModel();

        UIServer uiServer = UIServer.getInstance();
        StatsStorage statsStorage = new InMemoryStatsStorage();
        uiServer.attach(statsStorage);
        model.setListeners(new ScoreIterationListener(10), new StatsListener(statsStorage));

        model.fit(trainIter,nEpochs);

        RegressionEvaluation regEval= model.evaluateRegression(testIter);
        System.out.println(regEval.stats());


        File locationToSave = new File("src/main/resources/test");
        System.out.println(locationToSave.toString());
        boolean saveUpdater = false;

        // ModelSerializer needs modelname, saveUpdater, Location
        try {
            ModelSerializer.writeModel(model,locationToSave,saveUpdater);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("saved");
    }
}
