package com.oop_final.dao.mysql;

import com.oop_final.bo.Directory;
import com.oop_final.dao.DirectoryDAO;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian.Flak on 6/2/2017.
 */
public class DirectoryDAOImpl extends MySQL implements DirectoryDAO {

    //region Parameters
    int id = 0;//if returns zero not successful
    Directory directory = null; //null so if no records returned no memory used
    List<Directory> directoryList = new ArrayList<Directory>();
    static String sp = CallProcedures.callDirectory;
    //endregion

    //region HydrationObject and CallableStatement Methods

    private static Directory HydrateObject(ResultSet rs) throws SQLException {

        Directory directory = new Directory();
        directory.setIdDirectory(rs.getInt(1));
        directory.setDirName(rs.getString(2));
        directory.setDirSize(rs.getInt(3));
        directory.setNumberOfFiles(rs.getInt(4));
        directory.setPath(rs.getString(5));
        return directory;
    }


    private static CallableStatement callableStatement(int queryId, int idDirectory, String dirName,
                                                       int dirSize, int numberOfFiles, String path)throws SQLException{
        CallableStatement cStmt = connection.prepareCall(sp);
        cStmt.setInt(1,queryId);
        cStmt.setInt(2, idDirectory);
        cStmt.setString(3, dirName);
        cStmt.setInt(4, dirSize);
        cStmt.setInt(5, numberOfFiles);
        cStmt.setString(6, path);
        return cStmt;
    }

    //endregion

    //region DAO CRUD

    @Override
    public Directory getDirectoryById(int idDirectory) {
        Connect();
        try {

            ResultSet rs = callableStatement(20,idDirectory,"",0,0,"").executeQuery();

            if(rs.next()) {
                directory = HydrateObject(rs);
            }


        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return directory;
    }

    @Override
    public List<Directory> getDirectoryAll() {
        Connect();
        try {

            ResultSet rs = callableStatement(10,0,"",0,0,"").executeQuery();

            if(rs.next()) {
                directoryList.add(HydrateObject(rs));
            }


        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return directoryList;
    }

    @Override
    public int insertDirectory(Directory directory) {
        Connect();
        try{

            ResultSet rs = callableStatement(30,directory.getIdDirectory(), directory.getDirName(),
                    directory.getDirSize(), directory.getNumberOfFiles(), directory.getPath()).executeQuery();

            if (rs.next()){
                id = rs.getInt(1);
            }


        }catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return id;

    }

    @Override
    public boolean updateDirectory(Directory directory) {
        Connect();
        try{

            ResultSet rs = callableStatement(40,directory.getIdDirectory(), directory.getDirName(),
                    directory.getDirSize(), directory.getNumberOfFiles(), directory.getPath()).executeQuery();

            if (rs.next()){
                id = rs.getInt(1);
            }


        }catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return id > 0;
    }

    @Override
    public boolean deleteDirectory(int idDirectory) {
        Connect();
        try{

            ResultSet rs = callableStatement(50,idDirectory,"",0,0,"").executeQuery();

            if (rs.next()){
                id = rs.getInt(1);
            }


        }catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return id > 0;
    }

    //endregion

}
