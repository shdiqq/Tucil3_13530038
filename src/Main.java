import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        
        int x;
        int y;
        int[] posisi = new int[2];
        int[][] initial = new int [4][4];
        Menu Menu = new Menu();
        Scanner inputFile;

        int menu = Menu.menu();
        if ( menu == 2 ) {
            inputFile = Menu.menu_2();

        } else {
            inputFile = Menu.menu_1();
        }
        initial = Menu.membentuk_matriks_posisi_awal(inputFile);
        posisi = Menu.mencari_posisi_0(initial);
        x = posisi[0];
        y = posisi[1];
        
        Puzzle puzzle = new Puzzle();
        if (puzzle.cek_dapat_diselesaikan(initial)) {
            System.out.println("Status Tujuan Dapat Dicapai");
            System.out.println("Berikut urutan matriks dari posisi awal ke posisi akhir");
            puzzle.using_branch_bound(initial, x, y);
        } 
        else {
            System.out.println("Status Tidak Tujuan Dapat Dicapai");
        }
        System.out.println("Program telah Berakhir");
    }
}
