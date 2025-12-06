package DataStructureAndAlgorithms.domain.practices;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.problems.ProblemRepository;
import DataStructureAndAlgorithms.infrastructure.discovery.ClassScanner;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PracticeRepositoryImpl implements PracticeRepository {
    private final ClassScanner classScanner;
    private final ProblemRepository problemRepository;
    private final Map<String, PracticeInfo> practicesById = new ConcurrentHashMap<>();
    private final Map<String, List<PracticeInfo>> practicesByName = new ConcurrentHashMap<>();
    private final Map<String, List<PracticeInfo>> practicesByCategory = new ConcurrentHashMap<>();

    public PracticeRepositoryImpl(ClassScanner classScanner, ProblemRepository problemRepository) {
        this.classScanner = classScanner;
        this.problemRepository = problemRepository;
        initialize();
    }

    private void initialize() {
        practicesById.clear();
        practicesByName.clear();
        practicesByCategory.clear();

        Map<String, List<ProblemInfo>> problemsByName = problemRepository.findAll().stream()
                .collect(Collectors.groupingBy(ProblemInfo::getName));

        Map<String, List<PracticeInfo>> discoveredPractices = classScanner.discoverPractices(problemsByName);

        discoveredPractices.values().stream()
                .flatMap(List::stream)
                .forEach(this::indexPractice);
    }

    private void indexPractice(PracticeInfo practice) {
        practicesById.put(practice.getUniqueId(), practice);

        String problemName = practice.getProblemInfo().getName();
        practicesByName.computeIfAbsent(problemName, k -> new ArrayList<>())
                .add(practice);

        String category = practice.getProblemInfo().getCategory();
        practicesByCategory.computeIfAbsent(category, k -> new ArrayList<>())
                .add(practice);
    }

    @Override
    public List<PracticeInfo> findAll() {
        return new ArrayList<>(practicesById.values());
    }

    @Override
    public Optional<PracticeInfo> findById(String uniqueId) {
        return Optional.ofNullable(practicesById.get(uniqueId));
    }

    @Override
    public List<PracticeInfo> findByName(String name) {
        return practicesByName.getOrDefault(name, new ArrayList<>());
    }

    @Override
    public Optional<PracticeInfo> findByNameAndCategory(String name, String category) {
        return findByName(name).stream()
                .filter(practice -> practice.getProblemInfo().getCategory().equals(category))
                .findFirst();
    }

    @Override
    public Map<String, List<PracticeInfo>> findAllGroupedByCategory() {
        return new HashMap<>(practicesByCategory);
    }

    @Override
    public boolean exists(String name, String category) {
        return findByNameAndCategory(name, category).isPresent();
    }

    @Override
    public void add(PracticeInfo practice) {
        String name = practice.getProblemInfo().getName();
        String category = practice.getProblemInfo().getCategory();

        if (exists(name, category)) {
            throw new IllegalArgumentException("Practice already exists: " + practice.getUniqueId());
        }
        indexPractice(practice);
    }

    @Override
    public void remove(PracticeInfo practice) {
        practicesById.remove(practice.getUniqueId());

        String problemName = practice.getProblemInfo().getName();
        List<PracticeInfo> byName = practicesByName.get(problemName);
        if (byName != null) {
            byName.remove(practice);
            if (byName.isEmpty()) {
                practicesByName.remove(problemName);
            }
        }

        String category = practice.getProblemInfo().getCategory();
        List<PracticeInfo> byCategory = practicesByCategory.get(category);
        if (byCategory != null) {
            byCategory.remove(practice);
            if (byCategory.isEmpty()) {
                practicesByCategory.remove(category);
            }
        }
    }

}
