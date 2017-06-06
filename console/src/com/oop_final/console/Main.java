package com.oop_final.console;


import com.oop_final.bo.Directory;
import com.oop_final.bo.File;
import com.oop_final.dao.DirectoryDAO;
import com.oop_final.dao.FileDAO;
import com.oop_final.dao.MenuChoiceDAO;
import com.oop_final.dao.mysql.DirectoryDAOImpl;
import com.oop_final.dao.mysql.FileDAOImpl;
import com.oop_final.dao.mysql.MenuChoiceDAOImpl;
import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;




public class Main {

    final static Logger logger = Logger.getLogger(Main.class);
    static DirectoryDAO directoryDAO = new DirectoryDAOImpl();

    public static void main(String[] args) {
        Main myMain = new Main();
        UserPrompt();

    }

    private static void UserPrompt(){

        //region PROMPT USER
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please Type Path you would like to Add Directories and Files to the Data Base: ");
        String path = scanner.nextLine();
        InsertInitialDirectory(new java.io.File(path));

        //endregion

        SelectAndClearByMenuChoice();


    }

    private static void InsertInitialDirectory(java.io.File initialDir) {


        if(initialDir.isDirectory()) {

            Directory directory = new Directory();

            //region New Instance of Directory created
            directory.setDirName(initialDir.getName());

            try {
                //use apache commons FileUtils.sizeOfDirectory to get size of directory in bytes
                directory.setDirSize((int) FileUtils.sizeOfDirectory(new java.io.File(initialDir.getCanonicalPath())));

                //count number of files in the list
                directory.setNumberOfFiles((int) Files.list(Paths.get(initialDir.getCanonicalPath())).count());

                directory.setPath(initialDir.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //endregion

            //region insert New Instance of Directory to Data Base

            int dirId = directoryDAO.insertDirectory(directory);
            RecursiveFileFinderAndInserter(initialDir, dirId);
            //endregion
        } else {
            System.out.println("not a directory, please enter a dir");
        }



    }

    private static void RecursiveFileFinderAndInserter(java.io.File dir, int dirId){

        try{
            java.io.File[] files = dir.listFiles();
            for(java.io.File file: files){
                if(file.isDirectory()){

                    //region New Instance of Directory created

                    Directory directory = new Directory();
                    directory.setDirName(file.getName());

                    //use apache commons FileUtils.sizeOfDirectory to get size of directory in bytes
                    directory.setDirSize((int)FileUtils.sizeOfDirectory(new java.io.File(file.getCanonicalPath())));

                    //count number of files in the list
                    directory.setNumberOfFiles((int) Files.list(Paths.get(file.getCanonicalPath())).count());

                    directory.setPath(file.getCanonicalPath());

                    //endregion

                    //region insert New Instance of Directory to Data Base

                    int newDirId = directoryDAO.insertDirectory(directory);

                    //endregion



                    RecursiveFileFinderAndInserter(file, newDirId);//repeat as needed

                } else {


                    //region New Instance of File Created
                    File fileObj = new File();
                    fileObj.setFileName(file.getName());

                    //uses probeContentType to get type
                    Path path = Paths.get(file.getPath());

                    if (Files.probeContentType(path)== null){
                        // if null assume file extension best version of file type
                        fileObj.setFileType(GetFileExtension(file.getName()));
                    }else {
                        fileObj.setFileType(Files.probeContentType(path)); // if not null set using probeContentType
                    }


                    //get size in bytes
                    fileObj.setFileSize((int)new java.io.File(file.getCanonicalPath()).length());

                    fileObj.setPath(file.getCanonicalPath());
                    fileObj.setIdDirectory(dirId);

                    //endregion

                    //region insert New instance of File to Data Base

                    FileDAO fileDAO = new FileDAOImpl();
                    fileDAO.insertFile(fileObj);


                    //endregion

                }
            }
        } catch (IOException ioEx){
            logger.error(ioEx);
        }
    }

    private static String GetFileExtension(String fileName) {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    private static int MenuPrompt(){

        //region Menu choice prompt

        Scanner scanner = new Scanner(System.in);
        System.out.println("*************************************************");
        System.out.println("*     Enter the number from the list below      *");
        System.out.println("*************************************************");
        System.out.println("_________________________________________________");
        System.out.println("*************************************************");
        System.out.println("* 1) Display directory with most files          *");
        System.out.println("* 2) Display directory largest in size          *");
        System.out.println("* 3) Display 5 largest files in size            *");
        System.out.println("* 4) Display all files of a certain type        *");
        System.out.println("* 5) Clear the db and start over                *");
        System.out.println("* 6) Exit                                       *");
        System.out.println("**************ENTER NUMBER BELOW*****************");
        int checkIfInt = 0; //initialize variable
        try {
            checkIfInt = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("************************************************************");
            System.out.println("* The value you have input is not a valid choice TRY AGAIN *");
            System.out.println("************************************************************");
            SelectAndClearByMenuChoice();
        }
        return checkIfInt;

        //endregion

    }

    private static void SelectAndClearByMenuChoice(){

        //region Parameters
        MenuChoiceDAO menuChoiceDAO = new MenuChoiceDAOImpl();
        int queryId = MenuPrompt();//runs MenuPrompt applies user choice to queryId variable
        List<File> fileList;
        List<Directory> dirList;
        //endregion

        //region Run Users Query Choice

        if (queryId == 5) {

            //region Clear DataBase

            CountDownToClear(menuChoiceDAO);

            //endregion

        } else if (queryId == 4 ){

            //region Prompt user to pick a File Type

            String fileType = UserChoiceFileType();

            //endregion

            //region Display File Types chosen by user
           fileList = menuChoiceDAO.getFileByType(fileType);
            System.out.println("----------------------------------");
            System.out.println("-  Files Type of " + fileType);
            System.out.println("----------------------------------");
            FileScreenPrinter(fileList);
            System.out.println("-----------------------------------");
            //endregion wi

        } else if (queryId == 3){

            //region Display top 5 greatest files by size
            fileList = menuChoiceDAO.getFileChoice();
            System.out.println("--------------------------------");
            System.out.println("- Top 5 Greatest Files By Size -");
            System.out.println("--------------------------------");
            FileScreenPrinter(fileList);
            System.out.println("--------------------------------");
            //endregion

        } else if (queryId == 2){

            //region Display directory largest in size
            dirList = menuChoiceDAO.getDirectoryChoice(queryId);
            System.out.println("--------------------------------");
            System.out.println("-  Directory Largest In Size   -");
            System.out.println("--------------------------------");
            DirScreenPrinter(dirList);
            System.out.println("--------------------------------");
            //endregion

        } else if (queryId == 1){

            //region Display directory with most files
            dirList = menuChoiceDAO.getDirectoryChoice(queryId);
            System.out.println("--------------------------------");
            System.out.println("-  Directory With Most Files   -");
            System.out.println("--------------------------------");
            DirScreenPrinter(dirList);
            System.out.println("--------------------------------");
            //endregion

        } else if (queryId == 6){
            System.out.println("********************");
            System.out.println("*      EXITED      *");
            System.out.println("********************");
            System.exit(0);
            return;
        } else {
            System.out.println("************************************************************");
            System.out.println("* The value you have input is not a valid choice TRY AGAIN *");
            System.out.println("************************************************************");
            SelectAndClearByMenuChoice();
            //endregion
        }
        SelectAndClearByMenuChoice();
        //endregion



    }

    private static void CountDownToClear(MenuChoiceDAO menuChoiceDAO){

        System.out.println("DataBase Clear has been selected, will execute in");
        try
        {
            Thread.sleep(1000); // 1 second
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("10");
        try
        {
            Thread.sleep(1000); // 1 second
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("9");
        try
        {
            Thread.sleep(1000); // 1 second
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("8");
        try
        {
            Thread.sleep(1000); // 1 second
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("7");
        try
        {
            Thread.sleep(1000); // 1 second
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("6");
        try
        {
            Thread.sleep(1000); // 1 second
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("5");
        try
        {
            Thread.sleep(1000); // 1 second
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("4");
        try
        {
            Thread.sleep(1000); // 1 second
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("3");
        try
        {
            Thread.sleep(1000); // 1 second
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("2");
        try
        {
            Thread.sleep(1000); // 1 second
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("1");
        try
        {
            Thread.sleep(1000); // 1 second
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if(menuChoiceDAO.clearDataBase()){
            logger.info("DataBase Cleared");
        }else {
            logger.info("failed to Clear DataBase");
        }
        //endregion

    }//count down then clear database

    private static HashSet FileTypeHashSetMaker() {

        //region Parameters
        FileDAO fileDAO = new FileDAOImpl();
        List<File> fileList = fileDAO.getFileAll();
        HashSet<String> fileTypeHashSet = new HashSet<>();
        //endregion

        //region For Loop to Add FileTypes to Hashset
        for (File file:fileList){
            fileTypeHashSet.add(file.getFileType());
        }
        //endregion

        return fileTypeHashSet;
    }

    private static String UserChoiceFileType(){
        HashSet<String> fileTypeHashSet = FileTypeHashSetMaker();

        System.out.println("***************************************************");
        for (String fileType : fileTypeHashSet){
            System.out.println("      " + fileType );
        }
        System.out.println("***************************************************");
        Scanner scanner = new Scanner(System.in);
        System.out.println("* Key in File Type Below as appears above *");
        return scanner.nextLine();
    }

    private static void FileScreenPrinter(List<File> fileList){
        for (File file:fileList){
            System.out.println("- id = " + file.getIdFile() + ", Name = " + file.getFileName() +
                    ", Size =  " + file.getFileSize() + " bytes"+ ", Path = " + file.getPath());
        }
    }

    private static void DirScreenPrinter(List<Directory> dirList){
        for (Directory directory:dirList){
            System.out.println("- id = " + directory.getIdDirectory() + ", Name = " +directory.getDirName() +
                    ", Size =  " + directory.getDirSize() + " bytes" + ", # of Files = " + directory.getNumberOfFiles());
        }

    }

}
