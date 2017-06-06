package com.oop_final.dao.mysql;

/**
 * Created by Adrian.Flak on 6/1/2017.
 */
final class CallProcedures {

    //region Call File

    public static final String callFile = "{call usp_File(?,?,?,?,?,?,?)}";

    //endregion

    //region Call Directory

    public static final String callDirectory = "{call usp_Directory(?,?,?,?,?,?)}";
    
    //endregion

    //region Menu choice

    public static final String callMenuChoice = "{call usp_MenuChoice(?,?)}";

    //endregion

}
