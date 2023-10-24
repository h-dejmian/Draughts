import java.awt.*;

public class Board {
    private Pawn[][] fields;
    private final int SIZE;
    public static String message = "";
    public static final String[] instructions = {"Format your coordinates as follows:",
            "Pawn you pick + SPACE + destination",
            "For example: B2 C3"};


    public Board(int size) {
        SIZE = size;
        this.fields = getStartingBoard();
    }

    public void removePawn(Pawn pawn) {
        Coordinates pawnPosition = pawn.getPosition();
        int pawnX = pawnPosition.getX();
        int pawnY = pawnPosition.getY();

        fields[pawnX][pawnY] = null;
    }

    //Returns true if pawn was beaten and second move can be made
    public boolean movePawn(Coordinates[] userInput, Player player) {
        Pawn pickedPawn = getPawn(userInput[0]);
        Coordinates pickedPawnPosition = userInput[0];
        Coordinates newPosition = userInput[1];

        Pawn beatenPawn = getBeatenPawn(pickedPawnPosition, newPosition);
        boolean isPawnBeaten = beatenPawn != null;

        if (!isMoveValid(pickedPawn, userInput[1], player, isPawnBeaten)) {
            return false;
        }

        if (beatenPawn != null) {
            player.setBeatenPaws(player.getBeatenPaws() + 1);

            if (pickedPawn.getColor() == beatenPawn.getColor()) {
                message = "Invalid move!";
                return false;
            }
            removePawn(beatenPawn);
        }
        removePawn(pickedPawn);

        int newX = newPosition.getX();
        int newY = newPosition.getY();

        fields[newX][newY] = pickedPawn;
        pickedPawn.setPosition(userInput[1]);

        return isPawnBeaten;
    }

    public boolean isMoveValid(Pawn pawn, Coordinates move, Player player, boolean isPawnBeaten) {
        if (pawn == null) {
            message = "No pawn to move!";
            return false;
        }
        if (pawn.getColor() != player.getColor()) {
            message = "Not your pawn!";
            return false;
        }

        Coordinates pawnPosition = pawn.getPosition();
        Direction direction = getMoveDirection(pawnPosition, move);

        int moveX = move.getX();
        int moveY = move.getY();
        int pawnX = pawnPosition.getX();
        int pawnY = pawnPosition.getY();

        if (!isMoveDirectionCorrect(player, direction)) {
            message = "Incorrect direction!";
            return false;
        }
        if (moveX >= fields.length || moveY >= fields.length || moveX < 0 || moveY < 0) {
            message = "Coordinates out of bound!";
            return false;
        }
        if (fields[moveX][moveY] != null) {
            message = "No pawn to pick on this field!";
            return false;
        }
        if (!(pawnX != moveX && pawnY != moveY)) {
            message = "Only diagonal move allowed!";
            return false;
        }
        if (!isDiagonalMoveCorrect(pawnPosition, move, isPawnBeaten)) {
            message = "Only diagonal move allowed!";
            return false;
        }

        return true;
    }

    public boolean isDiagonalMoveCorrect(Coordinates pawnPosition, Coordinates move, boolean isPawnBeaten) {
        int pawnToMoveDifferenceX = Math.abs(move.getX() - pawnPosition.getX());
        int pawnToMoveDifferenceY = Math.abs(move.getY() - pawnPosition.getY());

        if (pawnToMoveDifferenceX != pawnToMoveDifferenceY) return false;
        if (pawnToMoveDifferenceX > 2 || pawnToMoveDifferenceY > 2) return false;
        if (pawnToMoveDifferenceX == 2 && !isPawnBeaten) return false;
        if (pawnToMoveDifferenceY == 2 && !isPawnBeaten) return false;

        return true;
    }

    public boolean isMoveDirectionCorrect(Player player, Direction direction) {
        if (player.getColor() == Color.WHITE && direction == Direction.UPPER_LEFT || direction == Direction.UPPER_RIGHT) {
            return true;
        }
        if (player.getColor() == Color.BLACK && direction == Direction.BOTTOM_LEFT || direction == Direction.BOTTOM_RIGHT) {
            return true;
        }
        return false;
    }

