package com.spechpro.biometric.onepass.client.util;

import java.io.File;
import java.io.IOException;

/**
 * Created by sadurtinova on 20.04.2017.
 */
public class TestHelper {

    public byte[] readBytes (String fileName){
        File testFile = searchFileInResource(fileName);
        try {
            return org.apache.commons.io.FileUtils.readFileToByteArray(testFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File searchFileInResource(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file;
    }
}
