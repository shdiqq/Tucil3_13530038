public class Node {

	// Node Induk
	public Node parent;

	// Informasi perpindahan ( atas, kanan, bawah, atau kiri )
	public String move;

	// Matrix ( puzzle )
	public int[][] matrix;
	
	// Posisi "0" pada matrix puzzle
	public int x, y;
	
	// Jumlah ubin tidak kosong yang tidak terdapat pada susunan akhir {g(P)}
	public int ubin_not_empty;
	
	// Panjang lintasan dari simpul akar ke P {f(P)} dengan memanfaatkan tingkat level pada tree
	public int level;
	
	// Constructor dengan parameter
	public Node(int[][] matrix, int x, int y, int newX, int newY, int level, String move, Node parent) {
		this.parent = parent;
		
		this.matrix = new int[matrix.length][];
		for (int i = 0; i < matrix.length; i++) {
			this.matrix[i] = matrix[i].clone();
		}
		
		// Swap value
		this.matrix[x][y]       = this.matrix[x][y] + this.matrix[newX][newY];
		this.matrix[newX][newY] = this.matrix[x][y] - this.matrix[newX][newY];
		this.matrix[x][y]       = this.matrix[x][y] - this.matrix[newX][newY];
		
		this.level = level;
		this.x = newX;
		this.y = newY;

		this.move = move;
	}
	
	// Fungsi untuk menyimpan ubin_not_empty
	public void inisiasi_ubin_not_empty(int ubin_not_empty){
		this.ubin_not_empty = ubin_not_empty;
	}
}
