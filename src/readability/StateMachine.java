package readability;

/** A state machine to count syllable. */
public class StateMachine {

    /** State of vowel. */
    private boolean vowelState = false;

    /** Count the number of syllable in a word.
     *
     * @param word is the word to be counted.
     * @return syllableCount is the number of syllable in a word.
     */
    public double countSyllable(String word) {
        double syllableCount = 0;
        for (int j = 0; j < word.length(); j++) {
            String sub = word.substring(j, j + 1);
            if (sub.equals("y") || sub.equals("Y") && !vowelState) {
                vowelState = true;
            }
            if (j == word.length() - 1 && sub.equals("e") && !vowelState) {
                syllableCount -= 1;
            }
            if (sub.equals("a") || sub.equals("e") || sub.equals("i") || sub.equals("o") || sub.equals("u")
                    || sub.equals("A") || sub.equals("E") || sub.equals("I") || sub.equals("O") || sub.equals("U")) {
                vowelState = true;
            } else if (vowelState) {
                vowelState = false;
                syllableCount += 1;
            }
            if (j == word.length() - 1 && vowelState) {
                vowelState = false;
                syllableCount += 1;
            }
        }
        return syllableCount;
    }
}
