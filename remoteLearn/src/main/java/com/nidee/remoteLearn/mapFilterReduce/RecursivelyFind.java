package com.nidee.remoteLearn.mapFilterReduce;

import org.springframework.lang.NonNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * find all the files in the project, by scanning recursively from the project’s root folder
 * restrict them to files with a particular suffix, in this case .java
 * open each file and read it in line-by-line
 * break each line into words
 *
 * https://web.mit.edu/6.005/www/fa15/classes/25-map-filter-reduce/
 */
public class RecursivelyFind {

    private final String path;
    private String fileSuffix;

    public RecursivelyFind(@NonNull String path) {
        this.path = path;
    }

    public RecursivelyFind(@NonNull String path, @NonNull String fileSuffix) {
        this.path = path;
        this.fileSuffix = fileSuffix;
    }

    /**
    find all the files in the project, by scanning recursively from the project’s root folder
     */
    private List<Path> findFilesAndFolders(String path) {
        File file = new File(path);
        List<Path> files = new ArrayList<>();
        if(file.isDirectory()) {
            File[] localFileList = file.listFiles();
            if (localFileList == null) {
                return files;
            }
            for(File f: localFileList) {
                if(f.isDirectory()) {
                    files.addAll(findFilesAndFolders(f.getPath()));
                } else if (fileSuffix != null) {
                    if(f.getName().endsWith("fileSuffix")) {
                        files.add(f.toPath());
                    }
                } else {
                    files.add(f.toPath());
                }
            }
        }
        return files;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }
}
