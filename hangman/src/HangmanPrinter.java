import java.util.ArrayList;

// This class is used as a helper class for printing the outputs
public class HangmanPrinter {

    // Prints the missing characters and characters correctly guessed
    public static void printGuess(String word, ArrayList<HangmanGuess> guesses) { // TODO change params
        char temp = 's';
        if (guesses.stream().anyMatch(val -> val.c == temp)) {

        }
    }

    // The functions below are used to print the hanging man
    public static void printMan(int counter) {
        switch (counter) {
            case 0:
                incorrect_0();
                return;
            case 1:
                incorrect_1();
                return;
            case 2:
                incorrect_2();
                return;
            case 3:
                incorrect_3();
                return;
            case 4:
                incorrect_4();
                return;
            case 5:
                incorrect_5();
                return;
            case 6:
                incorrect_6();
        }
    }

    private static void incorrect_0() {

    }

    private static void incorrect_1() {

    }

    private static void incorrect_2() {

    }


    private static void incorrect_3() {

    }


    private static void incorrect_4() {

    }


    private static void incorrect_5() {

    }


    private static void incorrect_6() {

    }


}
