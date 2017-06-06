package com.oop_final.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian.Flak on 6/1/2017.
 */
public class Directory extends BaseBo implements Serializable {

    //region Properties
    private int idDirectory;
    private String dirName;
    private int dirSize;
    private int numberOfFiles;
    private String path;
    private List<File> files;
    //endregion

    //region Constructors

    public Directory(){}

    public Directory(String dirName, int dirSize, int numberOfFiles, String path) {
        this.setFiles(new ArrayList<>());
        this.setDirName(dirName);
        this.setDirSize(dirSize);
        this.setNumberOfFiles(numberOfFiles);
        this.setPath(path);
    }

    //endregion

    //region Getters/Setters
    public int getIdDirectory() {
        return idDirectory;
    }

    public void setIdDirectory(int idDirectory) {
        this.idDirectory = idDirectory;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public int getDirSize() {
        return dirSize;
    }

    public void setDirSize(int dirSize) {
        this.dirSize = dirSize;
    }

    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    public void setNumberOfFiles(int numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    //endregion

    //region  Common Methods
    public String LongDirectoryName(){
        return this.dirName + " " + this.dirSize + " bytes";
    }

    public String ToString(){
        return "Directory Id=" + this.idDirectory + ", Name=" + LongDirectoryName() + ", Number Of Files=" + this.numberOfFiles + ", Directory Path=" + this.path;
    }
    //endregion


}
