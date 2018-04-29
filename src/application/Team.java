package application;

public class Team {
    int seed = 0; 
    String name = "";
    int score = 0;
    boolean isEliminated = false;
    
    public Team(int seed, String name, int score) {
        this.seed = seed;
        this.name = name;
        this.score = score; 
    }
    
    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isEliminated() {
        return isEliminated;
    }

    public void setEliminated(boolean isEliminated) {
        this.isEliminated = isEliminated;
    }

    
}