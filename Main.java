import java.io.*;
import java.util.*;
class Main{
    
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter Word to be corrected");
        String word = scanner.next();

        SpellingCorrector spellingCorrector = new SpellingCorrector();
        String correctWord = spellingCorrector.correctWord(word);

        System.out.println("word after spelling correction : "+correctWord);

    }
}
