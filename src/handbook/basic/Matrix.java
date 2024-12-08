package handbook.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dungptm2
 */
public class Matrix {
    public void setZeroes(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int col0 = -1;
        for (int i = 0; i < row; i++) {
            if (matrix[i][0] == 0) {
                col0 = 0;
            }
            for (int j = 1; j < col; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = row - 1; i >= 0; i--) {
            for (int j = col - 1; j >= 1; j--) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
            if (col0 == 0) {
                matrix[i][0] = 0;
            }
        }
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        List<Integer> result = new ArrayList<Integer>(row * col);
        int sRow = 0, eRow = row - 1;
        int sCol = 0, eCol = col - 1;
        while (sRow <= eRow && sCol <= eCol) {
            for (int k = sCol; k <= eCol; k++) {
                if (matrix[sRow][k] != -200) {
                    result.add(matrix[sRow][k]);
                    matrix[sRow][k] = -200;
                }
            }
            sRow++;
            for (int k = sRow; k <= eRow; k++) {
                if (matrix[k][eCol] != -200) {
                    result.add(matrix[k][eCol]);
                    matrix[k][eCol] = -200;
                }

            }
            eCol--;
            for (int k = eCol; k >= sCol; k--) {
                if (matrix[eRow][k] != -200) {
                    result.add(matrix[eRow][k]);
                    matrix[eRow][k] = -200;
                }
            }
            eRow--;
            for (int k = eRow; k >= sRow; k--) {
                if (matrix[k][sCol] != -200) {
                    result.add(matrix[k][sCol]);
                    matrix[k][sCol] = -200;
                }
            }
            sCol++;
        }
        return result;
    }

    public boolean isValidSudoku(char[][] board) {
        int n = 9;
        int[][] mapRow = new int[n][n];
        int[][] mapCol = new int[n][n];
        int[][] mapBox = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    int k = (i / 3) * 3 + j / 3;
                    mapRow[i][num]++;
                    mapCol[j][num]++;
                    mapBox[k][num]++;
                    if (mapRow[i][num] > 1 || mapCol[j][num] > 1 || mapBox[k][num] > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Matrix m = new Matrix();
        System.out.println();
        System.exit(0);
    }


}
