import java.util.ArrayList;

/**
 * Board.java
 * 
 * Represents the game "board" for Chomp.
 * Manages the grid state, move history, and turn menu options.
 * 
 * @author Student
 * @version 1.0
 */
public class Board {
    private boolean[][] grid;  // true = square exists, false = chomped
    private int rows;
    private int cols;
    private ArrayList<String> moveHistory;  // Tracks all moves
    
    /**
     * Constructor for Board
     * @param rows Number of rows on the board
     * @param cols Number of columns on the board
     */
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new boolean[rows][cols];
        this.moveHistory = new ArrayList<>();
        initializeBoard();
    }
    
    // Initialize all squares to available (true)
    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = true;
            }
        }
    }
    
    /**
     * Prints the visual representation of the board
     */
    public void print() {
        System.out.println();
        System.out.println("          CHOMP BOARD");
        System.out.println("    ==========================");
        
        // Print column headers
        System.out.print("    ");
        for (int j = 0; j < cols; j++) {
            System.out.printf(" %2d ", j + 1);
        }
        System.out.println();
        System.out.println("    --------------------------");
        
        // Print board rows
        for (int i = 0; i < rows; i++) {
            System.out.printf(" %2d ", i + 1);
            System.out.print("|");
            for (int j = 0; j < cols; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("[X] ");  // Poisoned square
                } else if (grid[i][j]) {
                    System.out.print("[O] ");  // Available square
                } else {
                    System.out.print("    ");  // Chomped square
                }
            }
            System.out.println("|");
        }
        System.out.println("    ==========================");
        System.out.println("    [O] = Available  [X] = Poisoned");
        System.out.println();
    }
    
    /**
     * Displays the menu of turn options.
     * Rubric requirement: Must be in Board class.
     */
    public void displayTurnMenu() {
        System.out.println();
        System.out.println("    TURN OPTIONS");
        System.out.println("    -------------");
        System.out.println("    1. Take a turn (chomp a square)");
        System.out.println("    2. View game status");
        System.out.println("    3. Concede the game");
        System.out.println("    -------------");
        System.out.print("    Enter your choice: ");
    }
    
    /**
     * Checks if a selected move is valid (within bounds and not already chomped)
     * @param row Row index (0-based)
     * @param col Column index (0-based)
     * @return true if the move is valid
     */
    public boolean isValidMove(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        return grid[row][col];
    }
    
    /**
     * Executes a move on the board, removing the square and all below/right
     * @param row Row index (0-based)
     * @param col Column index (0-based)
     * @param playerName Name of player making the move
     * @return true if the poisoned square (0,0) was taken
     */
    public boolean makeMove(int row, int col, String playerName) {
        // Record the move in the ArrayList
        String move = String.format("%s chomped (%d,%d)", playerName, row + 1, col + 1);
        moveHistory.add(move);
        
        // Chomp logic: remove selected and all below/to the right
        for (int i = row; i < rows; i++) {
            for (int j = col; j < cols; j++) {
                grid[i][j] = false;
            }
        }
        
        return (row == 0 && col == 0);
    }
    
    /**
     * Checks if the game is over (only the poisoned square remains)
     * @return true if game is over
     */
    public boolean isGameOver() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 && j == 0) continue;  // Skip poisoned square
                if (grid[i][j]) return false;
            }
        }
        return true;
    }
    
    /**
     * Counts remaining squares on the board
     * @return Number of remaining squares
     */
    public int getRemainingSquares() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j]) count++;
            }
        }
        return count;
    }
    
    // Getters
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public ArrayList<String> getMoveHistory() { return moveHistory; }
    
    /**
     * Resets the board for a new game
     * @param rows New number of rows
     * @param cols New number of columns
     */
    public void reset(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new boolean[rows][cols];
        this.moveHistory = new ArrayList<>();
        initializeBoard();
    }
}

