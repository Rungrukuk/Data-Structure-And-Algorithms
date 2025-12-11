package DataStructureAndAlgorithms.domain.problems;

import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.creators.ProblemGenerator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProblemOrchestrator {
    private final ProblemRepository problemRepository;
    private final ProblemExecutor problemExecutor;
    private final ProblemGenerator problemGenerator;

    public ProblemOrchestrator(ProblemRepository problemRepository,
            ProblemExecutor problemExecutor,
            ProblemGenerator problemGenerator) {
        this.problemRepository = problemRepository;
        this.problemExecutor = problemExecutor;
        this.problemGenerator = problemGenerator;
    }

    // ========================= LISTING OPERATIONS =========================

    public List<ProblemInfo> listAllProblems() {
        return problemRepository.findAll();
    }

    public Map<String, List<ProblemInfo>> listProblemsByCategory() {
        return problemRepository.findAllGroupedByCategory();
    }

    public List<ProblemInfo> listProblemVariants(String name) {
        return problemRepository.findByName(name);
    }

    // ========================= EXECUTION OPERATIONS =========================

    public Optional<String> runProblem(ProblemInfo problemInfo) {
        return problemExecutor.runProblem(problemInfo)
                .map(problemExecutor::formatResult);
    }

    // ========================= CREATION OPERATIONS =========================

    public void createNewProblem(String name, String category, String returnType, String difficulty) {
        ProblemInfo problemInfo = new ProblemInfo(name, category, null, returnType, null, difficulty);

        try {
            problemGenerator.createProblem(problemInfo);

            problemRepository.add(problemInfo);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    // ========================= UTILITY METHODS =========================

    public boolean problemExists(String name, String category) {
        return problemRepository.exists(name, category);
    }

    public List<String> getAllCategories() {
        return problemRepository.findAllGroupedByCategory().keySet().stream()
                .sorted()
                .toList();
    }

}
