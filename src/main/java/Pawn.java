import java.awt.*;

public class Pawn {
    private Coordinates position;
    private Color color;

    public Pawn(Coordinates position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        if(color == Color.WHITE) {return ConsoleColors.WHITE_BOLD + "O" + ConsoleColors.RESET ;}
        if(color == Color.BLACK) {return ConsoleColors.RED_BOLD + "X" + ConsoleColors.RESET ;}
        else return "";
    }
}
