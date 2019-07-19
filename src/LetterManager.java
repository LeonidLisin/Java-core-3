public class LetterManager extends Thread {
    private char myLetter;
    private LetterGenerator lg;

    LetterManager(char myLetter, LetterGenerator lg){
        this.myLetter = myLetter;
        this.lg = lg;
    }

    @Override
    public void run(){
        for (int i = 0; i < 5; i++)
            lg.letterGenerate(myLetter);
    }
}
