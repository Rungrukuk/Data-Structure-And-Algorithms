package DataStructureAndAlgorithms.utils;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;

import java.util.Comparator;

public final class ProblemComparators {
    public static final Comparator<ProblemInfo> PROBLEM_BY_CATEGORY_DIFFICULTY_NAME =
            Comparator.comparing(ProblemInfo::getCategory)
                    .thenComparing(ProblemInfo::getDifficulty)
                    .thenComparing(ProblemInfo::getName);
    public static final Comparator<PracticeInfo> PRACTICE_BY_CATEGORY_DIFFICULTY_NAME =
            Comparator.comparing((PracticeInfo p) -> p.getProblemInfo().getCategory())
                    .thenComparing(p -> p.getProblemInfo().getDifficulty())
                    .thenComparing(p -> p.getProblemInfo().getName());

    private ProblemComparators() {}
}
