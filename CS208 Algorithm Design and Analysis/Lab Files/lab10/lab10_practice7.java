import java.util.*;

public class lab10_practice7 {  // x is col, y is row
    private static int dominoNumber = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt(), x = sc.nextInt(), y = sc.nextInt(), length = (int) Math.pow(2, k);
        int[][] chessBoard = new int[length][length];
        chessBoard[x][y] = -1;
        solve(chessBoard, x, y, 0, 0, k);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++)  {
                System.out.print(chessBoard[i][j] + (chessBoard[i][j] == -1 || chessBoard[i][j] / 10 > 0 ? "" : " ") + " ");
            }
            System.out.println();
        }
    }

    private static void solve(int[][] chessBoard, int x, int y, int rowIndex, int colIndex, int k) {
        if (k == 0) return;

        int currentHalfLength = (int) Math.pow(2, k - 1), currentNumber = dominoNumber++,
                currentCase = (x < colIndex + currentHalfLength && y < rowIndex + currentHalfLength) ? 0 :
                        (x < colIndex + currentHalfLength) ? 1 :
                        (y < rowIndex + currentHalfLength) ? 2 : 3;
        // leftUp = 0, leftDown = 1, rightUp = 2, rightDown = 3

        switch (currentCase) {
            case 0:  // leftUp
                // set rightUpCorner of leftDown
                chessBoard[rowIndex + currentHalfLength][colIndex + currentHalfLength - 1] = currentNumber;
                // set leftDownCorner of rightUp
                chessBoard[rowIndex + currentHalfLength - 1][colIndex + currentHalfLength] = currentNumber;
                // set leftUpCorner of rightDown
                chessBoard[rowIndex + currentHalfLength][colIndex + currentHalfLength] = currentNumber;
                // solve leftUp
                solve(chessBoard, x, y, rowIndex, colIndex, k - 1);
                // solve leftDown
                solve(chessBoard, colIndex + currentHalfLength - 1, rowIndex + currentHalfLength, rowIndex + currentHalfLength, colIndex, k - 1);
                // solve rightUp
                solve(chessBoard, colIndex + currentHalfLength, rowIndex + currentHalfLength - 1, rowIndex, colIndex + currentHalfLength, k - 1);
                // solve rightDown
                solve(chessBoard, colIndex + currentHalfLength, rowIndex + currentHalfLength, rowIndex + currentHalfLength, colIndex + currentHalfLength, k - 1);
                break;
            case 1:  // leftDown
                // set rightDownCorner of leftUp
                chessBoard[rowIndex + currentHalfLength - 1][colIndex + currentHalfLength - 1] = currentNumber;
                // set leftDownCorner of rightUp
                chessBoard[rowIndex + currentHalfLength - 1][colIndex + currentHalfLength] = currentNumber;
                // set leftUpCorner of rightDown
                chessBoard[rowIndex + currentHalfLength][colIndex + currentHalfLength] = currentNumber;
                // solve leftUp
                solve(chessBoard, colIndex + currentHalfLength - 1, rowIndex + currentHalfLength - 1, rowIndex, colIndex, k - 1);
                // solve leftDown
                solve(chessBoard, x, y, rowIndex + currentHalfLength, colIndex, k - 1);
                // solve rightUp
                solve(chessBoard, colIndex + currentHalfLength, rowIndex + currentHalfLength - 1, rowIndex, colIndex + currentHalfLength, k - 1);
                // solve rightDown
                solve(chessBoard, colIndex + currentHalfLength, rowIndex + currentHalfLength, rowIndex + currentHalfLength, colIndex + currentHalfLength, k - 1);
                break;
            case 2:  // rightUp
                // set rightDownCorner of leftUp
                chessBoard[rowIndex + currentHalfLength - 1][colIndex + currentHalfLength - 1] = currentNumber;
                // set rightUpCorner of leftDown
                chessBoard[rowIndex + currentHalfLength][colIndex + currentHalfLength - 1] = currentNumber;
                // set leftUpCorner of rightDown
                chessBoard[rowIndex + currentHalfLength][colIndex + currentHalfLength] = currentNumber;
                // solve leftUp
                solve(chessBoard, colIndex + currentHalfLength - 1, rowIndex + currentHalfLength - 1, rowIndex, colIndex, k - 1);
                // solve leftDown
                solve(chessBoard, colIndex + currentHalfLength - 1, rowIndex + currentHalfLength, rowIndex + currentHalfLength, colIndex, k - 1);
                // solve rightUp
                solve(chessBoard, x, y, rowIndex, colIndex + currentHalfLength, k - 1);
                // solve rightDown
                solve(chessBoard, colIndex + currentHalfLength, rowIndex + currentHalfLength, rowIndex + currentHalfLength, colIndex + currentHalfLength, k - 1);
                break;
            case 3:  // rightDown
                // set rightDownCorner of leftUp
                chessBoard[rowIndex + currentHalfLength - 1][colIndex + currentHalfLength - 1] = currentNumber;
                // set rightUpCorner of leftDown
                chessBoard[rowIndex + currentHalfLength][colIndex + currentHalfLength - 1] = currentNumber;
                // set leftDownCorner of rightUp
                chessBoard[rowIndex + currentHalfLength - 1][colIndex + currentHalfLength] = currentNumber;
                // solve leftUp
                solve(chessBoard, colIndex + currentHalfLength - 1, rowIndex + currentHalfLength - 1, rowIndex, colIndex, k - 1);
                // solve leftDown
                solve(chessBoard, colIndex + currentHalfLength - 1, rowIndex + currentHalfLength, rowIndex + currentHalfLength, colIndex, k - 1);
                // solve rightUp
                solve(chessBoard, colIndex + currentHalfLength, rowIndex + currentHalfLength - 1, rowIndex, colIndex + currentHalfLength, k - 1);
                // solve rightDown
                solve(chessBoard, x, y, rowIndex + currentHalfLength, colIndex + currentHalfLength, k - 1);
        }

    }
}
