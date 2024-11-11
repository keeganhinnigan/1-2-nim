package org.keglez;

public class Player {
    private String name;
    private MoveStrategy strategy;
    
    public Player(String name) {
        this.name = name;
        this.strategy = null; // No strategy means it's a human player
    }
    
    public Player(String name, MoveStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public int getMove(int currentPileSize) {
        return strategy.NextMove();
    }
    
    public String getName() {
        return name;
    }

    public MoveStrategy getStrategy() {
        return strategy;
    }
    
    public void setStrategy(MoveStrategy strategy) {
        this.strategy = strategy;
    }
    
}
