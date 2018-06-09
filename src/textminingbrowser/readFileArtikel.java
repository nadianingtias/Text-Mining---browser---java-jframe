/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textminingbrowser;

import java.io.File;
import java.util.ArrayList;

public class readFileArtikel {
    public static void main(String[] args) {
        ArrayList<File> listFiles = new ArrayList<>();
        final File folder = new File("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\TextMiningBrowser\\src\\resources");
        listFiles = listFilesForFolder(folder);
        readListFiles(listFiles);
    }
    public static ArrayList<File> listFilesForFolder(final File folder) {
        ArrayList<File> mListFiles = new ArrayList<>();
        int counter=0;
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    mListFiles.add(fileEntry);
                    //System.out.println(fileEntry.getName());
                    //System.out.println("mList: " +mListFiles.get(counter).getName());
                    counter++;
                }
            }
        return mListFiles;
        }

    private static void readListFiles(ArrayList<File> mFiles) {
        for(File mfile : mFiles){
            System.out.println("FILE : "+mfile.getName());
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
