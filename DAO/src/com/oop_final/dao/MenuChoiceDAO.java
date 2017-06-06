package com.oop_final.dao;

import com.oop_final.bo.Directory;
import com.oop_final.bo.File;

import java.util.List;

/**
 * Created by Adrian.Flak on 6/5/2017.
 */
public interface MenuChoiceDAO {

    //region File and Directory choice

    List<File> getFileChoice();
    List<Directory> getDirectoryChoice(int queryId);
    List<File> getFileByType(String fileType);

    //endregion

    //region Clear Database

    boolean clearDataBase();

    //endregion

}
