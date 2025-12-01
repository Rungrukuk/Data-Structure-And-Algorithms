package DataStructureAndAlgorithms.domain.practices;

import DataStructureAndAlgorithms.core.models.PracticeInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PracticeRepository {
    List<PracticeInfo> findAll();

    Optional<PracticeInfo> findById(String uniqueId);

    List<PracticeInfo> findByName(String name);

    Optional<PracticeInfo> findByNameAndCategory(String name, String category);

    Map<String, List<PracticeInfo>> findAllGroupedByCategory();

    boolean exists(String name, String category);

    void add(PracticeInfo practice);

    void remove(PracticeInfo practice);

    void refresh();
}