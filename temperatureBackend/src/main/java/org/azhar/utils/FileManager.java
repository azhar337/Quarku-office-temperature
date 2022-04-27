package org.azhar.utils;

import org.apache.commons.io.IOUtils;
import org.azhar.MultipartBody;
import org.azhar.dbmanager.DataResource;
import org.azhar.prediction.PredictionResources;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

@Singleton
public class FileManager {

    @Inject
    DataResource dataResource;

    public String storeFile(@MultipartForm MultipartBody data) throws IOException {

        byte[] bytes = IOUtils.toByteArray(data.file);

        String path = "src/main/resources/temperature/"+ data.fileName +".csv";

        File targetFile = new File("src/main/resources/temperature/"+ data.fileName +".csv");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(bytes);
        IOUtils.closeQuietly(outStream);

        //train model for each file
        PredictionResources.training(path);

        return  path;

    }

    public File grabFile(String dir){
        File file= new File(dir);
        return file;
    }
}
