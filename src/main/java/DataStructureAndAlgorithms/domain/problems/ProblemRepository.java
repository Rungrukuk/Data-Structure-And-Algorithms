package DataStructureAndAlgorithms.domain.problems;

import DataStructureAndAlgorithms.core.models.ProblemInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProblemRepository {
    List<ProblemInfo> findAll();

    Optional<ProblemInfo> findById(String uniqueId);

    List<ProblemInfo> findByName(String name);

    Optional<ProblemInfo> findByNameAndCategory(String name, String category);

    Map<String, List<ProblemInfo>> findAllGroupedByCategory();

    boolean exists(String name, String category);

    void add(ProblemInfo problem);

    void remove(ProblemInfo problem);

    void refresh();
}