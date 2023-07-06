public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Coordinates[] getCoordinatesFromInput(String userInput) {
        String[] inputDivided = userInput.split(" ");
        Coordinates[] coordinates = new Coordinates[2];

        for (int i = 0; i < inputDivided.length; i++) {
            int x = inputDivided[i].charAt(0) - 65;
            int y;

            if(inputDivided[i].length() > 2) {
                String yStr = "" + inputDivided[i].charAt(1) + inputDivided[i].charAt(2);
                y = Integer.parseInt(yStr) - 1;
            }
            else {
                y = Character.getNumericValue(inputDivided[i].charAt(1)) - 1;
            }

            coordinates[i] = new Coordinates(x, y);
        }

        return coordinates;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
