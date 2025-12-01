package DataStructureAndAlgorithms.domain.problems;

import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.creators.ProblemGenerator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProblemOrchestrator {
    private final ProblemRepository problemRepository;
    private final ProblemSelector problemSelector;
    private final ProblemExecutor problemExecutor;
    private final ProblemGenerator problemGenerator;

    public ProblemOrchestrator(ProblemRepository problemRepository,
            ProblemSelector problemSelector,
            ProblemExecutor problemExecutor,
            ProblemGenerator problemGenerator) {
        this.problemRepository = problemRepository;
        this.problemSelector = problemSelector;
        this.problemExecutor = problemExecutor;
        this.problemGenerator = problemGenerator;
    }

    // ========================= LISTING OPERATIONS =========================

    public List<String> listAllProblems() {
        return problemSelector.getAllProblemDisplays();
    }

    public Map<String, List<String>> listProblemsByCategory() {
        return problemSelector.getProblemsByCategoryDisplays();
    }

    public List<String> listProblemVariants(String name) {
        return problemSelector.findProblemDisplaysByName(name);
    }

    // ========================= SELECTION OPERATIONS =========================

    public Optional<ProblemInfo> selectProblemFromAll() {
        List<String> problems = listAllProblems();
        if (problems.isEmpty()) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    // ========================= EXECUTION OPERATIONS =========================

    public Optional<String> runProblem(ProblemInfo problemInfo) {
        return problemExecutor.runProblem(problemInfo)
                .map(problemExecutor::formatResult);
    }

    public Optional<String> runProblemByDisplay(String displayString) {
        return problemSelector.findProblemByDisplay(displayString)
                .flatMap(this::runProblem);
    }

    // ========================= CREATION OPERATIONS =========================

    public void createNewProblem(String name, String category, String returnType) {
        ProblemInfo problemInfo = new ProblemInfo(name, category, null, returnType, null);

        try {
            problemGenerator.createProblem(problemInfo);

            problemRepository.add(problemInfo);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create problem: " + e.getMessage(), e);
        }
    }

    // ========================= UTILITY METHODS =========================

    public boolean problemExists(String name, String category) {
        return problemRepository.exists(name, category);
    }

    public List<String> getAllCategories() {
        return problemSelector.getAllCategories();
    }

    public void refresh() {
        problemRepository.refresh();
    }
}