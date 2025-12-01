package DataStructureAndAlgorithms.domain.problems;

import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.infrastructure.discovery.ClassScanner;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ProblemRepositoryImpl implements ProblemRepository {
    private final ClassScanner classScanner;
    private final Map<String, ProblemInfo> problemsById = new ConcurrentHashMap<>();
    private final Map<String, List<ProblemInfo>> problemsByName = new ConcurrentHashMap<>();
    private final Map<String, List<ProblemInfo>> problemsByCategory = new ConcurrentHashMap<>();

    public ProblemRepositoryImpl(ClassScanner classScanner) {
        this.classScanner = classScanner;
        initialize();
    }

    private void initialize() {
        problemsById.clear();
        problemsByName.clear();
        problemsByCategory.clear();

        Map<String, List<ProblemInfo>> discoveredProblems = classScanner.discoverProblems();

        discoveredProblems.values().stream()
                .flatMap(List::stream)
                .forEach(this::indexProblem);
    }

    private void indexProblem(ProblemInfo problem) {
        problemsById.put(problem.getUniqueId(), problem);

        problemsByName.computeIfAbsent(problem.getName(), k -> new ArrayList<>())
                .add(problem);

        problemsByCategory.computeIfAbsent(problem.getCategory(), k -> new ArrayList<>())
                .add(problem);
    }

    @Override
    public List<ProblemInfo> findAll() {
        return new ArrayList<>(problemsById.values());
    }

    @Override
    public Optional<ProblemInfo> findById(String uniqueId) {
        return Optional.ofNullable(problemsById.get(uniqueId));
    }

    @Override
    public List<ProblemInfo> findByName(String name) {
        return problemsByName.getOrDefault(name, new ArrayList<>());
    }

    @Override
    public Optional<ProblemInfo> findByNameAndCategory(String name, String category) {
        return findByName(name).stream()
                .filter(problem -> problem.getCategory().equals(category))
                .findFirst();
    }

    @Override
    public Map<String, List<ProblemInfo>> findAllGroupedByCategory() {
        return new HashMap<>(problemsByCategory);
    }

    @Override
    public boolean exists(String name, String category) {
        return findByNameAndCategory(name, category).isPresent();
    }

    @Override
    public void add(ProblemInfo problem) {
        if (exists(problem.getName(), problem.getCategory())) {
            throw new IllegalArgumentException("Problem already exists: " + problem.getUniqueId());
        }
        indexProblem(problem);
    }

    @Override
    public void remove(ProblemInfo problem) {
        problemsById.remove(problem.getUniqueId());

        List<ProblemInfo> byName = problemsByName.get(problem.getName());
        if (byName != null) {
            byName.remove(problem);
            if (byName.isEmpty()) {
                problemsByName.remove(problem.getName());
            }
        }

        List<ProblemInfo> byCategory = problemsByCategory.get(problem.getCategory());
        if (byCategory != null) {
            byCategory.remove(problem);
            if (byCategory.isEmpty()) {
                problemsByCategory.remove(problem.getCategory());
            }
        }
    }

    @Override
    public void refresh() {
        initialize();
    }
}