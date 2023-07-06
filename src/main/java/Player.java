import java.awt.*;

public class Player {
    private String name;
    private char symbol;
    private Color color;
    private int beatenPaws = 0;
    private int bestCombo = 0;

    public Player(String name, char symbol, Color color) {
        this.name = name;
        this.symbol = symbol;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getBeatenPaws() {
        return beatenPaws;
    }

    public void setBeatenPaws(int beatenPaws) {
        this.beatenPaws = beatenPaws;
    }

    public int getBestCombo() {
        return bestCombo;
    }

    public void setBestCombo(int bestCombo) {
        if(bestCombo > this.bestCombo) {
            this.bestCombo = bestCombo;
        }
    }

    @Override
    public String toString() {
        if(color == Color.WHITE) return ConsoleColors.WHITE_BOLD + name + ConsoleColors.RESET;
        if(color == Color.BLACK) return ConsoleColors.RED_BOLD + name + ConsoleColors.RESET;
        else return "";
    }
}
