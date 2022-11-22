import java.util.Scanner;

public class Main {

    public static int ROWS = 6, COLUMNS = 7;
    public static char EMPTY_INDICATOR = '-', FIRST_PLAYER = 'O', SECOND_PLAYER = 'X';

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31;1m";
    public static final String ANSI_YELLOW = "\u001B[93;1m";
    public static final String ANSI_BACK_WHITE = "\u001b[47m";


    public static void main(String[] args) {
        // Init board
        char[][] board = new char[ROWS][COLUMNS];
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                board[i][j] = EMPTY_INDICATOR;

        Scanner myObj = new Scanner(System.in);

        // Getting the player names
        System.out.println("Name of first player: ");
        String firstPlayerName = myObj.nextLine();
        System.out.println("Name of second player: ");
        String secondPlayerName = myObj.nextLine();

        // Store information about the previous player's turn
        int prevPos = -1, prevHeight = -1;

        char player = FIRST_PLAYER;
        while (true) {
            display(board, prevPos, prevHeight);

            String playerName = player == FIRST_PLAYER ? firstPlayerName : secondPlayerName;
            String color = player == FIRST_PLAYER ? ANSI_RED : ANSI_YELLOW;
            System.out.println("(" + color + player + ANSI_RESET + ") " + playerName + "'s turn, pick a position (1 to 7): ");
            String input = myObj.nextLine();

            if (!isNumeric(input)) {
                System.out.println("Integer value needed!");
                continue;
            }

            // Subtract 1 since index starts at 0 instead of 1
            int position = Integer.parseInt(input) - 1;
            if (!isPositionWithinRange(position)) {
                System.out.println("Please choose a range between 1 to 7");
                continue;
            }

            int positionHeight = getPositionHeight(board, position);
            if (positionHeight == -1) {
                System.out.println("Please choose a column with an empty slot");
                continue;
            }

            // Update the board
            board[positionHeight][position] = player;

            // Store placement data
            prevPos = position;
            prevHeight = positionHeight;

            if (isPlayerWon(board, player)) {
                display(board, prevPos, prevHeight);
                System.out.println("Game Over: " + playerName + " Wins!");
                break;
            }

            if (isGameTied(board)) {
                display(board, prevPos, prevHeight);
                System.out.println("Game Over: Tied Game!");
                break;
            }

            // Swap player's turn
            player = player == FIRST_PLAYER ? SECOND_PLAYER : FIRST_PLAYER;
        }
    }

    // Note: Could also check only the top of the board for more efficiency
    // Return: If all rows and columns are filled
    public static boolean isGameTied(char[][] board) {
        for (char[] i : board)
            for (char j : i)
                if (j == EMPTY_INDICATOR)
                    return false;
        return true;
    }

    // Note: The checking algorithms are not the most efficient, but easiest to understand
    // Condition for winning: 4 connections in a row
    // Check 1: Vertical
    // Check 2: Horizontal
    // Check 3 - 6: Diagonal directions
    public static boolean isPlayerWon(char[][] board, char player) {
        int connections = 0; // Used to keep track of how many connects in a row

        // Check 1: Vertical
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (connections >= 4)
                    return true;
                if (board[j][i] == player)
                    connections++;
                else
                    connections = 0;
            }
            connections = 0;
        }

        // Check 2: Horizontal
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (connections >= 4)
                    return true;
                if (board[i][j] == player)
                    connections++;
                else
                    connections = 0;
            }
            connections = 0;
        }


        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                // Check 3: Diagonal to the up right
                if (getDiagonalConnections(board, 0, i, j, 1, 1, player) >= 4)
                    return true;
                // Check 4: Diagonal to the down right
                if (getDiagonalConnections(board, 0, i, j, 1, -1, player) >= 4)
                    return true;
                // Check 5: Diagonal to the up left
                if (getDiagonalConnections(board, 0, i, j, -1, 1, player) >= 4)
                    return true;
                // Check 6: Diagonal to the down left
                if (getDiagonalConnections(board, 0, i, j, -1, -1, player) >= 4)
                    return true;
            }
        }

        return false;
    }

    // This function is used to determine the number of connections in a row
    // using recursion.
    public static int getDiagonalConnections(char[][] board, int currentCount, int currentXIndex,
                                             int currentYIndex, int xChange, int yChange, char player) {
        int xIndex = currentXIndex - 1, yIndex = currentYIndex - 1;
        if (currentXIndex <= 0 || currentYIndex <= 0 || xIndex >= ROWS || yIndex >= COLUMNS)
            return currentCount;

        if (board[xIndex][yIndex] != player)
            return currentCount;

        int nextXIndex = currentXIndex + xChange;
        int nextYIndex = currentYIndex + yChange;
        return getDiagonalConnections(board, currentCount + 1, nextXIndex, nextYIndex, xChange, yChange, player);
    }

    // Return: If string is a numeric string
    public static boolean isNumeric(String string) {
        if (string == null || string.equals("")) {
            return false;
        }

        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    // Returns: if the player's desired location is valid.
    // Valid when: pos is within the range 0 - 6, inclusive and space is remaining in the column pos
    public static boolean isPositionWithinRange(int pos) {
        return pos >= 0 && pos < COLUMNS;
    }

    // PreCheck: Position is valid
    // Returns: the height of where to place a piece on the board
    public static int getPositionHeight(char[][] board, int pos) {
        for (int height = 0; height < ROWS; height++) {
            if (board[height][pos] == EMPTY_INDICATOR)
                return height;
        }

        return -1;
    }

    public static void display(char[][] board, int prevPos, int prevHeight) {
        // Displaying the board
        System.out.println(ANSI_BLUE + "-----------------" + ANSI_RESET);
        for (int i = board.length - 1; i >= 0; i--) {
            System.out.print(ANSI_BLUE + "| " + ANSI_RESET);
            for (int j = 0; j < board[0].length; j++) {
                String color = board[i][j] == FIRST_PLAYER ? ANSI_RED
                        : board[i][j] == SECOND_PLAYER ? ANSI_YELLOW
                        : ANSI_RESET;

                // If condition valid: Print with background color to indicate previous placement
                if (prevPos != -1 && prevHeight != -1 && prevPos == j && prevHeight == i)
                    System.out.print(ANSI_BACK_WHITE + color + board[i][j] + ANSI_RESET + " ");
                else
                    System.out.print(color + board[i][j] + ANSI_RESET + " ");
            }
            System.out.print(ANSI_BLUE + "|" + ANSI_RESET);
            System.out.println();
        }
        System.out.println(ANSI_BLUE + "-----------------" + ANSI_RESET);

        // Displaying rows
        System.out.print(ANSI_BLUE + "| " + ANSI_RESET);
        for (int i = 1; i <= COLUMNS; i++)
            System.out.print(i + " ");
        System.out.print(ANSI_BLUE + "|" + ANSI_RESET);
        System.out.println();
    }

}