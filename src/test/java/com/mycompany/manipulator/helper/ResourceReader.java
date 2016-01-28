package com.mycompany.manipulator.helper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.maven.shared.utils.io.FileUtils;

/**
 *
 * @author saj
 */
public class ResourceReader {

    private String fileName;

    public ResourceReader() {
    }

    public ResourceReader read(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public File asFile() {
        URI uri = null;
        try {
            uri = getClass().getClassLoader().getResource(fileName).toURI();
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

        return new File(uri);
    }

    public String asString() {
        String fileAsString;
        try {
            fileAsString = FileUtils.fileRead(asFile());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return fileAsString;
    }
}
