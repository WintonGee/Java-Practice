// This class is used to keep track of the guesses made for the hangman game
public class HangmanGuess {

    public char c;
    public boolean correct;

    public HangmanGuess(char newC, boolean newCorrect) {
        this.c = newC;
        this.correct = newCorrect;
    }

}
