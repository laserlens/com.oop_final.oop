package com.oop_final.bo;

import java.io.Serializable;

/**
 * Created by Adrian.Flak on 6/1/2017.
 */
public class File extends BaseBo implements Serializable{

    //region Properties
    private int idFile;
    private String fileName;
    private String fileType;
    private int fileSize;
    private String path;
    private int idDirectory;
    //endregion

    //region Constructors

    public File(){

    }

    public File(String fileName, String fileType, int fileSize, String path, int idDirectory){
        this.setFileName(fileName);
        this.setFileType(fileType);
        this.setFileSize(fileSize);
        this.setPath(path);
        this.setIdDirectory(idDirectory);
    }

    //endregion

    //region Getter/Setters

    public int getIdFile() {
        return idFile;
    }

    public void setIdFile(int idFile) {
        this.idFile = idFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getIdDirectory() {
        return idDirectory;
    }

    public void setIdDirectory(int idDirectory) {
        this.idDirectory = idDirectory;
    }



    //endregion

    //region Common Methods
    public String GetLongFileName (){
        return this.fileName + " " + this.fileSize + " bytes";
    }

    public String ToString(){
        return "File Id=" + this.idFile + ", File Full Name=" + GetLongFileName() + ", Path=" + this.path;
    }
    //endregion

}
