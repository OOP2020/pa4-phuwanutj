package readability.Strategy;

/**
 * A strategy to calculate a score from a given file or url.
 * The first strategy is to use Flesch Reading Ease test.
 * The second strategy is to use Flesch–Kincaid grade level test.
 *
 * @author Phuwanut Jiamwatthanaloet
 */
public interface ScoringStrategy {

    /**
     * Calculate score of a file or url.
     * @param wordCount is the number of words in the file.
     * @param syllableCount is the number of syllables in the file.
     * @param sentenceCount is the number of sentences in the file.
     */
    double getScore(double wordCount,double syllableCount,double sentenceCount);
}
