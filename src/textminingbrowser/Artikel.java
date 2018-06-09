/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textminingbrowser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

class WordCount{
    String word;
    int count;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}

public class Artikel {
    private File fileArtikel;
    private String title;   //line 1
    private String penulis; //line 2
    private String tahun; //line 3
    private String hyperlink; //line 4
    private String article; //line 5
    
    private String articleFilteredSymbol; //only reduced symbol
    private String articleFilteredSymbolWords; //reduced symbol and some certain word
    private ArrayList<String> tokened = new ArrayList<>();  //terpisah berdasar kata
//    private ArrayList<WordCount> filterTokened  = new ArrayList<>();
    
    //ini constructur Artikel
    Artikel(File newFile) {
        //eachfile diolah disini
        this.fileArtikel = newFile;
        
        //String lokasi_file = "C:\\Users\\ASUS\\Documents\\NetBeansProjects\\Coba\\src\\File\\abstrak1.txt";
        try {
            //FileReader newFile = new FileReader(lokasi_file); //mendeteksi lokasi file
            newFile.createNewFile();
            Reader targetReader = new FileReader(newFile); // konversi file ke filereader nanti filenya bisa dibaca
            BufferedReader br = new BufferedReader(targetReader);
            
            String text;
            int counter =0;
            StringBuilder strBuilder = new StringBuilder();
            
            while((text = br.readLine()) != null){ //membaca per line
                //System.out.println(text);
                if(counter==0) {
                    this.title = text;
                }
                else if(counter==1) {
                    this.penulis = text;
                }
                else if(counter==2) {
                    this.tahun = text;
                }
                else if(counter==3) {
                    this.hyperlink = text;
                }
                else if (counter>=4) {
                    strBuilder.append(text);
                }
                counter++;
            }
            System.out.println("----------"+newFile.getName());
                System.out.println("judul: "+this.title);
//                System.out.println("penulis: "+this.penulis);
                System.out.println("Hyperlink: "+this.hyperlink);
            this.article = strBuilder.toString();
        }
        catch(FileNotFoundException fnfe) {
            fnfe.getMessage();
        }
        catch (IOException ioe) {
            ioe.getMessage();
        }
        
        this.articleFilteredSymbol=this.article.replaceAll("[^A-Za-z ]", ""); //filter untuk symbol
//        System.out.println("filter simbol : " +this.articleFilteredSymbol);
        
        
        this.articleFilteredSymbolWords = getFilterKata(this.articleFilteredSymbol); //filter untuk kata bantu
//        System.out.println("filter symbol word : "+this.articleFilteredSymbolWords);
        
        this.tokened = getTokenKata(this.articleFilteredSymbolWords); //proses tokenizing cuy
        this.tokened = getTokenKata(this.title);
                //jalankan method baca this.articleFilteredSymbolWords -> pisahkan dari spasi (" "), masukkan perkata ke dalam arraylist tokened
        
    }
    //ini default constructor
    public Artikel() {
    }

    public File getFileArtikel() {
        return fileArtikel;
    }

    public void setFileArtikel(File fileArtikel) {
        this.fileArtikel = fileArtikel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getArticleFilteredSymbol() {
        return articleFilteredSymbol;
    }

    public void setArticleFilteredSymbol(String articleFilteredSymbol) {
        this.articleFilteredSymbol = articleFilteredSymbol;
    }

    public String getArticleFilteredSymbolWords() {
        return articleFilteredSymbolWords;
    }

    public void setArticleFilteredSymbolWords(String articleFilteredSymbolWords) {
        this.articleFilteredSymbolWords = articleFilteredSymbolWords;
    }

    public ArrayList<String> getTokened() {
        return tokened;
    }

    public void setTokened(ArrayList<String> tokened) {
        this.tokened = tokened;
    }
    
    public static String getFilterKata(String words){ //method kata bantu
        String result;
        String regex ="(\\\\s*\\\\bnot\\\\b\\\\s*)|(\\\\s*\\\\bis\\\\b\\\\s*)";
        String[] filter1 = {"is","hampir","dan","lagi","serta","tetapi","akan tetapi","melainkan","tidak", "hanya","sebagainya","bila","selama","sesudah","sehabis","agar","supaya","biar","sebab","karena","sebab", "itu","hingga","sampai","jika","apabila","andaikata","atau","maupun","seperti","ibarat","bak","semakin","kian","bahwa","sambil","sembari","alkisah","konon","dengan","tetapi"};
        StringBuilder strBuilder = new StringBuilder();
        int count=0;
        for(String filter: filter1){
            if(!(count==0))
            strBuilder.append("|");
            strBuilder.append("(\\s*\\b"+filter+"\\b\\s*)");
            count++;
        }
        regex = strBuilder.toString();
        result = words.replaceAll(regex, " ");
        return result;
    }
    
    public static ArrayList<String> getTokenKata(String words) {
        ArrayList <String> result=new ArrayList<String>();
        String[] kata = words.split(" ");
        for(int counter=0;counter<kata.length;counter++) {
            result.add(kata[counter]);
//            System.out.println("token : "+counter+" "+kata[counter]);
        }
        return result;
    }
}
