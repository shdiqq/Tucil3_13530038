import java.util.*;
import java.io.*;

public class Menu {

    Scanner input = new Scanner(System.in);

    int[][] membentuk_matriks_posisi_awal(Scanner inputFile){
        int[][] initial = new int [4][4];
        for ( int i = 0 ; i < 4 ; i++ ){
            for ( int j = 0 ; j < 4 ; j++ ){
                if( inputFile.hasNextInt() ) {
                    initial[i][j]= inputFile.nextInt();
                }
            }
        }
        return initial;
    }

    public int[] mencari_posisi_0(int[][] matriks){
        int[] posisi = new int [2];
        for ( int i = 0 ; i < 4 ; i++ ){
            for ( int j = 0 ; j < 4 ; j++ ){
                if(matriks[i][j] == 0){
                    posisi[0] = i;
                    posisi[1] = j;
                    return(posisi);
                }
            }
        }
        return posisi;
    }
        
    public boolean cek_file_tersedia(String namaFile){
        File folder1 = new File("../test");
        String contents[] = folder1.list();
        boolean isFound = false;
        int i = 0;
        while (!isFound && i < contents.length) {
            if ( contents[i].equals(namaFile) ){
                isFound = true;
            } else {
                i++;
            }
        }
        return isFound;
    }

    public int menu() {
        input = new Scanner(System.in);
        System.out.println("Ketik 1 jika posisi awal 15-puzzle dibangkitkan secara acak atau ketik 2 menggunakan file teks");
        int inputInt = input.nextInt();
        while ( inputInt != 1 && inputInt != 2 ) {
            System.out.println("Input salah!");
            inputInt = input.nextInt();
        }
        return inputInt;
    }

    public Scanner menu_1() throws FileNotFoundException{
        Random randNum = new Random();
        File folder1 = new File("../test");
        String contents[] = folder1.list();
        int num = randNum.nextInt(contents.length);
        Scanner inputFile = new Scanner (new File("../test/" + contents[num]));
        return inputFile;
    }
    
    public Scanner menu_2() throws FileNotFoundException{
        System.out.print("Masukkan nama file yang berada pada folder test ( contoh : test1.txt ) : ");
        input = new Scanner(System.in);
        String inputString = input.nextLine();
        
        while ( !cek_file_tersedia(inputString) ){
            System.out.print("Nama file tidak ditemukan pada folder test, masukkan nama file : ");
            inputString = input.nextLine();
        }
        Scanner inputFile = new Scanner (new File("../test/" + inputString));
        
        return inputFile;
    }
}
