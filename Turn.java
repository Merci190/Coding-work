import java.util.Scanner;

/**
 * Turn.java
 * 
 * Controls what happens during a single player's turn.
 * Handles user inputs for moves, viewing status, and conceding.
 * 
 * @author Student
 * @version 1.0
 */
public class Turn {
    private Player currentPlayer;
    private Board board;
    private Game game; // Reference to Game to call status method
    private Scanner scanner;
    
    /**
     * Constructor for Turn
     * @param currentPlayer The player whose turn it is
     * @param board The current game board
     * @param scanner The scanner for user input
     * @param game The main game controller
     */
    public Turn(Player currentPlayer, Board board, Scanner scanner, Game game) {
        this.currentPlayer = currentPlayer;
        this.board = board;
        this.scanner = scanner;
        this.game = game;
    }
    
    /**
     * Executes the turn loop, returning a status code when the turn resolves.
     * @return 0 = normal move, 1 = poisoned taken, 2 = conceded
     */
    public int executeTurn() {
        while (true) {
            board.displayTurnMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    return takeTurn();
                case 2:
                    game.displayGameStatus(); // Calls Game class method
                    break;
                case 3:
                    int concedeResult = handleConcede();
                    if (concedeResult == 2) return 2; // Only return if actually conceded
                    break;
                default:
                    System.out.println("    Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }
    
    /**
     * Handles the logic of selecting and validating a square to chomp
     * @return 0 = normal move, 1 = poisoned taken
     */
    private int takeTurn() {
        System.out.println();
        System.out.println("    " + currentPlayer.getName() + "'s turn to chomp!");
        System.out.println("    Select a square to remove it and all squares");
        System.out.println("    below it and to its right.");
        System.out.println();
        
        while (true) {
            System.out.print("    Enter row (1-" + board.getRows() + "): ");
            int row = getIntInput() - 1;
            System.out.print("    Enter column (1-" + board.getCols() + "): ");
            int col = getIntInput() - 1;
            System.out.println();
            
            if (!board.isValidMove(row, col)) {
                System.out.println("    Invalid move! Square is out of bounds or already removed.");
                System.out.println("    Please try again.\n");
                continue;
            }
            
            if (row == 0 && col == 0) {
                System.out.println("    *** " + currentPlayer.getName() + " took the POISONED square! ***");
                board.makeMove(row, col, currentPlayer.getName());
                return 1;
            }
            
            board.makeMove(row, col, currentPlayer.getName());
            System.out.println("    " + currentPlayer.getName() + " chomped square (" + (row + 1) + ", " + (col + 1) + ")!");
            return 0;
        }
    }
    
    /**
     * Prompts the user to confirm if they want to concede
     * @return 2 if conceded, -1 if cancelled
     */
    private int handleConcede() {
        System.out.println();
        System.out.print("    Are you sure you want to concede? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("y") || confirm.equals("yes")) {
            return 2;
        }
        System.out.println("    Concede cancelled.");
        return -1;
    }
    
    /**
     * Helper method to safely parse integer inputs without crashing
     * @return Valid integer from user
     */
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("    Invalid input. Please enter a number: ");
            }
        }
    }
}

