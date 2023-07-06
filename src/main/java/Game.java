import java.awt.*;
import java.util.Scanner;

public class Game {

    public void start() {
        Scanner scanner = new Scanner(System.in);

        printWelcomeMessage();

        System.out.println("Please provide board size [10-20]:");
        int fieldSize = scanner.nextInt();
        scanner.nextLine();

        Board board = new Board(fieldSize);
        Player[] players = getPlayers(scanner);

        board.printBoard(players);

        int playerIndex = 0;
        int countCombo = 0;

        while (true) {
            Player currentPlayer = players[playerIndex];
            System.out.println(currentPlayer.toString() + " please provide coordinates for move:");

            String userInput = getUserInput(scanner);

            if (userInput.equals("pass")) {
                players[playerIndex].setBestCombo(countCombo);
                board.printBoard(players);
                countCombo = 0;
                playerIndex ^= 1;
                continue;
            }

            Coordinates[] coordinates = Coordinates.getCoordinatesFromInput(userInput);
            boolean secondMove = board.movePawn(coordinates, currentPlayer);

            if (board.hasPlayerWon(currentPlayer.getColor())) {
                board.printBoard(players);
                System.out.println("Player " + currentPlayer + " has won the game!");
                break;
            }

            board.printBoard(players);

            if (secondMove) {
                System.out.println(currentPlayer + " you have another move!");
                countCombo++;
            } else {
                players[playerIndex].setBestCombo(countCombo);
                countCombo = 0;
                playerIndex ^= 1;
            }
        }
    }

    //TODO
    public void singleTurn() {
    }

    public String getUserInput(Scanner scanner) {
        String userInput = scanner.nextLine().toUpperCase();

        if (userInput.equals("Q")) {
            System.out.println("Bye!");
            System.exit(0);
        }

        if (userInput.equals("PASS")) {
            return "pass";
        }

        while (!isUserInputValid(userInput)) {
            userInput = scanner.nextLine().toUpperCase();
        }

        return userInput;
    }

    public boolean isUserInputValid(String userInput) {

        if (userInput.length() < 5 || userInput.length() > 7) {
            System.out.println("Incorrect input length!");
            return false;
        }

        if (!userInput.contains(" ")) {
            System.out.println("Input should be divided with space!");
            return false;
        }

        String[] inputDivided = userInput.split(" ");
        String firstCoordinate = inputDivided[0];
        String secondCoordinate = inputDivided[1];

        if (!isCoordinateValid(firstCoordinate)) return false;
        if (!isCoordinateValid(secondCoordinate)) return false;

        return true;
    }

    public boolean isCoordinateValid(String coordinate) {
        if (!Character.isLetter(coordinate.charAt(0))) {
            System.out.println("First character should be a letter!");
            return false;
        }
        if (!Character.isDigit(coordinate.charAt(1))) {
            System.out.println("Second character should be a number!");
            return false;
        }
        if (coordinate.length() > 2 && !Character.isDigit(coordinate.charAt(2))) {
            System.out.println("Third character should be a number!");
            return false;
        }

        return true;
    }

    public Player[] getPlayers(Scanner scanner) {
        Player[] players = new Player[2];

        System.out.println("Please provide Player 1 name:");
        String player1Name = scanner.nextLine();

        System.out.println("Please provide Player 2 name:");
        String player2Name = scanner.nextLine();

        players[0] = new Player(player1Name, 'O', Color.WHITE);
        players[1] = new Player(player2Name, 'X', Color.BLACK);

        return players;
    }

    public void printWelcomeMessage() {
        System.out.println();
        System.out.println(ConsoleColors.CYAN_BOLD + "Welcome to the Draughts game!" + ConsoleColors.RESET);
        System.out.println();
        System.out.println("----------------------------------------------------------------");
    }
}
