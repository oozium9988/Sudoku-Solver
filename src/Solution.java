
import java.util.*;

class Solution {
    private static int solvedCount = 0;
    private static int solutionsLeft = 729;
    
    public static void solveSudoku(char[][] board) {
        List<List<HashSet<Integer>>> sudoku = new ArrayList<List<HashSet<Integer>>>();
        
        for (int i = 0; i < 9; i++) {
            sudoku.add(new ArrayList<HashSet<Integer>>());
            List<HashSet<Integer>> list = sudoku.get(i);
            for (int j = 0; j < 9; j++) {
                sudoku.get(i).add(new HashSet<Integer>());
                HashSet<Integer> set = sudoku.get(i).get(j);
                char c = board[i][j];
                
                if (c != '.') {
                    set.add((int)(c - '0'));
                    solvedCount++;
                    solutionsLeft-=8;
                } else {
                    for (int k = 1; k <= 9; k++) {
                        set.add(k);
                    }
                }
            }
        }
        
        printSudoku(sudoku);
        
        //int iterateCount = 0;
        int prevSolvedCount = 0;
        int prevSolutionsLeft = 0;
        char[][] prevBoard = new char[9][9];
        List<List<List<HashSet<Integer>>>> prevSudokus = new ArrayList();
        char[][][] prevBoards = new char[81][9][9];
        int prevBoardsCount = 0;
        //List<Integer> prevSolvedCounts = new ArrayList();
        //List<Integer> prevSolutionsLeftList = new ArrayList();
        //List<List<HashSet<Integer>>> prevSudoku = new ArrayList();
        int[] initialCoords = new int[2];
        int[][] prevPairs = new int[81][2];
        int prevPairsCount = 0;
        //int initialSolvedCount = 0;
        //int initialSolutionsLeft  = 0;
        List<List<Integer>> initCoords = new ArrayList();
        List<Integer> initSolvedCounts = new ArrayList();
        List<Integer> initSolutionsLeft = new ArrayList();
        initialCoords[0] = -1;
        initialCoords[1] = -1;
        
        while (solvedCount < 81) {
            for (int i = 0; i < 9; i+=3) {
                for (int j = 0; j < 9; j+=3) {
                    checkBox(i, j, sudoku, board);
                }
            }
            
            for (int i = 0; i < 9; i++) {
                checkRow(i, sudoku, board);
            }
            
            for (int i = 0; i < 9; i++) {
                checkColumn(i, sudoku, board);
            }
            
            for (int i = 0; i < 9; i+=3) {
                for (int j = 0; j < 9; j+=3) {
                    checkBox1(i, j, sudoku, board);
                }
            }
            
            for (int i = 0; i < 9; i++) {
                checkRow1(i, sudoku, board);
            }
            
            for (int i = 0; i < 9; i++) {
                checkColumn1(i, sudoku, board);
            }
            
            boolean broke = false;
            
            if (solutionsLeft == prevSolutionsLeft) {
                int[] coords = new int[2];
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (sudoku.get(i).get(j).size() == 2) {
                            initCoords.add(new ArrayList(Arrays.asList(i, j)));
                            coords[0] = i;
                            coords[1] = j;

                            /*System.out.println("i: " + i + " j: " + j);
                            System.out.print("Set: ");

                            for (int num : sudoku.get(i).get(j)) {
                                System.out.print(num + " ");
                            }
                            System.out.println();*/

                            broke = true;
                            break;
                        }
                    }
                    if (broke) {
                        break;
                    }
                }

                int[] pair = new int[2];
                int count = 0;

                for (int num : sudoku.get(coords[0]).get(coords[1])) {
                    pair[count++] = num;
                }
                
                prevPairs[prevPairsCount][0] = pair[0];
                prevPairs[prevPairsCount++][1] = pair[1];

                List<List<HashSet<Integer>>> prevSudoku = new ArrayList();
                
                count = 0;

                for (List<HashSet<Integer>> list : sudoku) {
                    prevSudoku.add(new ArrayList());
                    int setCount = 0;

                    for (HashSet<Integer> set : list) {
                        prevSudoku.get(count).add(new HashSet<Integer>());
                        for (int num : set) {
                            prevSudoku.get(count).get(setCount).add(num);
                        }
                        setCount++;
                    }

                    count++;
                }
                
                prevSudokus.add(prevSudoku);

                sudoku.get(coords[0]).get(coords[1]).remove(pair[0]);
                
                System.out.println("Cannot proceed here, so try removing " + pair[0] + " from cell [" + (coords[1]+1) + "," + (coords[0]+1) + "]");

                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        prevBoards[prevBoardsCount][i][j] = board[i][j];
                    }
                }
                
                prevBoardsCount++;
                
                /*for (int i = 0; i < 9; i++) {
                    if (i%3 == 0) {
                        System.out.println("-----------------------");
                    }

                    for (int j = 0; j < 9; j++) {
                        if (j%3 == 0) {
                            System.out.print("|");
                        }
                        System.out.print(prevBoard[i][j] + " ");
                    }

                    System.out.println();
                }*/

                board[coords[0]][coords[1]] = (char)(pair[1] + '0');
                //initialSolvedCount = solvedCount;
                //initialSolutionsLeft = solutionsLeft;
                initSolvedCounts.add(solvedCount);
                initSolutionsLeft.add(solutionsLeft);
                solvedCount++;
                solutionsLeft--;
                
                System.out.println("Sudoku now:");
                printSudoku(sudoku);
            } 
                 
            prevSolvedCount = solvedCount;
            prevSolutionsLeft = solutionsLeft;
            
            if (Contradiction(board)) {
                /*for (int i = 0; i < 9; i++) {
                    if (i%3 == 0) {
                        System.out.println("-----------------------");
                    }

                    for (int j = 0; j < 9; j++) {
                        if (j%3 == 0) {
                            System.out.print("|");
                        }
                        System.out.print(board[i][j] + " ");
                    }

                    System.out.println();
                }*/
                
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        board[i][j] = prevBoards[prevBoardsCount-1][i][j];
                    }
                }
                
                System.out.println("CONTRADICTION!");
                
                sudoku.clear();
                
                int count = 0;
                
                int s = prevSudokus.size();
                
                for (List<HashSet<Integer>> list : prevSudokus.get(s-1)) {
                    sudoku.add(new ArrayList());
                    int setCount = 0;

                    for (HashSet<Integer> set : list) {
                        sudoku.get(count).add(new HashSet<Integer>());
                        for (int num : set) {
                            sudoku.get(count).get(setCount).add(num);
                        }
                        setCount++;
                    }

                    count++;
                }
                
                solvedCount = initSolvedCounts.get(initSolvedCounts.size()-1);
                prevSolvedCount = solvedCount;
                solutionsLeft = initSolutionsLeft.get(initSolvedCounts.size()-1);
                prevSolutionsLeft = solutionsLeft;
                int S = initCoords.size();
                sudoku.get(initCoords.get(S-1).get(0)).get(initCoords.get(S-1).get(1)).remove(prevPairs[prevPairsCount-1][1]);
                int[] coords = new int[]{initCoords.get(S-1).get(0), initCoords.get(S-1).get(1)};
                
                System.out.println("Now try removing " + prevPairs[prevPairsCount-1][1] + " from cell [" + (coords[1]+1) + "," + (coords[0]+1) + "]");
                board[coords[0]][coords[1]] = (char)(prevPairs[prevPairsCount-1][0] + '0');
                solutionsLeft--;
                solvedCount++;
                
                initCoords.remove(initCoords.size()-1);
                prevSudokus.remove(prevSudokus.size()-1);
                prevBoardsCount--;
                prevPairsCount--;
                initSolvedCounts.remove(initSolvedCounts.size()-1);
                initSolutionsLeft.remove(initSolutionsLeft.size()-1);
                
                System.out.println("Sudoku now:");
                printSudoku(sudoku);              
            }
                
                /*for (int i = 0; i < 9; i++) {
                    if (i%3 == 0) {
                        System.out.println("-----------------------");
                    }

                    for (int j = 0; j < 9; j++) {
                        if (j%3 == 0) {
                            System.out.print("|");
                        }
                        System.out.print(board[i][j] + " ");
                    }

                    System.out.println();
                }*/
                
                /*for (int i = 0; i < 9; i++) {
                    if (i%3 == 0) {
                        System.out.println("-----------------------");
                    }

                    for (int j = 0; j < 9; j++) {
                        if (j%3 == 0) {
                            System.out.print("|");
                        }
                        System.out.print(" [");
                        for (int num : sudoku.get(i).get(j)) {
                            System.out.print(num + ",");
                        }
                        System.out.print("] ");
                    }

                    System.out.println();
                }*/
            
            //System.out.println(solvedCount);
        }
        
        printBoard(board);
    }
    
    private static void checkBox(int x, int y, List<List<HashSet<Integer>>> sudoku, char[][] board) {
        Map<HashSet<Integer>, Integer> map = new HashMap();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                HashSet<Integer> set = sudoku.get(x+i).get(y+j);
                if (map.get(set) != null) {
                    map.put(set, map.get(set) + 1);
                } else {
                    map.put(set, 1);
                }
            }
        }
        
        List<HashSet<Integer>> list = new ArrayList();
        
        for (Map.Entry<HashSet<Integer>, Integer> entry : map.entrySet()) {
            if (entry.getKey().size() == entry.getValue()) {
                list.add(entry.getKey());        
            }
        }
        
        boolean printable = false;
        String sudokuString = sudokuString(sudoku);
        HashSet<HashSet<Integer>> setOfSets = new HashSet();
        
        for (HashSet<Integer> set : list) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    HashSet<Integer> st = sudoku.get(x+i).get(y+j);
                    if (st.size() > 1 && !st.equals(set)) {                        
                        for (int num : set) {
                            if (st.remove(num)) {
                                solutionsLeft--;
                                if (set.size() > 1) {
                                    setOfSets.add(set);
                                    printable = true;
                                }           
                            }
                        }
                                   
                        if (st.size() == 1) {
                            for (int num : st) {
                                board[x+i][y+j] = (char)(num + '0');
                            }                            
                            solvedCount++;
                        }
                    }
                }
            }
        }
        
        if (printable) {
            for (HashSet<Integer> hashset : setOfSets) {
                System.out.print("Naked subset [");
                String prefix = "";

                for (int num : hashset) {
                    System.out.print(prefix);
                    prefix = ",";
                    System.out.print(num);
                }

                System.out.println("] in box [" + (y+1) + "," + (x+1) + "]");
            }
            
            System.out.println("Sudoku Before Changes:");
            System.out.print(sudokuString);
            System.out.println("Sudoku After Changes:");
            printSudoku(sudoku);
        }
    }
    
    private static void checkColumn(int y, List<List<HashSet<Integer>>> sudoku, char[][] board) {
        Map<HashSet<Integer>, Integer> map = new HashMap();
        
        for (int i = 0; i < 9; i++) {
            HashSet<Integer> set = sudoku.get(i).get(y);
            if (map.get(set) != null) {
                map.put(set, map.get(set)+1);
            } else {
                map.put(set, 1);
            }
        }
        
        List<HashSet<Integer>> list = new ArrayList();
        
        for (Map.Entry<HashSet<Integer>, Integer> entry : map.entrySet()) {
            if (entry.getKey().size() == entry.getValue()) {
                list.add(entry.getKey());
            }
        }
        
        boolean printable = false;
        String sudokuString = sudokuString(sudoku);
        HashSet<HashSet<Integer>> setOfSets = new HashSet();
        
        for (HashSet<Integer> set : list) {
            for (int i = 0; i < 9; i++) {
                HashSet<Integer> st = sudoku.get(i).get(y);
                if (st.size() > 1 && !st.equals(set)) {
                    for (int num : set) {
                        if (st.remove(num)) {
                            solutionsLeft--;                     
                            if (set.size() > 1) {
                                setOfSets.add(set);
                                printable = true;
                            }
                        }
                    }

                    if (st.size() == 1) {
                        for (int num : st) {
                            board[i][y] = (char)(num + '0');
                        }
                        solvedCount++;
                    }
                }
            }
        }
        
        if (printable) {
            for (HashSet<Integer> hashset : setOfSets) {
                System.out.print("Naked subset [");
                String prefix = "";

                for (int num : hashset) {
                    System.out.print(prefix);
                    prefix = ",";
                    System.out.print(num);
                }

                System.out.println("] in column " + (y+1));
            }
            System.out.println("Sudoku Before Changes");
            System.out.print(sudokuString);
            System.out.println("Sudoku After Changes");
            printSudoku(sudoku);
        }
    }
    
    private static void checkRow(int x, List<List<HashSet<Integer>>> sudoku, char[][] board) {
        Map<HashSet<Integer>, Integer> map = new HashMap();
        
        for (int i = 0; i < 9; i++) {
            HashSet<Integer> set = sudoku.get(x).get(i);
            if (map.get(set) != null) {
                map.put(set, map.get(set)+1);
            } else {
                map.put(set, 1);
            }
        }
        
        List<HashSet<Integer>> list = new ArrayList();
        
        for (Map.Entry<HashSet<Integer>, Integer> entry : map.entrySet()) {
            if (entry.getKey().size() == entry.getValue()) {
                list.add(entry.getKey());
            }
        }
        
        boolean printable = false;
        String sudokuString = sudokuString(sudoku);
        HashSet<HashSet<Integer>> setOfSets = new HashSet();
        
        for (HashSet<Integer> set : list) {
            for (int i = 0; i < 9; i++) {
                HashSet<Integer> st = sudoku.get(x).get(i);
                if (st.size() > 1 && !st.equals(set)) {
                    for (int num : set) {
                        if (st.remove(num)) {
                            solutionsLeft--;
                            if (set.size() > 1) {
                                printable = true;
                                setOfSets.add(set);
                            }         
                        }
                    }

                    if (st.size() == 1) {
                        for (int num : st) {
                            board[x][i] = (char)(num + '0');
                        }
                        solvedCount++;
                    }
                }
            }
        }
        
        if (printable) {
            for (HashSet<Integer> hashset : setOfSets) {
                System.out.print("Naked subset [");
                String prefix = "";

                for (int num : hashset) {
                    System.out.print(prefix);
                    prefix = ",";
                    System.out.print(num);
                }

                System.out.println("] in row " + (x+1));
            }
            
            System.out.println("Sudoku Before Changes");
            System.out.print(sudokuString);
            System.out.println("Sudoku After Changes");
            printSudoku(sudoku);
        }
    }
    
    private static void checkBox1(int x, int y, List<List<HashSet<Integer>>> sudoku, char[][] board) {
        List<HashSet<Integer>> indices = new ArrayList();
        
        for (int i = 0; i < 10; i++) {
            indices.add(new HashSet<Integer>());
        }
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                HashSet<Integer> set = sudoku.get(x+i).get(y+j);
                for (int num : set) {
                    indices.get(num).add(i*3 + j);
                }
            }
        }
        
        Map<HashSet<Integer>, HashSet<Integer>> map = new HashMap();
        
        for (int i = 1; i < 10; i++) {
            HashSet<Integer> st = indices.get(i);
            if (map.get(st) != null) {
                map.get(st).add(i);
                //map.put(st, map.get(st).add(i));
            } else {
                HashSet<Integer> newSet = new HashSet();
                newSet.add(i);
                map.put(st, newSet);
            }
        }
        
        boolean printable = false;
        HashSet<HashSet<Integer>> setOfSets = new HashSet();
        String sudokuString = sudokuString(sudoku);
        
        for (Map.Entry<HashSet<Integer>, HashSet<Integer>> entry : map.entrySet()) {
            if (entry.getKey().size() == entry.getValue().size()) {
                
                for (int num : entry.getKey()) {
                    int i = num/3;
                    int j = num%3;
                    HashSet<Integer> st = sudoku.get(x+i).get(y+j);
                    
                    if (st.size() == 1) {
                        continue;
                    }
                    
                    for (Iterator<Integer> it = st.iterator(); it.hasNext();) {
                        Integer element = it.next();
                        if (!entry.getValue().contains(element)) {
                            it.remove();
                            solutionsLeft--;
                            if (entry.getValue().size() > 1) {
                                setOfSets.add(entry.getValue());
                                printable = true;
                            }
                        }
                    }
                    
                    if (st.size() == 1) {
                        for (int n : st) {
                            board[x+i][y+j] = (char)(n + '0');
                        }
                        solvedCount++;  
                    }
                }
            }
        }
        
        if (printable) {
            for (HashSet<Integer> hashset : setOfSets) {
                System.out.print("Hidden subset [");
                String prefix = "";

                for (int num : hashset) {
                    System.out.print(prefix);
                    prefix = ",";
                    System.out.print(num);
                }

                System.out.println("] in box [" + (y+1) + "," + (x+1) + "]");
            }
            System.out.println("Sudoku Before Changes:");
            System.out.print(sudokuString);
            System.out.println("Sudoku After Changes:");
            printSudoku(sudoku);
        }
    }
    
    private static void checkColumn1(int y, List<List<HashSet<Integer>>> sudoku, char[][] board) {
        List<HashSet<Integer>> indices = new ArrayList();
        
        for (int i = 0; i < 10; i++) {
            indices.add(new HashSet<Integer>());
        }
        
        for (int i = 0; i < 9; i++) {
            HashSet<Integer> set = sudoku.get(i).get(y);
            for (int num : set) {
                indices.get(num).add(i);
            }
        }
        
        Map<HashSet<Integer>, HashSet<Integer>> map = new HashMap();
        
        for (int i = 1; i < 10; i++) {
            HashSet<Integer> st = indices.get(i);
            if (map.get(st) != null) { 
                map.get(st).add(i);
                //map.put(st, map.get(st).add(i));
            } else {
                HashSet<Integer> newSet = new HashSet();
                newSet.add(i);
                map.put(st, newSet);
            }
        }
        
        boolean printable = false;
        String sudokuString = sudokuString(sudoku);
        HashSet<HashSet<Integer>> setOfSets = new HashSet();
        
        for (Map.Entry<HashSet<Integer>, HashSet<Integer>> entry : map.entrySet()) {
            if (entry.getKey().size() == entry.getValue().size()) {
                          
                for (int num : entry.getKey()) {
                    HashSet<Integer> st = sudoku.get(num).get(y);
                    
                    if (st.size() == 1) {
                        continue;
                    }
                    
                    for (Iterator<Integer> it = st.iterator(); it.hasNext();) {
                        Integer element = it.next();
                        if (!entry.getValue().contains(element)) {
                            it.remove();
                            solutionsLeft--;
                            if (entry.getValue().size() > 1) {
                                printable = true;
                                setOfSets.add(entry.getValue());
                            }
                        }
                    }
                    
                    if (st.size() == 1) {
                        for (int n : st) {
                            board[num][y] = (char)(n + '0');
                        }
                        solvedCount++;  
                    }
                }
            }
        }
        
        if (printable) {
            for (HashSet<Integer> hashset : setOfSets) {
                System.out.print("Hidden subset [");
                String prefix = "";

                for (int num : hashset) {
                    System.out.print(prefix);
                    prefix = ",";
                    System.out.print(num);
                }

                System.out.println("] in column " + (y+1));
            }
            System.out.println("Sudoku Before Changes");
            System.out.print(sudokuString);
            System.out.println("Sudoku After Changes");
            printSudoku(sudoku);
        }
    }
        
    private static void checkRow1(int x, List<List<HashSet<Integer>>> sudoku, char[][] board) {
        List<HashSet<Integer>> indices = new ArrayList();
        
        for (int i = 0; i < 10; i++) {
            indices.add(new HashSet<Integer>());
        }
        
        for (int i = 0; i < 9; i++) {
            HashSet<Integer> set = sudoku.get(x).get(i);
            for (int num : set) {
                indices.get(num).add(i);
            }
        }
        
        Map<HashSet<Integer>, HashSet<Integer>> map = new HashMap();
        
        for (int i = 1; i < 10; i++) {
            HashSet<Integer> st = indices.get(i);
            if (map.get(st) != null) { 
                map.get(st).add(i);
                //map.put(st, map.get(st).add(i));
            } else {
                HashSet<Integer> newSet = new HashSet();
                newSet.add(i);
                map.put(st, newSet);
            }
        }
        
        boolean printable = false;
        String sudokuString = sudokuString(sudoku);
        HashSet<HashSet<Integer>> setOfSets = new HashSet();
        
        for (Map.Entry<HashSet<Integer>, HashSet<Integer>> entry : map.entrySet()) {
            if (entry.getKey().size() == entry.getValue().size()) {
                
                for (int num : entry.getKey()) {
                    HashSet<Integer> st = sudoku.get(x).get(num);
                    
                    if (st.size() == 1) {
                        continue;
                    }
                    
                    for (Iterator<Integer> it = st.iterator(); it.hasNext();) {
                        Integer element = it.next();
                        if (!entry.getValue().contains(element)) {
                            it.remove();
                            solutionsLeft--;
                            if (entry.getValue().size() > 1) {
                                printable = true;
                                setOfSets.add(entry.getValue());
                            }
                        }
                    }
                    
                    if (st.size() == 1) {
                        for (int n : st) {
                            board[x][num] = (char)(n + '0');
                        }
                        solvedCount++;  
                    }
                }
            }
        }
        
        if (printable) {
            for (HashSet<Integer> hashset : setOfSets) {
                System.out.print("Hidden subset [");
                String prefix = "";

                for (int num : hashset) {
                    System.out.print(prefix);
                    prefix = ",";
                    System.out.print(num);
                }

                System.out.println("] in row " + (x+1));
            }
            System.out.println("Sudoku Before Changes");
            System.out.print(sudokuString);
            System.out.println("Sudoku After Changes");
            printSudoku(sudoku);
        }
    }
    
    private static boolean Contradiction(char[][] board) {
        for (int i = 0; i < 9; i++) {
            int[] numCounts = new int[10];
            for (int j = 0; j < 9; j++) {
                int num = (int)(board[i][j] - '0');
                
                if (num < 1) {
                    continue;
                }
                
                numCounts[num]++;
                
                if (numCounts[num] > 1) {
                    return true;
                }   
            }
        }
        
        for (int i = 0; i < 9; i++) {
            int[] numCounts = new int[10];
            for (int j = 0; j < 9; j++) {
                int num = (int)(board[j][i] - '0');
                
                if (num < 1) {
                    continue;
                }
                
                numCounts[num]++;
                if (numCounts[num] > 1) {
                    return true;
                }
                
            }
        }
        
        int x = 0;
        int y = 0;
        
        while (x < 9) {
            while (y < 9) {
                int[] numCounts = new int[10];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int num = (int)(board[x+i][y+j] - '0');
                        
                        if (num < 1) {
                            continue;
                        }
                        
                        numCounts[num]++;
                        if (numCounts[num] > 1) {
                            return true;
                        }
                    }
                }
                y+=3;
            }
            x+=3;
        }
        
        return false;
    }
    
    private void xWingRows(List<List<HashSet<Integer>>> sudoku, char[][] board) {
        List<List<HashSet<Integer>>> indices = new ArrayList();
        
        for (int i = 0; i < 9; i++) {
            indices.add(new ArrayList());
            for (int j = 0; j < 10; j++) {
                indices.get(i).add(new HashSet());
            }
        }
        
        for (int i = 0; i < 9; i++) {
            for (int j = 1; j <= 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if (sudoku.get(k).get(i).contains(j)) {
                        indices.get(i).get(j).add(k);
                    }
                }
            }
        }
        
        for (int i = 1; i <= 9; i++) {
            HashMap<HashSet<Integer>, List<Integer>> map = new HashMap();
            for (int j = 0; j < 9; j++) {
                HashSet<Integer> set = indices.get(j).get(i);
                if (set.size() == 2) {
                    if (map.get(set) == null) {
                        List<Integer> list = new ArrayList();
                        list.add(j);
                        map.put(set, list);
                    } else {
                        map.get(set).add(j);
                    }
                }
            }
        
            for (Map.Entry<HashSet<Integer>, List<Integer>> entry : map.entrySet()) {
                List<Integer> value = entry.getValue();  
                HashSet<Integer> key = entry.getKey();
                for (int k = 0; k < value.size() - 1; k++) {
                    for (int j = value.get(k) + 1; j < value.get(k+1); j++) {
                        for (int num : key) {
                            HashSet<Integer> st = sudoku.get(num).get(j);

                            if (st.size() == 1) {
                                continue;
                            }

                            st.remove(i);

                            if (st.size() == 1) {
                                for (int n : st) {
                                    board[num][j] = (char)(n + '0');
                                    solvedCount++;
                                }      
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void xWingColumns(List<List<HashSet<Integer>>> sudoku, char[][] board) {
        List<List<HashSet<Integer>>> indices = new ArrayList();
        
        for (int i = 0; i < 9; i++) {
            indices.add(new ArrayList());
            for (int j = 0; j < 10; j++) {
                indices.get(i).add(new HashSet());
            }
        }
        
        for (int i = 0; i < 9; i++) {
            for (int j = 1; j <= 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if (sudoku.get(i).get(k).contains(j)) {
                        indices.get(i).get(j).add(k);
                    }
                }
            }
        }
        
        for (int i = 1; i <= 9; i++) {
            HashMap<HashSet<Integer>, List<Integer>> map = new HashMap();
            for (int j = 0; j < 9; j++) {
                HashSet<Integer> set = indices.get(j).get(i);
                if (set.size() == 2) {
                    if (map.get(set) == null) {
                        List<Integer> list = new ArrayList();
                        list.add(j);
                        map.put(set, list);
                    } else {
                        map.get(set).add(j);
                    }
                }
            }
        
        
            for (Map.Entry<HashSet<Integer>, List<Integer>> entry : map.entrySet()) {
                List<Integer> value = entry.getValue();  
                HashSet<Integer> key = entry.getKey();
                for (int k = 0; k < value.size() - 1; k++) {
                    for (int j = value.get(k) + 1; j < value.get(k+1); j++) {
                        for (int num : key) {
                            HashSet<Integer> st = sudoku.get(j).get(num);

                            if (st.size() == 1) {
                                continue;
                            }

                            st.remove(i);

                            if (st.size() == 1) {
                                for (int n : st) {
                                    board[j][num] = (char)(n + '0');
                                    solvedCount++;
                                }      
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void printSudoku(List<List<HashSet<Integer>>> sudoku) {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                System.out.println("-----------------------------------------------------------------------------------------------");
            }
            System.out.println("-----------------------------------------------------------------------------------------------");
            for (int n = 1; n <= 9; n+=3) {
                for (int j = 0; j < 9; j++) {
                    if (j%3 == 0) {
                        System.out.print("|");
                    }
                    System.out.print("|");
                    for (int num = n; num < n+3; num++) {
                        if (sudoku.get(i).get(j).contains(num)) {
                            System.out.print(" " + num + " ");
                        } else {
                            System.out.print("   ");
                        }
                    }
                }
                System.out.println("||");
            }           
        }
        
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------------------------------------");
    }
    
    private static void printBoard(char[][] board) {
        for (int i = 0; i < 9; i++) {
            if (i%3 == 0) {
                System.out.println("-----------------------");
            }

            for (int j = 0; j < 9; j++) {
                if (j%3 == 0) {
                    System.out.print("|");
                }
                System.out.print(board[i][j] + " ");
            }

            System.out.println();
        }
    }
    
    private static String sudokuString(List<List<HashSet<Integer>>> sudoku) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                sb.append("-----------------------------------------------------------------------------------------------\n");
            }
            sb.append("-----------------------------------------------------------------------------------------------\n");
            for (int n = 1; n <= 9; n+=3) {
                for (int j = 0; j < 9; j++) {
                    if (j%3 == 0) {
                        sb.append("|");
                    }
                    sb.append("|");
                    for (int num = n; num < n+3; num++) {
                        if (sudoku.get(i).get(j).contains(num)) {
                            sb.append(" " + num + " ");
                        } else {
                            sb.append("   ");
                        }
                    }
                }
                sb.append("||\n");
            }           
        }
        
        sb.append("-----------------------------------------------------------------------------------------------\n");
        sb.append("-----------------------------------------------------------------------------------------------\n");
        
        return sb.toString();
    }
}