package DataStructureAndAlgorithms.core;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

import DataStructureAndAlgorithms.exceptions.PracticeInstantiationException;
import DataStructureAndAlgorithms.exceptions.ProblemInstantiationException;
import DataStructureAndAlgorithms.models.*;
import DataStructureAndAlgorithms.services.ClassDiscoveryService;
import DataStructureAndAlgorithms.services.FileSystemService;

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

    public Optional<ProblemResult> runProblem(String problemName) {
        ProblemInfo info = problemInfoMap.get(problemName);
        if (info == null)
            return Optional.empty();

        BaseProblem<?> instance = instantiateProblem(info);
        Object result = instance.solve();

        return Optional.of(new ProblemResult(info.getName(), result));
    }

    public Optional<PracticeResult> runPractice(String practiceName) {
        PracticeInfo practiceInfo = practiceInfoMap.get(practiceName);
        if (practiceInfo == null)
            return Optional.empty();

        BaseProblem<?> problem = instantiateProblem(practiceInfo.getProblemInfo());
        BasePractice<?, ?> practice = instantiatePractice(practiceInfo, problem);

        Object practiceResult = practice.practice();
        Object solutionResult = problem.solve();
        boolean isCorrect = practice.compare();

        return Optional.of(
                new PracticeResult(
                        practiceInfo.getProblemInfo().getName(),
                        practiceResult,
                        solutionResult,
                        isCorrect));
    }

    public List<String> getAvailableProblems() {
        return new ArrayList<>(problemInfoMap.keySet());
    }

    public Map<String, List<String>> getProblemsByCategory() {
        return discoveryService.getProblemsByCategory(problemInfoMap).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .map(ProblemInfo::getName)
                                .sorted()
                                .toList()));
    }

    public Map<String, List<String>> getPracticesByCategory() {
        // TODO Implement this
        return null;
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
            Constructor<?> ctor = clazz.getDeclaredConstructor(BaseProblem.class);
            ctor.setAccessible(true);
            return (BasePractice<?, ?>) ctor.newInstance(problem);
        } catch (Exception e) {
            throw new PracticeInstantiationException(info.getPracticeClassName(), e);
        }
    }
}
