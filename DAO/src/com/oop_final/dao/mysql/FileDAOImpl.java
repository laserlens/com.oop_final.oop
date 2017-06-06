package com.oop_final.dao.mysql;

import com.oop_final.bo.File;
import com.oop_final.dao.FileDAO;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian.Flak on 6/1/2017.
 */
public class FileDAOImpl extends MySQL implements FileDAO{

    //region Parameters
    int id = 0;//if returns zero not successful
    File file = null; //null so if no records returned no memory used
    List<File> fileList = new ArrayList<File>();
    static String sp = CallProcedures.callFile;
    //endregion

    //region HydrationObject and CallableStatement Methods

    private static File HydrateObject(ResultSet rs) throws SQLException {

        File file = new File();
        file.setIdFile(rs.getInt(1));
        file.setFileName(rs.getString(2));
        file.setFileType(rs.getString(3));
        file.setFileSize(rs.getInt(4));
        file.setPath(rs.getString(5));
        file.setIdDirectory(6);
        return file;
    }


    private static CallableStatement callableStatement(int queryId, int idFile, String fileName,
                                                       String fileType, int fileSize, String path, int idDirectory)throws SQLException{
        CallableStatement cStmt = connection.prepareCall(sp);
            cStmt.setInt(1,queryId);
            cStmt.setInt(2, idFile);
            cStmt.setString(3, fileName);
            cStmt.setString(4, fileType);
            cStmt.setInt(5, fileSize);
            cStmt.setString(6, path);
            cStmt.setInt(7, idDirectory);
        return cStmt;
    }

    //endregion

    //region DAO CRUD

    @Override
    public File getFileById(int idFile) {
        Connect();
        try {

            ResultSet rs = callableStatement(20,idFile,"","",0,"",0).executeQuery();

            if(rs.next()) {
                file = HydrateObject(rs);
            }


        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return file;
    }

    @Override
    public List<File> getFileAll() {
        Connect();
        try {

            ResultSet rs = callableStatement(10,0,"","",0,"",0).executeQuery();

            while (rs.next()) {

                fileList.add(HydrateObject(rs));
            }


        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return fileList;
    }

    @Override
    public int insertFile(File file) {
        Connect();
        try{

            ResultSet rs = callableStatement(30,file.getIdFile(),file.getFileName(),
                    file.getFileType(),file.getFileSize(),file.getPath(),file.getIdDirectory()).executeQuery();

            if (rs.next()){
                id = rs.getInt(1);
            }


        }catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return id;

    }

    @Override
    public boolean updateFile(File file) {
        Connect();
        try{

            ResultSet rs = callableStatement(40,file.getIdFile(),file.getFileName(),
                    file.getFileType(),file.getFileSize(),file.getPath(),file.getIdDirectory()).executeQuery();
            if (rs.next()){
                id = rs.getInt(1);
            }


        }catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return id > 0;
    }

    @Override
    public boolean deleteFile(int idFile) {
        Connect();
        try{

            ResultSet rs = callableStatement(50,idFile,"","",0,"",0).executeQuery();

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
