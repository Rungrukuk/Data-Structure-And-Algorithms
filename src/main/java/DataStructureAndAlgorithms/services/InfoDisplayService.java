package DataStructureAndAlgorithms.services;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.problems.ProblemPracticeService_OLD;

public class InfoDisplayService {
    private final ProblemPracticeService_OLD problemPracticeService;

    // Display formatters
    private final Function<ProblemInfo, String> problemFormatter;
    private final Function<PracticeInfo, String> practiceFormatter;

    public InfoDisplayService(ProblemPracticeService_OLD problemPracticeService) {
        this.problemPracticeService = problemPracticeService;

        // Default formatters - can be customized if needed
        this.problemFormatter = ProblemInfo::getUniqueId;
        this.practiceFormatter = PracticeInfo::getUniqueId;
    }

    public InfoDisplayService(
            ProblemPracticeService_OLD problemPracticeService,
            Function<ProblemInfo, String> problemFormatter,
            Function<PracticeInfo, String> practiceFormatter) {

        this.problemPracticeService = problemPracticeService;
        this.problemFormatter = problemFormatter;
        this.practiceFormatter = practiceFormatter;
    }

    // ========================= PROBLEM LISTINGS =========================

    public List<ProblemInfo> getAllProblemInfos() {
        return problemPracticeService.getProblemInfoList();
    }

    public List<String> getAllProblemDisplayStrings() {
        return formatProblems(getAllProblemInfos());
    }

    public Map<String, List<ProblemInfo>> getProblemsByCategory() {
        return problemPracticeService.groupProblemsByCategory();
    }

    public Map<String, List<String>> getProblemDisplaysByCategory() {
        return transformMap(getProblemsByCategory(), this::formatProblems);
    }

    public List<ProblemInfo> findProblemInfosByName(String name) {
        return problemPracticeService.findProblemsByName(name);
    }

    public List<String> findProblemDisplayStringsByName(String name) {
        return formatProblems(findProblemInfosByName(name));
    }

    // ========================= PRACTICE LISTINGS =========================

    public List<PracticeInfo> getAllPracticeInfos() {
        return problemPracticeService.getPracticeInfoList();
    }

    public List<String> getAllPracticeDisplayStrings() {
        return formatPractices(getAllPracticeInfos());
    }

    public Map<String, List<PracticeInfo>> getPracticesByCategory() {
        return problemPracticeService.groupPracticesByCategory();
    }

    public Map<String, List<String>> getPracticeDisplaysByCategory() {
        return transformMap(getPracticesByCategory(), this::formatPractices);
    }

    public List<PracticeInfo> findPracticeInfosByName(String name) {
        return problemPracticeService.findPracticesByName(name);
    }

    public List<String> findPracticeDisplayStringsByName(String name) {
        return formatPractices(findPracticeInfosByName(name));
    }

    // ========================= FORMATTING HELPERS =========================

    private List<String> formatProblems(List<ProblemInfo> problems) {
        return problems.stream()
                .map(problemFormatter)
                .sorted()
                .collect(Collectors.toList());
    }

    private List<String> formatPractices(List<PracticeInfo> practices) {
        return practices.stream()
                .map(practiceFormatter)
                .sorted()
                .collect(Collectors.toList());
    }

    private <K, V, R> Map<K, List<R>> transformMap(
            Map<K, List<V>> originalMap,
            Function<List<V>, List<R>> transformer) {

        return originalMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> transformer.apply(entry.getValue())));
    }

    // ========================= CUSTOM FORMATTERS =========================

    public static Function<ProblemInfo, String> createDetailedProblemFormatter() {
        return info -> String.format("%s [Category: %s, Type: %s]",
                info.getName(),
                info.getCategory(),
                info.getReturnType());
    }

    public static Function<PracticeInfo, String> createDetailedPracticeFormatter() {
        return info -> {
            ProblemInfo problem = info.getProblemInfo();
            return String.format("%s [Category: %s, Practice]",
                    problem.getName(),
                    problem.getCategory());
        };
    }

    public static Function<ProblemInfo, String> createSimpleProblemFormatter() {
        return ProblemInfo::getUniqueId;
    }

    public static Function<PracticeInfo, String> createSimplePracticeFormatter() {
        return PracticeInfo::getUniqueId;
    }

    // ========================= FIND BY DISPLAY STRING =========================

    public Optional<ProblemInfo> findProblemByDisplayString(String displayString) {
        return getAllProblemInfos().stream()
                .filter(info -> problemFormatter.apply(info).equals(displayString))
                .findFirst();
    }

    public Optional<PracticeInfo> findPracticeByDisplayString(String displayString) {
        return getAllPracticeInfos().stream()
                .filter(info -> practiceFormatter.apply(info).equals(displayString))
                .findFirst();
    }

    // ========================= MUTATION METHODS =========================
    // These delegate to ProblemPracticeService

    public void addCreatedProblem(ProblemInfo problemInfo) {
        problemPracticeService.addCreatedProblem(problemInfo);
    }

    public void removeCreatedProblem(ProblemInfo problemInfo) {
        problemPracticeService.removeCreatedProblem(problemInfo);
    }

    public void addCreatedPractice(PracticeInfo practiceInfo) {
        problemPracticeService.addCreatedPractice(practiceInfo);
    }

    public void removeCreatedPractice(PracticeInfo practiceInfo) {
        problemPracticeService.removeCreatedPractice(practiceInfo);
    }

    public boolean problemExists(String name, String category) {
        return problemPracticeService.problemExists(name, category);
    }

    public boolean practiceExists(String name, String category) {
        return problemPracticeService.practiceExists(name, category);
    }

    public Optional<ProblemInfo> findProblemByUniqueId(String uniqueId) {
        return problemPracticeService.findProblemByUniqueId(uniqueId);
    }

    public Optional<PracticeInfo> findPracticeByUniqueId(String uniqueId) {
        return problemPracticeService.findPracticeByUniqueId(uniqueId);
    }

    public Optional<ProblemInfo> findProblem(String name, String category) {
        return problemPracticeService.findProblem(name, category);
    }

    public Optional<PracticeInfo> findPractice(String name, String category) {
        return problemPracticeService.findPractice(name, category);
    }
}