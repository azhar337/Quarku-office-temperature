package org.azhar.prediction.training;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class RegressionModel {

    private static final double learningRate = 0.01;
    private static final int input = 1;
    private static final int output = 1;

    public static MultiLayerNetwork simpleModel(){

        return new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                .seed(123)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam(learningRate))
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(input)
                        .nOut(100)
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .nIn(input)
                        .nOut(100)
                        .activation(Activation.RELU)
                        .build())
                .layer(2, new DenseLayer.Builder()
                        .nOut(100)
                        .activation(Activation.RELU)
                        .build())
                .layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .nOut(output)
                        .activation(Activation.RELU)
                        .build())
                .build());
    }

}
