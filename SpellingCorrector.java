
import java.io.*;
import java.util.*;
import java.util.regex.*;


/**
 * Spelling Corrector
 *
 * @author sagar sagautam5@gmail.com
 * @version 1.0
 * 
 * Correct spelling if word doesn't exist in dictionery
 *
 * @since 2017-07-15
 */
class SpellingCorrector{
    
    // Name of dictionery text file

    String fileName = "wordlist.txt";
    
    private HashMap<String, Integer> dictionery;
    ArrayList<String> dictionary = new ArrayList<String>();
    
    /**
     * Constructor
     *
     * Read dictionery file
     *
     * @param void
     * @return void
     *
     */
    SpellingCorrector(){
       
        try{

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            for(String line = bufferedReader.readLine(); line!=null; line = bufferedReader.readLine()){
               
               String[] words = line.split(" ");
               Collections.addAll(dictionary,words);
            }
            this.createHashMap(fileName);
        
        }catch(Exception e){
            System.out.println("ERROR: "+e.getMessage());
        }
    }

	/**
     *
	 * Builds up a map of correct words with
	 * their frequencies, based on the words in the given file.
	 * 
	 * @param file the text to process
	 * @throws IOException
	 */

	public void createHashMap(String file) throws IOException {
	    
        dictionery = new HashMap<String, Integer>();
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(file)); 

	    Pattern p = Pattern.compile("\\w+");

	    for(String temp = ""; temp != null; temp = bufferedReader.readLine()){
	        
            Matcher m = p.matcher(temp.toLowerCase());	      

	        while(m.find()){
		        dictionery.put((temp = m.group()), dictionery.containsKey(temp) ? dictionery.get(temp) + 1 : 1);
            }

	    }

	    bufferedReader.close();
	}

	/**
	 * Constructs a list of all words within edit distance 1 of the given word.
     *
     *
	 * @param word the word to construct the list from
	 * @return a list of words with in edit distance 1 of word
	 */

	private ArrayList<String> probableEdits(String word) {

	    ArrayList<String> closeWords = new ArrayList<String>();
	    
	    // Possible words with single letter delete
        
	    for(int i=0; i < word.length(); ++i) 
	      closeWords.add(word.substring(0, i) + word.substring(i+1));
	    
	    // Possible words with two letter swap

	    for(int i=0; i < word.length()-1; ++i) 
	      closeWords.add(word.substring(0, i) + word.substring(i+1, i+2) + 
		         word.substring(i, i+1) + word.substring(i+2));
	    
	    // Possible words with one letter replaced with any letter

	    for(int i=0; i < word.length(); ++i) 
	      for(char c='a'; c <= 'z'; ++c) 
		    closeWords.add(word.substring(0, i) + String.valueOf(c) + word.substring(i+1));
	    
	    // Possible words with insertion of any letter at any position in word

	    for(int i=0; i <= word.length(); ++i) 
	      for(char c='a'; c <= 'z'; ++c) 
		    closeWords.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
	    
	    return closeWords;
	}

    /**
     *
     * Correct spelling of given word using dictionery of words
     * If word exists in the dictionery, just return same word otherwise
     * find probable closer words with simple edits or errors and return most
     * probable word one from the probable list.
     *
     * @param word -> word to be corrected
     *
     * @return corrected word
     */

	public String correctWord(String word) {
         
        if(dictionery.containsKey(word))
            return word;

        ArrayList<String> list = probableEdits(word);    
        HashMap<Integer, String> candidates = new HashMap<Integer, String>();


	    for(String s : list) 
	      if(dictionery.containsKey(s))
              candidates.put(dictionery.get(s),s);
	    
        if(candidates.size() > 0)   
            return candidates.get(Collections.max(candidates.keySet()));

        for(String s : list) 
	      for(String w : probableEdits(s)) 
              if(dictionery.containsKey(w)) 
                  candidates.put(dictionery.get(w),w);
        
	    return candidates.size() > 0 ? candidates.get(Collections.max(candidates.keySet())) : "?" + word;
	}


}
