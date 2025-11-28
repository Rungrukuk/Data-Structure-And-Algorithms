package DataStructureAndAlgorithms.core;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

import DataStructureAndAlgorithms.exceptions.PracticeInstantiationException;
import DataStructureAndAlgorithms.exceptions.ProblemInstantiationException;
import DataStructureAndAlgorithms.models.*;
import DataStructureAndAlgorithms.services.ClassDiscoveryService;

public class ProblemManager {

    private final ClassDiscoveryService discoveryService;

    private List<ProblemInfo> problemInfoList;
    private List<PracticeInfo> practiceInfoList;

    public ProblemManager(ClassDiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    public void initialize() {
        Map<String, List<ProblemInfo>> problemMap = discoveryService.discoverProblems();
        problemInfoList = problemMap.values().stream()
                .flatMap(List::stream)
                .toList();

        Map<String, List<PracticeInfo>> practiceInfoMap = discoveryService.discoverPractices(problemMap);
        practiceInfoList = practiceInfoMap.values().stream()
                .flatMap(List::stream)
                .toList();
    }

    // ========================= RUN PROBLEM/PRACTICE =========================
    public Optional<ProblemResult> runProblem(String uniqueKey) {
        ProblemInfo info = problemInfoList.stream()
                .filter(p -> generateUniqueKey(p).equals(uniqueKey))
                .findFirst()
                .orElse(null);

        if (info == null)
            return Optional.empty();

        BaseProblem<?> instance = instantiateProblem(info);
        Object result = instance.solve();

        return Optional.of(new ProblemResult(info.getName(), result));
    }

    public Optional<PracticeResult> runPractice(String uniqueKey) {
        PracticeInfo info = practiceInfoList.stream()
                .filter(p -> generateUniqueKey(p).equals(uniqueKey))
                .findFirst()
                .orElse(null);

        if (info == null)
            return Optional.empty();

        BaseProblem<?> problem = instantiateProblem(info.getProblemInfo());
        BasePractice<?, ?> practice = instantiatePractice(info, problem);

        Object practiceResult = practice.practice();
        Object solutionResult = problem.solve();
        boolean isCorrect = practice.compare();

        return Optional.of(
                new PracticeResult(
                        info.getProblemInfo().getName(),
                        practiceResult,
                        solutionResult,
                        isCorrect));
    }

    // ========================= LISTING / VARIANTS =========================
    public List<String> getAvailableProblems() {
        return problemInfoList.stream()
                .map(this::generateUniqueKey)
                .sorted()
                .toList();
    }

    public List<String> getAvailablePractices() {
        return practiceInfoList.stream()
                .map(this::generateUniqueKey)
                .sorted()
                .toList();
    }

    public Map<String, List<String>> getProblemsByCategory() {
        return problemInfoList.stream()
                .collect(Collectors.groupingBy(ProblemInfo::getCategory,
                        Collectors.mapping(this::generateUniqueKey, Collectors.toList())));
    }

    public Map<String, List<String>> getPracticesByCategory() {
        return practiceInfoList.stream()
                .collect(Collectors.groupingBy(p -> p.getProblemInfo().getCategory(),
                        Collectors.mapping(this::generateUniqueKey, Collectors.toList())));
    }

    // ========================= DUPLICATE HANDLING =========================
    public List<String> getProblemVariants(String name) {
        return problemInfoList.stream()
                .filter(p -> p.getName().equals(name))
                .map(this::generateUniqueKey)
                .sorted()
                .toList();
    }

    public List<String> getPracticeVariants(String name) {
        return practiceInfoList.stream()
                .filter(p -> p.getProblemInfo().getName().equals(name))
                .map(this::generateUniqueKey)
                .sorted()
                .toList();
    }

    // ========================= HELPERS =========================
    private String generateUniqueKey(ProblemInfo info) {
        return info.getName() + " [" + info.getCategory() + "]";
    }

    private String generateUniqueKey(PracticeInfo info) {
        return info.getProblemInfo().getName() + " [" + info.getProblemInfo().getCategory() + "]";
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
}
