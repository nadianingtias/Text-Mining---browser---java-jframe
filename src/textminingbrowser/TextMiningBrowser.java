/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textminingbrowser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import static textminingbrowser.Artikel.getFilterKata;
import static textminingbrowser.readFileArtikel.listFilesForFolder;

/**
 *
 * @author Nadian
 */
public class TextMiningBrowser {
    private static Scanner scanner = new Scanner(System.in);
//    ArrayList<String> 
    public static void main(String[] args) {  
        ArrayList<File> listFiles = new ArrayList<>(); //siapkan tempat untuk tampung semua file
        final File folder = new File("E:\\Users\\Nadian\\Documents\\NetBeansProjects\\TextMiningBrowser\\src\\resources");
        
        //Baca resourcse 1 folder
        listFiles = listFilesForFolder(folder); ///mendapatkan semua file dari folder
        readListFiles(listFiles); //cek udah dapat file atau belum
        
        ArrayList<Artikel> listArtikel = new ArrayList<>(); //siapkan tempat untuk list of Artikel (DATABASE)
        //tiap file yang ada di dalam folder dibaca, dibuatkan instance Artikel, constructornya tipedata File 
        for(File eachFile : listFiles){
            Artikel artikelBaru = new Artikel(eachFile);  // buat instance dari tiap file
            listArtikel.add(artikelBaru);                   //tambahkan ArtikelBaru ke dalam listArtikel
        }
        
        //-------------------------- 1. preparation DB artikel SELESAI
        //-------------------------- 2. get inputan user
        int nArtikelBase = listArtikel.size();
        String inputUser ;
        System.out.print("1. Masukkan keywords: ");
            inputUser = scanner.nextLine();
            
        inputUser = inputUser.replaceAll("[^A-Za-z ]", ""); //filter symbol of userInput
        System.out.println("simbol filtered: "+inputUser);
        
        inputUser = getFilterKata(inputUser);               //filter kata bantu
        System.out.println("words filtered: "+inputUser);
        
        //split inputan menjadi n term
        String term[] = inputUser.split(" ");
        int nTerm = term.length;
        for(String str: term){
            //System.out.println("term " +str);
        }   
        // tf idf
        //----------------3. defining [term frequency]->tf, (word->how much term(word) in each document)
        //dok1 term1 term2
        //dok2 term1 term2
        //dok3 term1 term2
        int termFrek[][] = new int[nArtikelBase][nTerm];
        termFrek = initNol2D(termFrek);
        for(int i=0; i<nArtikelBase ; i++){
            termFrek[i] = getTFFromArtikel(term,listArtikel.get(i)); //get termcount perdokumen
        }
        printTermFrek(termFrek);
        
        //---------------- 4. defining [document frequency]-df, (term->how much document consisting those terms)
        int dokFrek[] = new int[nTerm];
        dokFrek = initNol1D(dokFrek);
        dokFrek = getDokFrek(termFrek);
        printDokFrek(dokFrek);
        
        //---------------- 5. defining [inverse document frequency]- idf, idf term = (1 + log(nArtikel/df term))
        double inverseDokfrek[] = new double[nTerm];
        inverseDokfrek = initDoubleNol1D(inverseDokfrek);
        inverseDokfrek = getInverseDokFrek(nArtikelBase,dokFrek);
        printInverseDokFrek(inverseDokfrek);
        
        //---------------- 6. defining tf*idf (pembobotan perdokumen)> kalikan term frekuensi dengan bobot kemunculan term(idf)
        //4,5 -> dok1
        //5,6 -> dok2
        double bobotDok[] = new double[nArtikelBase]; //preparing arrays of pair jarak and index of image
        bobotDok = initDoubleNol1D(bobotDok);
        bobotDok = getBobot(termFrek, inverseDokfrek);
        //----------------- 7 as LAST.do Sort for the array consisting val of bobot
        double[][] pairJarakIndex = new double[bobotDok.length][2]; //preparing arrays of pair jarak and index of image
        for(int i=0 ; i<bobotDok.length;i++){
            pairJarakIndex[i][0] = bobotDok[i];  //bobot
            pairJarakIndex[i][1] = i; //index of artikel
        }
        //descending
        System.out.println(" ");
        Arrays.sort(pairJarakIndex, (b, a) -> Double.compare(a[0], b[0]));
        for(int i=0;i<bobotDok.length;i++){
                System.out.println("sorted? "+pairJarakIndex[i][0]+" "+pairJarakIndex[i][1]);
        }
        
        
    }
    public static ArrayList<File> listFilesForFolder(final File folder) {
        ArrayList<File> mListFiles = new ArrayList<>();
        int counter=0;
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    mListFiles.add(fileEntry);
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

    //return termcount 
    public static int[] getTFFromArtikel(String[] mString, Artikel mArtikel) {
        int result[] = new int[mString.length];
        result = initNol1D(result);
        
        ArrayList<String> tokens =     mArtikel.getTokened();
        int counter=0;
        for(String token : tokens){
           for(int j=0; j<mString.length;j++){
               if(mString[j].compareToIgnoreCase(token)==0)
                   result[j]++;
           }
        }
        return result;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static int[][] initNol2D(int[][] mTermFrek) {
        int nArtikel = mTermFrek.length;
        int nTerm = mTermFrek[0].length;
        int result[][] = new int[nArtikel][nTerm];
        for(int i=0 ; i< nArtikel ; i++){
            for(int j=0 ;j< nTerm ; j++){
                result[i][j]=0;
            }
        }
        return result;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static int[] initNol1D(int[] array) {
        int result[] = new int[array.length];
        for(int j=0; j<array.length;j++) //initial
            result[j] = 0;
        
        return result;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static int[] getDokFrek(int[][] mTermFrek) {
        int nArtikel = mTermFrek.length;
        int nTerm = mTermFrek[0].length;
        int result[] = new int[nTerm];
        result = initNol1D(result);
        for(int i=0 ; i<nArtikel ; i++){
            for(int j=0 ; j<nTerm ; j++){
                if(mTermFrek[i][j]>0)
                    result[j]++;
            }
        }
        return result;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    //idf term = (1 + log(nArtikel/df term))
    public static double[] getInverseDokFrek(int nArtikel, int[] dokFrek) {
        int nTerm = dokFrek.length;
        double result[] = new double[nTerm];
        result = initDoubleNol1D(result);
        
        for(int j=0 ; j<nTerm ; j++){
            double log=0;
            if(dokFrek[j]!=0)
                log = Math.log((double)(nArtikel/dokFrek[j]));
            result[j] = 1 + log;
        }
        
        return result;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static double[] initDoubleNol1D(double[] array) {
        double result[] = new double[array.length];
        for(int j=0; j<array.length;j++) //initial
            result[j] = 0;
        
        return result;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void printTermFrek(int[][] mTermFrek) {
        int nArtikel = mTermFrek.length;
        int nTerm = mTermFrek[0].length;
        System.out.println("\nTerm frek:");
        
        for(int i=0 ; i<nArtikel ; i++){
            System.out.print("dok "+i+" : ");
            for(int j=0 ; j<nTerm ; j++){
                System.out.print(mTermFrek[i][j] +" ");
            }
            System.out.println(" ");
        }
    }

    public static void printDokFrek(int[] mDokFrek) {
        int nTerm = mDokFrek.length;
        System.out.println("\nDok Frek :");
        for(int j=0 ; j<nTerm ; j++){
                System.out.print(mDokFrek[j] +" ");
            }
        System.out.println(" ");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void printInverseDokFrek(double[] mInverseDokfrek) {
        int nTerm = mInverseDokfrek.length;
        System.out.println("\nInverse Dok Frek :");
        for(int j=0 ; j<nTerm ; j++){
                System.out.print(mInverseDokfrek[j] +"-");
            }
        System.out.println(" ");
    }

    public static double[] getBobot(int[][] mTermFrek, double[] mIDF) {
        int nArtikel = mTermFrek.length;
        int nTerm = mTermFrek[0].length;
        double result[] = new double[nArtikel];
        result = initDoubleNol1D(result);
        
        for(int i=0 ; i<nArtikel ; i++){
            for(int j=0 ; j<nTerm ; j++){
                    double temBobot = mTermFrek[i][j]* mIDF[j];
                    result[i] += temBobot;
                    //result[i][1] = i;
            }
        }
        return result;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static double[][] initDoubleNol2D(double[][] array2D) {
        int row = array2D.length;
        int column = array2D[0].length;
        double result[][] = new double[row][column];
        for(int i=0 ; i< row ; i++){
            for(int j=0 ;j< column ; j++){
                result[i][j]=0;
            }
        }
        return result;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
