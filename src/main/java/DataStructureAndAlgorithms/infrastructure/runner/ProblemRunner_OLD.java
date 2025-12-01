package DataStructureAndAlgorithms.infrastructure.runner;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Optional;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.base.BaseProblem;
import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.PracticeResult;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.core.models.ProblemResult;
import DataStructureAndAlgorithms.exceptions.PracticeInstantiationException;
import DataStructureAndAlgorithms.exceptions.ProblemInstantiationException;
import DataStructureAndAlgorithms.services.InfoDisplayService;

public class ProblemRunner_OLD {

    private final InfoDisplayService infoDisplayService;

    public ProblemRunner_OLD(InfoDisplayService infoDisplayService) {
        this.infoDisplayService = infoDisplayService;
    }

    // ========================= RUN PROBLEM/PRACTICE =========================
    public Optional<ProblemResult> runProblem(String uniqueId) {
        Optional<ProblemInfo> problemInfo = infoDisplayService.findProblemByUniqueId(uniqueId);
        if (problemInfo.isPresent()) {
            return runProblem(problemInfo.get());
        }
        return Optional.empty();
    }

    public Optional<ProblemResult> runProblem(ProblemInfo problemInfo) {
        try {
            BaseProblem<?> instance = instantiateProblem(problemInfo);
            Object result = instance.solve();
            ProblemResult problemResult = new ProblemResult(problemInfo.getName(), result);
            return Optional.of(problemResult);
        } catch (Exception e) {
            // Log the exception if needed
            return Optional.empty();
        }
    }

    public Optional<PracticeResult> runPractice(String uniqueId) {
        Optional<PracticeInfo> practiceInfo = infoDisplayService.findPracticeByUniqueId(uniqueId);
        if (practiceInfo.isPresent()) {
            return runPractice(practiceInfo.get());
        }
        return Optional.empty();
    }

    public Optional<PracticeResult> runPractice(PracticeInfo practiceInfo) {
        try {
            BaseProblem<?> problemInstance = instantiateProblem(practiceInfo.getProblemInfo());
            BasePractice<?, ?> practiceInstance = instantiatePractice(practiceInfo, problemInstance);

            Object practiceResult = practiceInstance.practice();
            Object solutionResult = problemInstance.solve();
            boolean isCorrect = practiceInstance.compare();

            PracticeResult result = new PracticeResult(
                    practiceInfo.getProblemInfo().getName(),
                    practiceResult,
                    solutionResult,
                    isCorrect);
            return Optional.of(result);
        } catch (Exception e) {
            // Log the exception if needed
            return Optional.empty();
        }
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

    // =========== DUPLICATE HANDLING / VARIANTS =============
    public List<String> getProblemVariants(String name) {
        return infoDisplayService.findProblemDisplayStringsByName(name);
    }

    public List<String> getPracticeVariants(String name) {
        return infoDisplayService.findPracticeDisplayStringsByName(name);
    }
}