    public boolean hasPlayerWon(Color playerColor) {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[0].length; j++) {
                if (fields[i][j] != null && fields[i][j].getColor() != playerColor) {
                    return false;
                }
            }
        }
        return true;
    }

    public Pawn getBeatenPawn(Coordinates coordinate1, Coordinates coordinate2) {
        Direction moveDirection = getMoveDirection(coordinate1, coordinate2);

        if (moveDirection == Direction.UPPER_LEFT) {
            return fields[coordinate1.getX() - 1][coordinate1.getY() - 1];
        }
        if (moveDirection == Direction.UPPER_RIGHT) {
            return fields[coordinate1.getX() - 1][coordinate1.getY() + 1];
        }
        if (moveDirection == Direction.BOTTOM_LEFT) {
            return fields[coordinate1.getX() + 1][coordinate1.getY() - 1];
        }
        if (moveDirection == Direction.BOTTOM_RIGHT) {
            return fields[coordinate1.getX() + 1][coordinate1.getY() + 1];
        }
        return null;
    }

    public Direction getMoveDirection(Coordinates coordinate1, Coordinates coordinate2) {
        boolean upperDirection = coordinate1.getX() - coordinate2.getX() >= 1;
        boolean leftDirection = coordinate1.getY() - coordinate2.getY() >= 1;

        if (upperDirection && leftDirection) return Direction.UPPER_LEFT;
        if (upperDirection && !leftDirection) return Direction.UPPER_RIGHT;
        if (!upperDirection && leftDirection) return Direction.BOTTOM_LEFT;
        if (!upperDirection && !leftDirection) return Direction.BOTTOM_RIGHT;

        return null;
    }

    public Pawn getPawn(Coordinates coordinates) {
        return fields[coordinates.getX()][coordinates.getY()];
    }

    public Pawn[][] getStartingBoard() {
        Pawn[][] board = new Pawn[SIZE][SIZE];
        int placeOnOddFactor = 1;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (i < 4 && j % 2 == placeOnOddFactor) {
                    board[i][j] = new Pawn(new Coordinates(i, j), Color.BLACK);
                }
                if (i > 5 && j % 2 == placeOnOddFactor) {
                    board[i][j] = new Pawn(new Coordinates(i, j), Color.WHITE);
                }
            }
            placeOnOddFactor ^= 1;
        }

        return board;
    }

    public void printBoard(Player[] players) {
        for (int i = 0; i < 50; i++) System.out.println();

        String[] backgrounds = {ConsoleColors.WHITE_BACKGROUND, ConsoleColors.BLACK_BACKGROUND};
        int backgroundIndex = 0;

        char letter = 'A';
        System.out.println("   " + ConsoleColors.BLUE + " 1  2  3  4  5  6  7  8  9  10" + ConsoleColors.RESET);

        for (int i = 0; i < fields.length; i++) {

            if (i > 9) System.out.print(ConsoleColors.YELLOW_BOLD + letter + "| " + ConsoleColors.RESET);
            else System.out.print(ConsoleColors.YELLOW_BOLD + letter + "| " + ConsoleColors.RESET);

            for (int j = 0; j < fields[0].length; j++) {
                if (fields[i][j] == null) System.out.print(backgrounds[backgroundIndex] + "   " + ConsoleColors.RESET);
                else
                    System.out.print(backgrounds[backgroundIndex] + " " + fields[i][j] + backgrounds[backgroundIndex] + " " + ConsoleColors.RESET);
                backgroundIndex ^= 1;
            }
            backgroundIndex ^= 1;

            if (i > 2 && i < 6) System.out.print("   " + instructions[i - 3]);
            letter++;
            System.out.println();
        }
        System.out.println(players[0] + " beaten paws: " + players[0].getBeatenPaws() + " |" +
                " best combo: " + players[0].getBestCombo());
        System.out.println(players[1] + " beaten paws: " + players[1].getBeatenPaws() + " |" +
                " best combo: " + players[1].getBestCombo());
        System.out.println();

        if (!message.equals("")) {
            System.out.println(message);
            message = "";
        }
    }
}
