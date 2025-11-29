package DataStructureAndAlgorithms.services;

import java.util.*;
import java.util.stream.Collectors;

import DataStructureAndAlgorithms.models.*;

public class ProblemPracticeService {

    private final ClassDiscoveryService discoveryService;

    private List<ProblemInfo> problemInfoList = new ArrayList<>();
    private List<PracticeInfo> practiceInfoList = new ArrayList<>();

    public ProblemPracticeService(ClassDiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
        initialize();
    }

    // ============ INITIALIZATION / REFRESH ============
    public final void initialize() {
        Map<String, List<ProblemInfo>> problemMap = discoveryService.discoverProblems();
        problemInfoList = new ArrayList<>(problemMap.values().stream()
                .flatMap(List::stream)
                .toList());

        Map<String, List<PracticeInfo>> practiceMap = discoveryService.discoverPractices(problemMap);
        practiceInfoList = new ArrayList<>(practiceMap.values().stream()
                .flatMap(List::stream)
                .toList());
    }

    public void refresh() {
        initialize();
    }

    public void addCreatedProblem(ProblemInfo problemInfo) {
        problemInfoList.add(problemInfo);
    }

    public void removeCreatedProblem(ProblemInfo problemInfo) {
        problemInfoList.remove(problemInfo);
    }

    public void addCreatedPractice(PracticeInfo practiceInfo) {
        practiceInfoList.add(practiceInfo);
    }

    public void removeCreatedPractice(PracticeInfo practiceInfo) {
        practiceInfoList.remove(practiceInfo);
    }

    // ============ GETTERS (SHALLOW COPY TO PROTECT INTERNAL STATE) ============
    public List<ProblemInfo> getProblemInfoList() {
        return new ArrayList<>(problemInfoList);
    }

    public List<PracticeInfo> getPracticeInfoList() {
        return new ArrayList<>(practiceInfoList);
    }

    // ============ EXISTENCE CHECKS (FOR CREATORS) ============
    public boolean problemExists(String name, String category) {
        return problemInfoList.stream()
                .anyMatch(p -> p.getName().equals(name) &&
                        p.getCategory().equals(category));
    }

    public boolean practiceExists(String name, String category) {
        return practiceInfoList.stream()
                .anyMatch(p -> {
                    ProblemInfo problem = p.getProblemInfo();
                    return problem.getName().equals(name) &&
                            problem.getCategory().equals(category);
                });
    }

    // ============ FINDING PROBLEM / PRACTICE BY NAME & CATEGORY ============
    public Optional<ProblemInfo> findProblem(String name, String category) {
        return problemInfoList.stream()
                .filter(p -> p.getName().equals(name) &&
                        p.getCategory().equals(category))
                .findFirst();
    }

    public Optional<PracticeInfo> findPractice(String name, String category) {
        return practiceInfoList.stream()
                .filter(p -> {
                    ProblemInfo problem = p.getProblemInfo();
                    return problem.getName().equals(name) &&
                            problem.getCategory().equals(category);
                })
                .findFirst();
    }

    // ============ FINDING BY UNIQUE ID ============
    public Optional<ProblemInfo> findProblemByUniqueId(String uniqueId) {
        return problemInfoList.stream()
                .filter(p -> p.getUniqueId().equals(uniqueId))
                .findFirst();
    }

    public Optional<PracticeInfo> findPracticeByUniqueId(String uniqueId) {
        return practiceInfoList.stream()
                .filter(p -> p.getUniqueId().equals(uniqueId))
                .findFirst();
    }

    // ============ FIND MULTIPLE VARIANTS (BY NAME ONLY) ============
    public List<ProblemInfo> findProblemsByName(String name) {
        return problemInfoList.stream()
                .filter(p -> p.getName().equals(name))
                .toList();
    }

    public List<PracticeInfo> findPracticesByName(String name) {
        return practiceInfoList.stream()
                .filter(p -> p.getProblemInfo().getName().equals(name))
                .toList();
    }

    // ============ GROUP BY CATEGORY ============
    public Map<String, List<ProblemInfo>> groupProblemsByCategory() {
        return problemInfoList.stream()
                .collect(Collectors.groupingBy(ProblemInfo::getCategory));
    }

    public Map<String, List<PracticeInfo>> groupPracticesByCategory() {
        return practiceInfoList.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getProblemInfo().getCategory()));
    }
}
