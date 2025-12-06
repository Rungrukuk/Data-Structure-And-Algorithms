package DataStructureAndAlgorithms.domain.practices;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.creators.PracticeGenerator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PracticeOrchestrator {
    private final PracticeRepository practiceRepository;
    private final PracticeExecutor practiceExecutor;
    private final PracticeGenerator practiceGenerator;

    public PracticeOrchestrator(PracticeRepository practiceRepository,
            PracticeExecutor practiceExecutor,
                                PracticeGenerator practiceGenerator) {
        this.practiceRepository = practiceRepository;
        this.practiceExecutor = practiceExecutor;
        this.practiceGenerator = practiceGenerator;
    }

    // ========================= LISTING OPERATIONS =========================

    public List<PracticeInfo> listAllPractices() {
        return practiceRepository.findAll();
    }

    public Map<String, List<PracticeInfo>> listPracticesByCategory() {
        return practiceRepository.findAllGroupedByCategory();
    }

    public List<PracticeInfo> listPracticeVariants(String name) {
        return practiceRepository.findByName(name);
    }


    public Optional<String> runPractice(PracticeInfo practiceInfo) {
        return practiceExecutor.runPractice(practiceInfo)
                .map(practiceExecutor::formatResult);
    }

    public void createPracticeForProblem(ProblemInfo problemInfo) {
        PracticeInfo practiceInfo = new PracticeInfo(problemInfo, null, null);

        practiceGenerator.createPractice(practiceInfo);

        practiceRepository.add(practiceInfo);
    }

    public void resetPractice(PracticeInfo practiceInfo) {
        practiceGenerator.resetPractice(practiceInfo);
    }

    public List<String> getAllCategories() {
        return practiceRepository.findAllGroupedByCategory().keySet().stream()
                .sorted()
                .toList();
    }

}
