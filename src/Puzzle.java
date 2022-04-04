import java.util.*;

public class Puzzle {
	
	// Dimensi dari matrix puzzle
	public int dimensi = 4;

	// Pergerakan disimpan dalam array yang terdiri dari baris dan kolom
	// Pergerakan ke atas = baris[0] kolom[0]
	// Pergerakan ke kanan = baris[1] kolom[1]
	// Pergerakan ke bawah = baris[2] kolom[2]
	// Pergerakan ke kiri = baris[3] kolom[3]
	int[] baris = { -1, 0, 1, 0 };
	int[] kolom = { 0, 1, 0, -1 };
	
	// Memberi output isi matrix
	public void print_Matrix(int[][] matrix) {
		System.out.println("=====================");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if ( j == 0 ) {
					System.out.print("| ");
				}
				if ( matrix[i][j] != 0 ) {
					if ( matrix[i][j] < 10 ) {
						System.out.print(matrix[i][j] + "  | ");
					} else {
						System.out.print(matrix[i][j] + " | ");
					}
				} else {
					System.out.print("   | ");
				}
			}
			System.out.println();
			if ( i != matrix.length - 1) {
				System.out.println("---------------------");
			}
		}
		System.out.println("=====================");
	}

	// Cetak jalur dari simpul akar ke simpul tujuan
	public void print_jalur(Node root) {
		if (root == null) {
			return;
		}
		print_jalur(root.parent);
		print_Matrix(root.matrix);
		System.out.println();
	}

	// Menghitung dan mengembalikan jumlah ubin yang tidak kosong yang tidak terdapat pada susunan akhir -> g(P)
	public int menghitung_ubin_tidak_kosong(int[][] initial, int[][] goal) {
		int count = 0;
		for (int i = 0; i < initial.length; i++) {
			for (int j = 0; j < initial.length; j++) {
				if (initial[i][j] != 0 && initial[i][j] != goal[i][j]) {
					count++;
				}
			}
		}
		return count;
	}
	
	// Memeriksa apakah setelah berpindah, posisi (x, y) masih koordinat yang valid
	public boolean cek_perpindahan_valid(int x, int y) {
		return (x >= 0 && x < dimensi && y >= 0 && y < dimensi);
	}
	
	// Menghitung reachable goal untuk menguji apakah layak atau tidak puzzle untuk dikerjakan/disusun dengan memanfaatkan KURANG(i) + X
	public boolean cek_dapat_diselesaikan(int[][] matrix) {
		System.out.println("Berikut Puzzle Awal");
		print_Matrix(matrix);
		System.out.println();

		int count = 0;
		Integer[] fungsiKurang = new Integer[matrix.length*matrix.length];
		int k = 0;
		// mencari nilai X ( 0 atau 1 )
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				fungsiKurang[k] = matrix[i][j];
				k++;
				if (matrix[i][j] == 0) {
					if ( (i+j) % 2 == 1) {
						count++;
					}
				}
			}
		}
		
		Integer[] nilaiFungsiKurang = new Integer[matrix.length*matrix.length];
		int count_temp;

		// menghitung Kurang(i)
		for (int i = 0; i < fungsiKurang.length; i++) {
			count_temp = 0;
			for (int j = i + 1; j < fungsiKurang.length; j++) {
				if ( fungsiKurang[i] == 0 ) {
					count_temp++;
				} else {
					if ( fungsiKurang[i] > fungsiKurang[j] && fungsiKurang[j] != 0) {
						count_temp++;
					}
				}
			}
			if ( fungsiKurang[i] != 0 ) {
				nilaiFungsiKurang[fungsiKurang[i] - 1] = count_temp;
			} else {
				nilaiFungsiKurang[15] = count_temp;
			}
			count = count + count_temp;
		}
		for ( int i = 0; i < nilaiFungsiKurang.length; i++) {
			System.out.println("Nilai KURANG(" + (i+1) + ") = " + nilaiFungsiKurang[i] );
		}
		System.out.println("Nilai dari KURANG(i) + X adalah " + count);
		System.out.println();
		return count % 2 == 0;
	}
	
	// Proses pencarian solusi puzzle matriks dengan menerapkan	 algoritma branch and bound
	public void using_branch_bound(int[][] initial, int x, int y) {
		long start = System.currentTimeMillis();
		int[][] goal= { {1, 2, 3, 4}, 
						{5, 6, 7, 8}, 
						{9, 10, 11, 12}, 
						{13, 14, 15, 0} };
		PriorityQueue<Node> pq = new PriorityQueue<Node>(999999, (a, b) -> (a.ubin_not_empty + a.level) - (b.ubin_not_empty + b.level)); //buat prioqueue dgn memprioritaskan ubin_not_empty terkecil
		Node root = new Node(initial, x, y, x, y, 0, "Initial", null);
		root.inisiasi_ubin_not_empty(menghitung_ubin_tidak_kosong(initial, goal));
		pq.add(root); //enquque pq
		int j = 0;

		while (!pq.isEmpty()) {
			Node min = pq.poll(); //dequque pq
			j++;
			if (min.ubin_not_empty == 0) {
				print_jalur(min);
				long end = System.currentTimeMillis();
				System.out.println("Waktu eksekusi program adalah "+ (end - start) + " milidetik");
				System.out.println("Jumlah simpul yang dibangkitkan di dalam pohon ruang status pencarian adalah " + (pq.size() + j));
				return;
			}
			
			for (int i = 0; i < 4; i++) {
	            if (cek_perpindahan_valid(min.x + baris[i], min.y + kolom[i])) {
					// Pergerakan ke atas, kanan, bawah, kiri
					String move;
					if ( i == 0 && min.move != "Bawah" ){
						move = "Atas";
						Node child = new Node(min.matrix, min.x, min.y, min.x + baris[i], min.y + kolom[i], min.level + 1, move, min);
						child.inisiasi_ubin_not_empty(menghitung_ubin_tidak_kosong(child.matrix, goal));
						pq.add(child);
					} else if ( i == 1 && min.move != "Kiri" ){
						move = "Kanan";
						Node child = new Node(min.matrix, min.x, min.y, min.x + baris[i], min.y + kolom[i], min.level + 1, move, min);
						child.inisiasi_ubin_not_empty(menghitung_ubin_tidak_kosong(child.matrix, goal));
						pq.add(child);
					} else if ( i == 2 && min.move != "Atas" ){
						move = "Bawah";
						Node child = new Node(min.matrix, min.x, min.y, min.x + baris[i], min.y + kolom[i], min.level + 1, move, min);
						child.inisiasi_ubin_not_empty(menghitung_ubin_tidak_kosong(child.matrix, goal));
						pq.add(child);
					} else if ( i == 3 && min.move != "Kanan" ){
						move = "Kiri";
						Node child = new Node(min.matrix, min.x, min.y, min.x + baris[i], min.y + kolom[i], min.level + 1, move, min);
						child.inisiasi_ubin_not_empty(menghitung_ubin_tidak_kosong(child.matrix, goal));
						pq.add(child);
					}
	            }
	        }
		}
	}
}
