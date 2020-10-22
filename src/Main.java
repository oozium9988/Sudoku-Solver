
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        System.out.println("This is a sudoku solver. Enter the lines below. You can put a \"b\" or any other letter for blank spaces. Make sure you only use 9 characters per line.\n");
        
        String[] lines = new String[9];
        int lineCount = 1;

        while (true) {
            while (lineCount < 10) {
                System.out.print("Line " + (lineCount) + ": ");
                String line = reader.nextLine();
                if (line.length() != 9) {
                    System.out.println("That was not 9 characters, try again.");
                } else {
                    lines[(lineCount++) - 1] = line;
                }    
            }

            System.out.print("\nIs the above correct? If not, type \"R\" to restart. Otherwise, press enter.");

            String option = reader.nextLine();
            
            if (!option.equals("R")) {
                break;
            }
        }
        
        char[][] board = new char[9][9];
        
        for (int i = 0; i < 9; i++) {
            char[] chars = lines[i].toCharArray();
            for (int j = 0; j < 9; j++) {
                if ((int)(chars[j] - '0') > 0 && (int)(chars[j] - '0') <= 9) {
                    board[i][j] = chars[j];
                } else {
                    board[i][j] = '.';
                }               
            }
        }
        
        Solution.solveSudoku(board);
    }
    
}
