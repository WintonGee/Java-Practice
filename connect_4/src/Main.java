import java.util.Scanner;

public class Main {

    public static int ROWS = 6, COLUMS = 7;
    public static char EMPTY_INDICATOR = '-', FIRST_PLAYER = 'O', SECOND_PLAYER = 'X';

    public static void main(String[] args) {
        // Init board
        char[][] board = new char[ROWS][COLUMS];
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                board[i][j] = EMPTY_INDICATOR;

        Scanner myObj = new Scanner(System.in);

        char player = FIRST_PLAYER;
        while (true) {
            display(board);

            System.out.println("Player " + player + " pick a position: ");
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

            // TODO adjust the display and fix the index checking...
            int positionHeight = getPositionHeight(board, position);
            if (positionHeight == -1) {
                System.out.println("Please choose a column with an empty slot");
                continue;
            }

            // Update the board
            board[position][positionHeight] = player;

            if (isPlayerWon(board, player)) {
                System.out.println("Game Over: " + player + " Wins!");
                return;
            }

            if (isGameTied(board)) {
                System.out.println("Game Over: Tied Game!");
                return;
            }

            // Swap player's turn
            player = player == FIRST_PLAYER ? SECOND_PLAYER : FIRST_PLAYER;
        }
    }

    public static boolean isGameTied(char[][] board) {
        for (char[] i : board)
            for (char j : i)
                if (j == EMPTY_INDICATOR)
                    return false;
        return true;
    }

    public static boolean isPlayerWon(char[][] board, char player) {
        return false;
    }

    // Return: If string is a numeric string
    public static boolean isNumeric(String string) {
        if(string == null || string.equals("")) {
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
        return pos >= 0 && pos < ROWS;
    }

    // PreCheck: Position is valid
    // Returns: the height of where to place a piece on the board
    public static int getPositionHeight(char[][] board, int pos) {
        for (int height = 0; height < ROWS; height++) {
            if (board[pos][height] == EMPTY_INDICATOR)
                return height;
        }

        return -1;
    }

    public static void display(char[][] board) {
        System.out.println("-------------");
        for (char[] i : board) {
            for (char j : i)
                System.out.print(j + " ");
            System.out.println();
        }

        for (int i = 0; i < COLUMS; i++)
            System.out.print(i + " ");
        System.out.println();
    }

}