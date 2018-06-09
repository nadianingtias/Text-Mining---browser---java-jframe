/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextMiningBrowser;

public class learning {
    public static void main2(String[] args) {
        //tahapan pada tokenizing
        String kalimat = "Output Awal : Dampak kecanduan internet (internet addiction) pada remaja\n" +
"Siti Nurina Hakim, Aliffatullah Alyu Raj, Fakultas Psikologi Universitas Muhammadiyah Surakarta\n" +
"http://jurnal.unissula.ac.id/index.php/ippi/article/view/2200\n" +
"Penelitian ini dilakukan atas dasar fenomena mulai semaraknya kecanduan internet (internet addiction) pada berbagai kalangan, terutama remaja. Tujuan penelitian ini adalah untuk memahami dan mendisripsikan berbagai dampak dari kecanduan internet (internet addiction) pada remaja. Penelitian ini dilakukan dengan metode kualitatif, dan teknik pengambilan sampel dilakukan secara proportional purposive random sampling, diperoleh 6 orang subjek, 3 laki-laki dan 3 perempuan. Hasil penelitian menunjukkan ada dampak positif dan negatif, yang dikelompokkan menjadi dampak yang bersifat sosial, klinis, akademis, ekonomis, dan agamis. Hasil penelitian ini juga memperlihatkan bahwa kecanduan internet (internet addiction) ini lebih banyak dampak negatifnya dibandingkan dampak positifnya.";
        System.out.println(kalimat);
        
        //tahapan pada filtering
        String content= "";
        String regex ="(\\\\s*\\\\bnot\\\\b\\\\s*)|(\\\\s*\\\\bis\\\\b\\\\s*)";
        StringBuilder stringBuilder = new StringBuilder();
        String finalString = stringBuilder.toString();
        System.out.println(finalString);
        
        regex = "(\\s*\\bnot\\b\\s*)|(\\s*\\bis\\b\\s*)"; //format yang bener untuk filtering
        //untuk memasukkan kata bantu yang dilarang
        String[] filter1 = {"dan","lagi","serta","tetapi","akan tetapi","melainkan","tidak hanya","sebagainya","bila","selama","sesudah","sehabis","agar","supaya","biar","sebab","karena","sebab itu","hingga","sampai","jika","apabila","andaikata","atau","maupun","seperti","ibarat","bak","semakin","kian","bahwa","sambil","sembari","alkisah","konon","dengan","tetapi"};
        String rx;
        StringBuilder strBuilder = new StringBuilder();
        int count=0;
        for(String filter: filter1){
            if(!(count==0))
            strBuilder.append("|");
            strBuilder.append("(\\s*\\b"+filter+"\\b\\s*)");
            count++;
        }
        
        //untuk memasukkan symbol
        String[] filter2 = {".",",","!","://","()"};
        String rx2;
        StringBuilder strBuilder2 = new StringBuilder();
        int countt=0;
        for(String filter: filter2){
           if(!(countt==0))
                strBuilder2.append("|");
                strBuilder2.append("(\\s*\\b"+filter+"\\b\\s*)");
                countt++;
        }
        
        //System.out.println(strBuilder);
        System.out.println(strBuilder2);
        rx2 = strBuilder2.toString();
        rx = strBuilder.toString();
        //System.out.println("rx : "+rx);
        //System.out.println("rx2 : "+rx2);
        content = kalimat;
        content = content.replaceAll(rx, " ");
        content = content.replaceAll(rx2, " ");
        //System.out.println("content : "+ content); 
        String[] pecah1 = content.split(" ");
        System.out.println("HASIL :");
        for (int counter = 0; counter < pecah1.length; counter++) {
            System.out.println(" " + pecah1[counter]);
        }
        
        //------------- cek string sama atau tidak
        String me = "Nadia Ningtias";
        String you = "Maya Listyani";
        System.out.println(me.compareToIgnoreCase(you));
        //------------------------ rumus logaritma
        // get two double numbers
        double x = 5;
        double y = -497.99;

        // get the natural logarithm for x
        System.out.println("Math.log(" + x + ")=" + Math.log(x));

        // get the natural logarithm for y
        System.out.println("Math.log(" + y + ")=" + Math.log(y));
        
        int nArtikel = 5;
        int[] dokFrek = {0,2,1};
        double log = Math.log(nArtikel/dokFrek[2]);
        System.out.println("log : "+ log);
    }
    
    
}
