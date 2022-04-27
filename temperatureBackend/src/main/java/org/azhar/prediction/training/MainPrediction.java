package org.azhar.prediction.training;

import org.azhar.prediction.PredictionResources;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;

public class MainPrediction implements Runnable {

    private static final int nEpochs = 1;
    private static String filePath;
    private static int batchSize;
    private static final Logger log = LoggerFactory.getLogger(PredictionResources.class);
    private static final Logger trainingLog = LoggerFactory.getLogger(MainPrediction.class);


    public MainPrediction(String filePath, int _batchSize) {
        MainPrediction.filePath = filePath;
        MainPrediction.batchSize = _batchSize;
    }

    private static RecordReader getData () throws IOException, InterruptedException {

        File file = new File(filePath);
        RecordReader rr = new CSVRecordReader(0);
        rr.initialize(new FileSplit(file));

        return rr;
    }


    public void run() {

        try {
            DataProcessing.setData(getData(),batchSize);
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
        trainingLog.info(regEval.stats());

        String[] fileName = filePath.split("/");
        String[] cleanName = fileName[3].split("\\.");

        File locationToSave = new File("src/main/resources/model/"+cleanName[0]);
        boolean saveUpdater = false;

        try {
            ModelSerializer.writeModel(model,locationToSave,saveUpdater);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info(cleanName[0]+"saved");
    }
}
