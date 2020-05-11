package readability.Strategy;

/**
 * A strategy to calculate score of a given file by using Flesch Reading Ease test.
 * The higher the score, the easier it is to read.
 *
 * @author Phuwanut Jiamwatthanaloet
 */
public class FleschReadingEase implements ScoringStrategy{

    /**
     * Calculate score of a file or url.
     * @param wordCount is the number of words in the file.
     * @param syllableCount is the number of syllables in the file.
     * @param sentenceCount is the number of sentences in the file.
     */
    public double getScore(double wordCount,double syllableCount,double sentenceCount) {
        return 206.835 - 1.015 * (wordCount/sentenceCount) - 84.6 * (syllableCount/wordCount);
    }
}
