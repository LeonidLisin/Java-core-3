class LetterGenerator {
    private char currentLetter = 'A', maxLetter;

    LetterGenerator(char maxLetter){
        this.maxLetter = maxLetter;
    }

    synchronized void letterGenerate(char currentThreadLetter){
        while(currentLetter!=currentThreadLetter){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(currentThreadLetter);
        currentLetter++;
        if(currentLetter>maxLetter) currentLetter='A';
        notifyAll();
    }
}
