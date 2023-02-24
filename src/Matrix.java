package src;

public final class Matrix {
	
	public static final int MAX_VALUE = 1 << 3;
	
	private Matrix() {}
	
	public static float[] create(final int n, final int m) {
		final float matrix[] = new float[n * m];
		
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < m; ++j) {
				matrix[j + i * n] = (float)(Math.random() * 2.f - 1.f) * MAX_VALUE;
			}
		}
		
		return matrix;
	}
	
	public static float[] create(final int n) { return Matrix.create(n, n); }
	
	public static void print(final float matrix[], final int n, final int m) {
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < m; ++j) {
				System.out.print(String.format("\t%.2f", matrix[j + i * n]));
			}
			System.out.println();
		}
	}
	
	public static float[] minor(final float matrix[], final int n, final int i, final int j) {
		final float sub[] = new float[(n - 1) * (n - 1)];
		
		for(int y = 0, k = 0; y < n; ++y) {
			for(int x = 0; x < n; ++x) {
				if(x == j || y == i) continue;
				sub[k++] = matrix[x + y * n];
			}
		}
		
		return sub;
	}
	
	public static float RecursiveDeterminant(final float matrix[], final int n) {
		if(n <= 1) return matrix[0];
		if(n <= 2) return matrix[0] * matrix[3] - matrix[1] * matrix[2];
		
		float sum = 0.f;
		for(int i = 0; i < n; ++i) sum += ((i & 1) == 0 ? matrix[i] : -matrix[i]) * RecursiveDeterminant(Matrix.minor(matrix, n, 0, i), n - 1);
		
		return sum;
	}
	
	@SuppressWarnings("unused")
	public static float[] GaussJordanElimination(final float matrix[], final int n) {
		float determinant = 1.f;
		
		for(int i = 0, k = 0, j = 0, swap = 0; i < n; ++i, swap = 0) {
			for(j = i + 1, k = i; j < n; ++j) {
				if(matrix[i + k * n] == 0.f && matrix[i + j * n] != 0.f) {
					k = j; swap = 1; break;
				}
				
				if(Math.abs(matrix[i + j * n]) < Math.abs(matrix[i + k * n]) && matrix[i + j * n] != 0.f) {
					k = j; swap = 1;
				}
			}
			
			if(swap == 1) {
				if(matrix[i + k * n] == 0.f) break;
				
				for(j = 0; j < n; ++j) {
					final float a 		= matrix[j + i * n];
					matrix[j + i * n] 	= matrix[j + k * n];
					matrix[j + k * n] 	= a;
				}
			}
			
			determinant *= swap == 1 ? -matrix[i * (n + 1)] : matrix[i * (n + 1)];
			for(j = i + 1; j < n; ++j) matrix[j + i * n] /= matrix[i * (n + 1)];
			matrix[i * (n + 1)] = 1.f;
			
			for(k = i + 1; k < n; ++k) {
				if(matrix[i + k * n] == 0.f) continue;
				
				final float delta = matrix[i + k * n];
				
				for(j = i; j < n; ++j) matrix[j + k * n] -= matrix[j + i * n] * delta;
			}
			
		}
		
		return matrix;
	}
	
	public static float GaussJordanEliminationDeterminant(final float matrix[], final int n) {
		if(n <= 1) return matrix[0];
		if(n <= 2) return matrix[0] * matrix[3] - matrix[1] * matrix[2];
		
		float determinant = 1.f;
		
		for(int i = 0, k = 0, j = 0, swap = 0; i < n; ++i, swap = 0) {
			for(j = i + 1, k = i; j < n; ++j) {
				if(matrix[i + k * n] == 0.f && matrix[i + j * n] != 0.f) {
					k = j; swap = 1; break;
				}
				
				if(Math.abs(matrix[i + j * n]) < Math.abs(matrix[i + k * n]) && matrix[i + j * n] != 0.f) {
					k = j; swap = 1;
				}
			}
			
			if(swap == 1) {
				if(matrix[i + k * n] == 0.f) return 0.f;
				
				for(j = 0; j < n; ++j) {
					final float a 		= matrix[j + i * n];
					matrix[j + i * n] 	= matrix[j + k * n];
					matrix[j + k * n] 	= a;
				}
			}
			
			determinant *= swap == 1 ? -matrix[i * (n + 1)] : matrix[i * (n + 1)];
			for(j = i + 1; j < n; ++j) matrix[j + i * n] /= matrix[i * (n + 1)];
			matrix[i * (n + 1)] = 1.f;
			
			for(k = i + 1; k < n; ++k) {
				if(matrix[i + k * n] == 0.f) continue;
				
				final float delta = matrix[i + k * n];
				
				for(j = i; j < n; ++j) matrix[j + k * n] -= matrix[j + i * n] * delta;
			}
			
		}
		
		return determinant;
	}
	
}
