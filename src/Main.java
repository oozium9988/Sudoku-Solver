
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        System.out.println("This is a sudoku solver. Enter the lines below. You can put a \"b\" or any other letter for blank spaces. Make sure you only use 9 characters per line.\n");

        String line1;
        String line2;
        String line3;
        String line4;
        String line5;
        String line6;
        String line7;
        String line8;
        String line9;

        while (true) {

            while (true) {
                System.out.print("Line 1: ");
                line1 = reader.nextLine();
                if (line1.length() != 9) {
                    System.out.println("That was not 9 characters, try again.");
                } else {
                    break;
                }
            }
            
            while (true) {
                System.out.print("Line 2: ");
                line2 = reader.nextLine();
                if (line2.length() != 9) {
                    System.out.println("That was not 9 characters, try again.");
                } else {
                    break;
                }
            }
            while (true) {
                System.out.print("Line 3: ");
                line3 = reader.nextLine();
                if (line3.length() != 9) {
                    System.out.println("That was not 9 characters, try again.");
                } else {
                    break;
                }
            }
            while (true) {
                System.out.print("Line 4: ");
                line4 = reader.nextLine();
                if (line4.length() != 9) {
                    System.out.println("That was not 9 characters, try again.");
                } else {
                    break;
                }
            }
            while (true) {
                System.out.print("Line 5: ");
                line5 = reader.nextLine();
                if (line5.length() != 9) {
                    System.out.println("That was not 9 characters, try again.");
                } else {
                    break;
                }
            }
            while (true) {
                System.out.print("Line 6: ");
                line6 = reader.nextLine();
                if (line6.length() != 9) {
                    System.out.println("That was not 9 characters, try again.");
                } else {
                    break;
                }
            }
            while (true) {
                System.out.print("Line 7: ");
                line7 = reader.nextLine();
                if (line7.length() != 9) {
                    System.out.println("That was not 9 characters, try again.");
                } else {
                    break;
                }
            }
            while (true) {
                System.out.print("Line 8: ");
                line8 = reader.nextLine();
                if (line8.length() != 9) {
                    System.out.println("That was not 9 characters, try again.");
                } else {
                    break;
                }
            }
            while (true) {
                System.out.print("Line 9: ");
                line9 = reader.nextLine();
                if (line9.length() != 9) {
                    System.out.println("That was not 9 characters, try again.");
                } else {
                    break;
                }
            }

            System.out.print("\nIs the above correct? If not, type \"R\" to restart. Otherwise, press enter.");

            String option = reader.nextLine();
            
            if (!option.equals("R")) {
                break;
            }
        }
        
        String[] strings = new String[]{line1, line2, line3, line4, line5, line6, line7, line8, line9};
        char[][] board = new char[9][9];
        
        for (int i = 0; i < 9; i++) {
            char[] chars = strings[i].toCharArray();
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
