package org.azhar.utils;

import org.apache.commons.io.IOUtils;
import org.azhar.MultipartBody;
import org.azhar.dbmanager.DataResource;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;

@Singleton
public class FileManager {

    @Inject
    DataResource dataResource;

    public String storeFile(Long id, @MultipartForm MultipartBody data) throws IOException {

        byte[] bytes = IOUtils.toByteArray(data.file);

        String path = "src/main/resources/temperature/"+ data.fileName +".csv";

        File targetFile = new File("src/main/resources/temperature/"+ data.fileName +".csv");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(bytes);
        IOUtils.closeQuietly(outStream);

        return dataResource.uploadDir(id, path);

    }

//    public String grabFile(){
//
//    }
}