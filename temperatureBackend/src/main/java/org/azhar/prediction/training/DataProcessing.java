package org.azhar.prediction.training;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.collection.CollectionRecordReader;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.filter.FilterInvalidValues;
import org.datavec.api.transform.schema.Schema;
import org.datavec.api.writable.Writable;
import org.datavec.local.transforms.LocalTransformExecutor;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.joda.time.DateTimeZone;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.ViewIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.util.ArrayList;
import java.util.List;

public class DataProcessing {

    public static ViewIterator trainIter;
    public static ViewIterator testIter;

    public static void setData(RecordReader rr, int batchSize){

        List<List<Writable>> oldData = new ArrayList<>();
        while (rr.hasNext()) {
            List<Writable> data = rr.next();
            oldData.add(data);
        }
        System.out.println(oldData.size());
        List<List<Writable>> newData = LocalTransformExecutor.execute(oldData, DataProcessing.transformProcess());
        System.out.println(newData.size());

        CollectionRecordReader collectionRecordReader = new CollectionRecordReader(newData);
        DataSetIterator iterator = new RecordReaderDataSetIterator(collectionRecordReader, newData.size(), 1, 1, true);

        DataSet dataSet = iterator.next();
        SplitTestAndTrain testAndTrain = dataSet.splitTestAndTrain(0.7);

        DataSet train = testAndTrain.getTrain();
        DataSet test = testAndTrain.getTest();

        trainIter = new ViewIterator(train, batchSize);
        testIter = new ViewIterator(test, batchSize);
    }


    private static TransformProcess transformProcess(){

        Schema sc = new Schema.Builder()
                .addColumnsString("id")
                .addColumnInteger("row2")
                .addColumnTime("time", DateTimeZone.UTC)
                .addColumnsInteger("row4","row5","row6")
                .addColumnDouble("temperature")
                .build();
        System.out.println(sc);

        TransformProcess tp = new TransformProcess.Builder(sc)
                .removeColumns("id","row2","row4","row5","row6")
                .filter(new FilterInvalidValues())
                .build();

        System.out.println(tp.getFinalSchema());
        return tp;
    }


}
