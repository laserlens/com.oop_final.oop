package com.oop_final.dao;

import com.oop_final.bo.Directory;

import java.util.List;

/**
 * Created by Adrian.Flak on 6/1/2017.
 */
public interface DirectoryDAO {

    //region select methods
    Directory getDirectoryById(int idDirectory);
    List<Directory> getDirectoryAll();
    //endregion

    //region insert,update,delete methods
    int insertDirectory(Directory directory);
    boolean updateDirectory(Directory directory);
    boolean deleteDirectory(int idDirectory);
    //endregion

}
