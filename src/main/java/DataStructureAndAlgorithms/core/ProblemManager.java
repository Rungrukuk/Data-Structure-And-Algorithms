package DataStructureAndAlgorithms.core;

import java.util.Map;

import DataStructureAndAlgorithms.exceptions.PracticeInstantiationException;
import DataStructureAndAlgorithms.exceptions.ProblemInstantiationException;
import DataStructureAndAlgorithms.models.PracticeInfo;
import DataStructureAndAlgorithms.models.PracticeResult;
import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.models.ProblemResult;
import DataStructureAndAlgorithms.services.ClassDiscoveryService;
import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.utils.Constants;

public class ProblemManager {
    private final ClassDiscoveryService discoveryService;
    private final FileSystemService fileSystemService;
    private final Map<String, ProblemInfo> problemInfoMap;
    private final Map<String, PracticeInfo> practiceInfoMap;

    public ProblemManager(ClassDiscoveryService discoveryService, FileSystemService fileSystemService) {
        this.discoveryService = discoveryService;
        this.fileSystemService = fileSystemService;
        this.problemInfoMap = discoveryService.discoverProblems();
        this.practiceInfoMap = discoveryService.discoverPractices();
    }

    public ProblemResult runProblem(String problemName) {
        BaseProblem<?> problemInstance = instantiateProblem(problemInfoMap.get(problemName));
        Object result = problemInstance.solve();
        return new ProblemResult(problemName, result);
    }

    public PracticeResult runPractice(String practiceName) {
        PracticeInfo practiceInfo = practiceInfoMap.get(practiceName);
        BaseProblem<?> problem = instantiateProblem(practiceInfo.getProblemInfo());
        BasePractice<?, ?> practice = instantiatePractice(practiceInfo, problem);

        Object practiceResult = practice.practice();
        Object solutionResult = problem.solve();
        boolean isCorrect = practice.compare();

        return new PracticeResult(
                practiceInfo.getProblemInfo().getName(),
                practiceResult,
                solutionResult,
                isCorrect);
    }

    // public List<String> getAvailableProblems() {
    // return null;
    // }

    // public List<String> getAvailablePractices() {
    // return null;
    // }

    private BaseProblem<?> instantiateProblem(ProblemInfo problemInfo) {
        try {
            Class<?> clazz = Class.forName(problemInfo.getClassName());
            return (BaseProblem<?>) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ProblemInstantiationException(
                    problemInfo.getClassName(),
                    e);
        }
    }

    private BasePractice<?, ?> instantiatePractice(PracticeInfo practiceInfo, BaseProblem<?> problem) {
        try {
            Class<?> clazz = Class.forName(practiceInfo.getPracticeClassName());
            return (BasePractice<?, ?>) clazz.getDeclaredConstructor().newInstance(problem);
        } catch (Exception e) {
            throw new PracticeInstantiationException(
                    practiceInfo.getPracticeClassName(),
                    e);
        }
    }
}
