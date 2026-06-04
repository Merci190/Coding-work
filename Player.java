public class Player {
    /**
 * Player.java
 * 
 * Represents a single player in the Chomp game.
 * Tracks cumulative player statistics across multiple games.
 * 
 * @author Student
 * @version 1.0
 */

    private String name;
    private int wins;
    private int losses;
    
    /**
     * Constructor for Player
     * @param name The name of the player
     */
    public Player(String name) {
        this.name = name;
        this.wins = 0;
        this.losses = 0;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public void setName(String name) { this.name = name; }
    public void setWins(int wins) { this.wins = wins; }
    public void setLosses(int losses) { this.losses = losses; }
    
    /**
     * Increments the player's win count by 1
     */
    public void addWin() { this.wins++; }
    
    /**
     * Increments the player's loss count by 1
     */
    public void addLoss() { this.losses++; }
    
    /**
     * Displays the cumulative statistics for the player.
     * Rubric requirement: Must include name and win/loss record.
     */
    public void print() {
        System.out.println("================================");
        System.out.println("       PLAYER STATISTICS");
        System.out.println("================================");
        System.out.println("Name:         " + name);
        System.out.println("Wins:         " + wins);
        System.out.println("Losses:       " + losses);
        System.out.println("Total Games:  " + (wins + losses));
        if (wins + losses > 0) {
            double winRate = (double) wins / (wins + losses) * 100;
            System.out.printf("Win Rate:     %.1f%%\n", winRate);
        } else {
            System.out.println("Win Rate:     N/A");
        }
        System.out.println("================================");
    }
}

