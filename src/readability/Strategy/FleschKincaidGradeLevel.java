package readability.Strategy;

/**
 * A strategy to calculate score of a given file by using Flesch Kincaid Grade Level test.
 * These readability tests are used extensively in the field of education.
 *
 * @author Phuwanut Jiamwatthanaloet
 */
public class FleschKincaidGradeLevel implements ScoringStrategy {

    /**
     * Calculate score of a file or url.
     * @param wordCount is the number of words in the file.
     * @param syllableCount is the number of syllables in the file.
     * @param sentenceCount is the number of sentences in the file.
     */
    public double getScore(double wordCount,double syllableCount,double sentenceCount) {
        return 0.39 * (wordCount/sentenceCount) + 11.8 * (syllableCount/wordCount) - 15.59;
    }
}
