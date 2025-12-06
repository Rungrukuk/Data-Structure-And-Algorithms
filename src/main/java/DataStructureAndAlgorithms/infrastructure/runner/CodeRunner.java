package DataStructureAndAlgorithms.infrastructure.runner;

import DataStructureAndAlgorithms.core.base.BasePractice;
import DataStructureAndAlgorithms.core.base.BaseProblem;
import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.PracticeResult;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.core.models.ProblemResult;

import java.lang.reflect.Constructor;
import java.util.Optional;

public class CodeRunner {

    public Optional<ProblemResult> runProblem(ProblemInfo problemInfo) {
        try {
            BaseProblem<?> instance = instantiateProblem(problemInfo);
            Object result = instance.solve();
            return Optional.of(new ProblemResult(problemInfo.getName(), result));
        } catch (Exception e) {
            // Log error
            return Optional.empty();
        }
    }

    public Optional<PracticeResult> runPractice(PracticeInfo practiceInfo) {
        try {
            BaseProblem<?> problemInstance = instantiateProblem(practiceInfo.getProblemInfo());
            BasePractice<?, ?> practiceInstance = instantiatePractice(practiceInfo, problemInstance);

            Object practiceResult = practiceInstance.practice();
            Object solutionResult = problemInstance.solve();
            boolean isCorrect = practiceInstance.compare();

            return Optional.of(new PracticeResult(
                    practiceInfo.getProblemInfo().getName(),
                    practiceResult,
                    solutionResult,
                    isCorrect));
        } catch (Exception e) {
            // Log error
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
            throw new RuntimeException("Failed to instantiate problem: " + info.getClassName(), e);
        }
    }

    private BasePractice<?, ?> instantiatePractice(PracticeInfo info, BaseProblem<?> problem) {
        try {
            Class<?> clazz = Class.forName(info.getPracticeClassName());
            Constructor<?> ctor = clazz.getDeclaredConstructor(problem.getClass());
            ctor.setAccessible(true);
            return (BasePractice<?, ?>) ctor.newInstance(problem);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate practice: " + info.getPracticeClassName(), e);
        }
    }
}