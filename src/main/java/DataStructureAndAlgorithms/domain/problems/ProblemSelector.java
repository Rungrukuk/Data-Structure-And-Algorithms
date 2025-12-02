package DataStructureAndAlgorithms.domain.problems;

import DataStructureAndAlgorithms.core.models.ProblemInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProblemSelector {
    private final ProblemRepository problemRepository;

    public ProblemSelector(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public List<String> getAllProblemDisplays() {
        return problemRepository.findAll().stream()
                .map(this::formatProblemDisplay)
                .sorted()
                .toList();
    }

    public Map<String, List<String>> getProblemsByCategoryDisplays() {
        Map<String, List<ProblemInfo>> problemsByCategory = problemRepository.findAllGroupedByCategory();

        return problemsByCategory.entrySet().stream()
                .collect(java.util.stream.Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(this::formatProblemDisplay)
                                .sorted()
                                .toList()));
    }

    public List<String> findProblemDisplaysByName(String name) {
        return problemRepository.findByName(name).stream()
                .map(this::formatProblemDisplay)
                .sorted()
                .toList();
    }

    public Optional<ProblemInfo> findProblemByDisplay(String displayString) {
        return problemRepository.findAll().stream()
                .filter(problem -> formatProblemDisplay(problem).equals(displayString))
                .findFirst();
    }

    private String formatProblemDisplay(ProblemInfo problem) {
        return String.format("%-35s [%s]",
                problem.getName(),
                problem.getCategory());
    }

    public List<String> getAllCategories() {
        return problemRepository.findAllGroupedByCategory().keySet().stream()
                .sorted()
                .toList();
    }
}