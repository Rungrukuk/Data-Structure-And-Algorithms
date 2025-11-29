package DataStructureAndAlgorithms.runner;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import DataStructureAndAlgorithms.core.BasePractice;
import DataStructureAndAlgorithms.core.BaseProblem;
import DataStructureAndAlgorithms.exceptions.PracticeInstantiationException;
import DataStructureAndAlgorithms.exceptions.ProblemInstantiationException;
import DataStructureAndAlgorithms.models.PracticeInfo;
import DataStructureAndAlgorithms.models.PracticeResult;
import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.models.ProblemResult;
import DataStructureAndAlgorithms.services.ProblemPracticeService;

public class ProblemRunner {

    private final ProblemPracticeService problemPracticeService;

    public ProblemRunner(ProblemPracticeService problemPracticeService) {
        this.problemPracticeService = problemPracticeService;
    }

    // ========================= LISTING / VARIANTS =========================
    public List<String> getAvailableProblems() {
        return problemPracticeService.getProblemInfoList().stream()
                .map(ProblemInfo::getUniqueId)
                .sorted()
                .toList();
    }

    public List<String> getAvailablePractices() {
        return problemPracticeService.getPracticeInfoList().stream()
                .map(PracticeInfo::getUniqueId)
                .sorted()
                .toList();
    }

    public Map<String, List<String>> getProblemsByCategory() {
        return problemPracticeService.groupProblemsByCategory().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .map(ProblemInfo::getUniqueId)
                                .sorted()
                                .toList()));
    }

    public Map<String, List<String>> getPracticesByCategory() {
        return problemPracticeService.groupPracticesByCategory().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .map(PracticeInfo::getUniqueId)
                                .sorted()
                                .toList()));
    }

    // ========================= RUN PROBLEM/PRACTICE =========================
    public Optional<ProblemResult> runProblem(String uniqueId) {
        return problemPracticeService.findProblemByUniqueId(uniqueId).map(info -> {
            BaseProblem<?> instance = instantiateProblem(info);
            Object result = instance.solve();
            return new ProblemResult(info.getName(), result);
        });
    }

    public Optional<PracticeResult> runPractice(String uniqueId) {
        return problemPracticeService.findPracticeByUniqueId(uniqueId).map(info -> {
            BaseProblem<?> problemInstance = instantiateProblem(info.getProblemInfo());
            BasePractice<?, ?> practiceInstance = instantiatePractice(info, problemInstance);

            Object practiceResult = practiceInstance.practice();
            Object solutionResult = problemInstance.solve();
            boolean isCorrect = practiceInstance.compare();

            return new PracticeResult(
                    info.getProblemInfo().getName(),
                    practiceResult,
                    solutionResult,
                    isCorrect);
        });
    }

    private BaseProblem<?> instantiateProblem(ProblemInfo info) {
        try {
            Class<?> clazz = Class.forName(info.getClassName());
            Constructor<?> ctor = clazz.getDeclaredConstructor();
            ctor.setAccessible(true);
            return (BaseProblem<?>) ctor.newInstance();
        } catch (Exception e) {
            throw new ProblemInstantiationException(info.getClassName(), e);
        }
    }

    private BasePractice<?, ?> instantiatePractice(PracticeInfo info, BaseProblem<?> problem) {
        try {
            Class<?> clazz = Class.forName(info.getPracticeClassName());
            Constructor<?> ctor = clazz.getDeclaredConstructor(problem.getClass());
            ctor.setAccessible(true);
            return (BasePractice<?, ?>) ctor.newInstance(problem);
        } catch (Exception e) {
            throw new PracticeInstantiationException(info.getPracticeClassName(), e);
        }
    }

    // ========================= DUPLICATE HANDLING / VARIANTS
    // =========================
    public List<String> getProblemVariants(String name) {
        return problemPracticeService.findProblemsByName(name).stream()
                .map(ProblemInfo::getUniqueId)
                .sorted()
                .toList();
    }

    public List<String> getPracticeVariants(String name) {
        return problemPracticeService.findPracticesByName(name).stream()
                .map(PracticeInfo::getUniqueId)
                .sorted()
                .toList();
    }

}
