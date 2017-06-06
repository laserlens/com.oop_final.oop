package com.oop_final.dao;

import com.oop_final.bo.File;

import java.util.List;

/**
 * Created by Adrian.Flak on 6/1/2017.
 */
public interface FileDAO {

    //region select methods
    File getFileById(int idFile);
    List<File> getFileAll();
    //endregion

    //region insert,update,delete methods
    int insertFile(File file);
    boolean updateFile(File file);
    boolean deleteFile(int idFile);
    //endregion


}
