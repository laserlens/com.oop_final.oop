package com.oop_final.dao.mysql;

import com.oop_final.bo.Directory;
import com.oop_final.bo.File;
import com.oop_final.dao.MenuChoiceDAO;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian.Flak on 6/5/2017.
 */
public class MenuChoiceDAOImpl extends MySQL implements MenuChoiceDAO {

    //region Parameters
    int booleanId = 0;//if returns zero not successful
    List<File> fileList = new ArrayList<File>();
    List<Directory> directoryList = new ArrayList<Directory>();
    static String sp = CallProcedures.callMenuChoice;
    //endregion

    //region HydrationObject and CallableStatement Methods

    private static File fileHydrateObject(ResultSet rs) throws SQLException {

        File file = new File();
        file.setIdFile(rs.getInt(1));
        file.setFileName(rs.getString(2));
        file.setFileType(rs.getString(3));
        file.setFileSize(rs.getInt(4));
        file.setPath(rs.getString(5));
        return file;
    }

    private static Directory dirHydrateObject(ResultSet rs) throws SQLException {

        Directory directory = new Directory();
        directory.setIdDirectory(rs.getInt(1));
        directory.setDirName(rs.getString(2));
        directory.setDirSize(rs.getInt(3));
        directory.setNumberOfFiles(rs.getInt(4));
        directory.setPath(rs.getString(5));
        return directory;
    }

    private static CallableStatement callableStatement(int queryId, String fileType)throws SQLException{
        CallableStatement cStmt = connection.prepareCall(sp);
        cStmt.setInt(1,queryId);
        cStmt.setString(2,fileType);
        return cStmt;
    }

    //endregion

    //region DAOs

    @Override
    public List<File> getFileChoice() {
        Connect();
        try {

            ResultSet rs = callableStatement(3,"").executeQuery();

            while (rs.next()) {

                fileList.add(fileHydrateObject(rs));
            }


        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return fileList;
    }

    @Override
    public List<Directory> getDirectoryChoice(int queryId) {
        Connect();
        try {

            ResultSet rs = callableStatement(queryId,"").executeQuery();

            while (rs.next()) {

                directoryList.add(dirHydrateObject(rs));
            }


        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return directoryList;
    }

    @Override
    public boolean clearDataBase() {
            Connect();
            try{

                ResultSet rs = callableStatement(5,"").executeQuery();

                if (rs.next()){
                    booleanId = rs.getInt(1);
                }


            }catch (SQLException sqlEx) {
                logger.error(sqlEx);
            }

            return booleanId > 0;
    }

    @Override
    public List<File> getFileByType(String fileType) {
        Connect();
        try {

            ResultSet rs = callableStatement(4,fileType).executeQuery();

            while (rs.next()) {

                fileList.add(fileHydrateObject(rs));
            }


        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return fileList;
    }

    //endregion

}
