public class LetterMain {
    public static void main(String[] args) {
        final char MAX_LETTER = 'C';
        LetterGenerator lg = new LetterGenerator(MAX_LETTER);
        for (char c = 'A'; c <= MAX_LETTER; c++)
            new LetterManager(c, lg).start();
    }
}
