import java.util.Scanner;
import java.util.ArrayList;

/**
 * Game.java
 * 
 * Main controller for the Chomp game.
 * Contains the main method, instantiates players and the board, and manages game flow.
 * 
 * @author Student
 * @version 1.0
 */
public class Game {
    private Player player1;
    private Player player2;
    private Board board;
    private Scanner scanner;
    
    /**
     * Constructor for Game
     */
    public Game() {
        scanner = new Scanner(System.in);
    }
    
    /**
     * Main method - entry point of the program
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.displayWelcome();
        game.initializePlayers();
        game.runMainMenu();
    }
    
    /**
     * Displays welcome message and game rules
     */
    private void displayWelcome() {
        System.out.println();
        System.out.println("    ========================================");
        System.out.println("    |         WELCOME TO CHOMP!            |");
        System.out.println("    ========================================");
        System.out.println();
        System.out.println("    RULES:");
        System.out.println("    - Two players take turns selecting squares on a grid.");
        System.out.println("    - Selecting a square removes it AND all squares");
        System.out.println("      below it and to its right.");
        System.out.println("    - The top-left square [X] is POISONED.");
        System.out.println("    - Whoever is forced to take the poisoned square LOSES!");
        System.out.println();
    }
    
    /**
     * Initializes the two player objects
     */
    private void initializePlayers() {
        System.out.print("    Enter Player 1's name: ");
        String name1 = getNonEmptyInput();
        player1 = new Player(name1);
        
        System.out.print("    Enter Player 2's name: ");
        String name2 = getNonEmptyInput();
        player2 = new Player(name2);
        
        System.out.println("\n    " + player1.getName() + " vs " + player2.getName() + "\n");
    }
    
    /**
     * Displays the main menu options.
     * Rubric requirement: Must be in Game class.
     */
    private void runMainMenu() {
        while (true) {
            System.out.println();
            System.out.println("    ========================================");
            System.out.println("    |             MAIN MENU                |");
            System.out.println("    ========================================");
            System.out.println("    |  1. Start a new game                |");
            System.out.println("    |  2. View player statistics          |");
            System.out.println("    |  3. Exit program                    |");
            System.out.println("    ========================================");
            System.out.print("    Enter your choice: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    startNewGame();
                    break;
                case 2:
                    viewPlayerStats();
                    break;
                case 3:
                    exitProgram();
                    return;
                default:
                    System.out.println("    Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }
    
    /**
     * Sets up and starts a new game
     */
    private void startNewGame() {
        System.out.println("\n    --- New Game Setup ---");
        System.out.print("    Enter number of rows (1-10, press Enter for 5): ");
        int rows = getValidRange(1, 10, 5);
        System.out.print("    Enter number of columns (1-10, press Enter for 5): ");
        int cols = getValidRange(1, 10, 5);
        
        if (board == null) {
            board = new Board(rows, cols);
        } else {
            board.reset(rows, cols);
        }
        
        playGame();
    }
    
    /**
     * Displays the current status of the game including board stats and move history.
     * Rubric requirement: Must be in Game class.
     */
    public void displayGameStatus() {
        System.out.println();
        System.out.println("    GAME STATUS");
        System.out.println("    ==========================");
        System.out.println("    Board Size:        " + board.getRows() + " x " + board.getCols());
        System.out.println("    Remaining Squares: " + board.getRemainingSquares());
        System.out.println("    Moves Made:        " + board.getMoveHistory().size());
        System.out.println();
        System.out.println("    PLAYER RECORDS:");
        System.out.println("    " + player1.getName() + " - W:" + player1.getWins() + " L:" + player1.getLosses());
        System.out.println("    " + player2.getName() + " - W:" + player2.getWins() + " L:" + player2.getLosses());
        
        if (!board.getMoveHistory().isEmpty()) {
            System.out.println();
            System.out.println("    MOVE HISTORY:");
            ArrayList<String> history = board.getMoveHistory();
            for (int i = 0; i < history.size(); i++) {
                System.out.println("    " + (i + 1) + ". " + history.get(i));
            }
        }
        System.out.println("    ==========================\n");
    }
    
    /**
     * Main game loop - handles alternating turns until game ends
     */
    private void playGame() {
        Player currentPlayer = player1;
        boolean gameOver = false;
        Player loser = null;
        
        System.out.println("\n    >>> " + player1.getName() + " goes first! <<<");
        
        while (!gameOver) {
            board.print();
            
            // Pass 'this' (the Game object) so Turn can trigger displayGameStatus()
            Turn turn = new Turn(currentPlayer, board, scanner, this);
            int result = turn.executeTurn();
            
            if (result == 1) {
                // Player took the poisoned square directly
                loser = currentPlayer;
                gameOver = true;
            } else if (result == 2) {
                // Player conceded the game
                System.out.println("\n    " + currentPlayer.getName() + " has conceded the game!");
                loser = currentPlayer;
                gameOver = true;
            } 
            // result == 0 means normal move occurred
            else if (board.isGameOver()) {
                // Only poisoned square remains - the NEXT player would be forced to take it and lose
                loser = (currentPlayer == player1) ? player2 : player1;
                gameOver = true;
            } else {
                // Normal move, switch to other player
                currentPlayer = (currentPlayer == player1) ? player2 : player1;
            }
        }
        
        // Determine winner and update cumulative statistics
        Player winner = (loser == player1) ? player2 : player1;
        winner.addWin();
        loser.addLoss();
        
        displayGameResults(winner, loser);
    }
    
    /**
     * Displays the end-of-game results and final board
     * @param winner The player who won
     * @param loser The player who lost
     */
    private void displayGameResults(Player winner, Player loser) {
        System.out.println();
        System.out.println("    ========================================");
        System.out.println("    |           GAME OVER!                 |");
        System.out.println("    ========================================");
        System.out.println("    |  WINNER: " + formatName(winner.getName()) + " |");
        System.out.println("    |  LOSER:  " + formatName(loser.getName()) + " |");
        System.out.println("    ========================================");
        
        System.out.println("\n    Final Board:");
        board.print();
        
        System.out.println("    Updated Player Statistics:");
        player1.print();
        System.out.println();
        player2.print();
    }
    
    /**
     * Displays statistics for both players
     */
    private void viewPlayerStats() {
        System.out.println("\n    --- Player Statistics ---\n");
        player1.print();
        System.out.println();
        player2.print();
    }
    
    /**
     * Exits the program gracefully
     */
    private void exitProgram() {
        System.out.println("\n    Thanks for playing Chomp!");
        System.out.println("    Goodbye!\n");
        scanner.close();
    }
    
    /**
     * Helper to format names nicely in the results box
     */
    private String formatName(String name) {
        if (name.length() > 24) return name.substring(0, 24);
        return String.format("%-24s", name);
    }
    
    /**
     * Helper to get integer input within a specific range, with a default fallback
     */
    private int getValidRange(int min, int max, int defaultVal) {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return defaultVal;
        try {
            int val = Integer.parseInt(input);
            if (val >= min && val <= max) return val;
            System.out.println("    Out of range. Using default: " + defaultVal);
            return defaultVal;
        } catch (NumberFormatException e) {
            System.out.println("    Invalid input. Using default: " + defaultVal);
            return defaultVal;
        }
    }
    
    /**
     * Helper to ensure a user doesn't submit an empty name
     */
    private String getNonEmptyInput() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.print("    Name cannot be empty. Please try again: ");
        }
    }
    
    /**
     * Helper to safely parse integers without throwing exceptions
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

