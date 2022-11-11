import java.util.Scanner;

public class Main {

    public static int ROWS = 6, COLUMNS = 7;
    public static char EMPTY_INDICATOR = '-', FIRST_PLAYER = 'O', SECOND_PLAYER = 'X';

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";


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

        char player = FIRST_PLAYER;
        while (true) {
            display(board);

            String playerName = player == FIRST_PLAYER ? firstPlayerName : secondPlayerName;
            String color = player == FIRST_PLAYER ? ANSI_RED : ANSI_YELLOW;
            System.out.println("(" + color + "O" + ANSI_RESET + ") " + playerName + " pick a position: ");
            String input = myObj.nextLine();

            if (!isNumeric(input)) {
                System.out.println("Integer value needed!");
                continue;
            }

            int position = Integer.parseInt(input);
            if (!isPositionWithinRange(position)) {
                System.out.println("Please choose a range between 0 - 6");
                continue;
            }

            int positionHeight = getPositionHeight(board, position);
            if (positionHeight == -1) {
                System.out.println("Please choose a column with an empty slot");
                continue;
            }

            // Update the board
            board[positionHeight][position] = player;

            if (isPlayerWon(board, player)) {
                display(board);
                System.out.println("Game Over: " + playerName + " Wins!");
                break;
            }

            if (isGameTied(board)) {
                display(board);
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

    // TODO
    // Note: The checking algorithms are not the most efficient, but easiest to understand
    // Condition for winning: 4 connections in a row
    // Check 1: Vertical
    // Check 2: Horizontal
    // Check 3: Diagonal to the left
    // Check 4: Diagonal to the right
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
        }

        // Check 2: Horizontal
        connections = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (connections >= 4)
                    return true;
                if (board[i][j] == player)
                    connections++;
                else
                    connections = 0;
            }
        }

        // Check 3: Diagonal to the left
        connections = 0;

        // Check 4: Diagonal to the right
        connections = 0;

        return false;
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

    public static void display(char[][] board) {
        System.out.println(ANSI_BLUE + "-----------------" + ANSI_RESET);

        for (int i = board.length - 1; i >= 0; i--) {
            System.out.print(ANSI_BLUE + "| " + ANSI_RESET);
            for (int j = 0; j < board[0].length; j++)
                System.out.print(board[i][j] + " ");
            System.out.print(ANSI_BLUE + "|" + ANSI_RESET);
            System.out.println();
        }

        System.out.println(ANSI_BLUE + "-----------------" + ANSI_RESET);

        System.out.print(ANSI_BLUE + "| " + ANSI_RESET);
        for (int i = 0; i < COLUMNS; i++)
            System.out.print(i + " ");
        System.out.print(ANSI_BLUE + "|" + ANSI_RESET);
        System.out.println();
    }

}