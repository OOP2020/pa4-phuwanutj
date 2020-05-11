package readability;

import javafx.application.Platform;
import java.io.*;
import java.net.*;
import javafx.scene.control.*;

/**
 * Count the number of words, syllables, and sentence of a given file or URL.
 *
 * @author Phuwanut Jiamwatthanaloet
 */
public class WordCount {
    /** Number of word. */
    private double wordCount = 0;
    /** Number of syllable. */
    private double syllableCount = 0;
    /** Number of sentence. */
    private double sentenceCount = 0;
    /** State of sentence. */
    private boolean sentenceState = false;

    /**
     * Count number of words, syllables, and sentences in the file.
     *
     * @param fileName is the name of the file.
     */
    public void CountWord(String fileName,TextField textField) {
        textField.setStyle("-fx-text-inner-color: black;");
        try (InputStream in = new URL(fileName).openStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            BufferWordCount(br,line);
        }
        catch (MalformedURLException mue) {
            File file = new File(fileName);
            try (InputStream in = new FileInputStream(file)) {
                Reader reader = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(reader);
                String line = "";
                BufferWordCount(br,line);
            }
            catch (FileNotFoundException foe) {
                textField.setStyle("-fx-text-inner-color: red;");
            }
            catch (IOException ioe) {
                textField.setStyle("-fx-text-inner-color: red;");
            }
        }
        catch (IOException ioe) {
            textField.setStyle("-fx-text-inner-color: red;");
        }
    }

    /**
     * Return the number of words in the file.
     *
     * @return the number of words in the file.
     */
    public double getWordCount() {
        return this.wordCount;
    }

    /**
     * Return the number of syllables in the file.
     *
     * @return the number of syllables in the file.
     */
    public double getSyllableCount() {
        return this.syllableCount;
    }

    /**
     * Return the sentence of syllables in the file.
     *
     * @return the sentence of syllables in the file.
     */
    public double getSentenceCount() {
        return this.sentenceCount;
    }

    /**
     * Check if the word have vowels or not.
     *
     * @param word is the word to check for vowels.
     * @return true if the word has at least one vowel,
     * false if it doesn't have a vowel.
     */
    public boolean haveVowel(String word){
        for (int i = 0 ; i < word.length() ; i++ ) {
            String sub = word.substring(i,i+1);
            if (sub.equals("a") || sub.equals("e") || sub.equals("i") || sub.equals("o") || sub.equals("u") || sub.equals("y")
                    || sub.equals("A") || sub.equals("E") || sub.equals("I") || sub.equals("O") || sub.equals("U") || sub.equals("Y")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the word contain number or not.
     *
     * @param word is the word to check for number.
     * @return true if the word has at least one number,
     * false if it doesn't have a number.
     */
    public boolean haveNonLetter(String word){
        for (char c: word.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Read data using buffered reader and count word, syllable, and sentence.
     *
     * @param br is the BufferedReader use to read a file.
     * @param line is the line in the file.
     */
    public void BufferWordCount(BufferedReader br,String line) {
        try {
            while ((line = br.readLine()) != null) {
                String[] wordList = line.split("[, .;  : _ \" !? ( )[ ]/]+");
                String[] lineArray = line.split("[.;?!]");
                for (String word : wordList) {
                    if (!word.isEmpty() && ((word.charAt(0) >= 'A' && word.charAt(0) <= 'Z') || word.charAt(0) >= 'a' && word.charAt(0) <= 'z') && haveVowel(word) && !haveNonLetter(word)) {
                        StateMachine syllable = new StateMachine();
                        this.syllableCount += syllable.countSyllable(word);
                        this.wordCount += 1;
                    }
                }
                if (line.isEmpty() && !sentenceState) {
                    sentenceState = true;
                    this.sentenceCount += 1;
                }
                for (int i = 0; i < lineArray.length-1; i++) {
                    String trimSentence = lineArray[i].trim();
                    for (int j = 0; j<trimSentence.length() ; j++) {
                        if (trimSentence.charAt(j) == 'a' || trimSentence.charAt(j) == 'e' || trimSentence.charAt(j) == 'i'
                                || trimSentence.charAt(j) == 'o' || trimSentence.charAt(j) == 'u' || trimSentence.charAt(j) == 'y'
                                || trimSentence.charAt(j) == 'A' || trimSentence.charAt(j) == 'E' || trimSentence.charAt(j) == 'I'
                                || trimSentence.charAt(j) == 'O' || trimSentence.charAt(j) == 'U' || trimSentence.charAt(j) == 'Y') {
                            this.sentenceCount += 1;
                            break;
                        }
                    }
                }
                String trimLine = line.trim();
                if ((trimLine.endsWith(".") || trimLine.endsWith("?") || trimLine.endsWith(";") || trimLine.endsWith("!"))) {
                    String trimSentence = lineArray[lineArray.length-1].trim();
                    for (int j = 0; j<trimSentence.length() ; j++) {
                        if (trimSentence.charAt(j) == 'a' || trimSentence.charAt(j) == 'e' || trimSentence.charAt(j) == 'i'
                                || trimSentence.charAt(j) == 'o' || trimSentence.charAt(j) == 'u' || trimSentence.charAt(j) == 'y'
                                || trimSentence.charAt(j) == 'A' || trimSentence.charAt(j) == 'E' || trimSentence.charAt(j) == 'I'
                                || trimSentence.charAt(j) == 'O' || trimSentence.charAt(j) == 'U' || trimSentence.charAt(j) == 'Y') {
                            this.sentenceCount += 1;
                            sentenceState = true;
                            break;
                        }
                    }
                }
                else {
                    if (!line.isEmpty()) sentenceState = false;
                }
            }
        }
        catch (IOException ioe) {
            Platform.exit();
        }
    }
}