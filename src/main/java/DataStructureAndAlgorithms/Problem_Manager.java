package DataStructureAndAlgorithms;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.util.*;

public class Problem_Manager {
        private final Map<String, Base_Problem<?>> problemsMap = new HashMap<>();
        private final Map<String, String> problemCategories = new HashMap<>();
        private final Map<String, Base_Test<?, ?>> testsMap = new HashMap<>();
        private final boolean isTest;
        private final Base_Problem<?> problem;
        private Base_Test<?, ?> testObject;

        public Problem_Manager(String problemName, boolean isTest) {
                this.isTest = isTest;
                initializeProblemMap();
                this.problem = problemsMap.get(problemName);
                if (problem == null) {
                        throw new IllegalArgumentException("Problem not found: " + problemName);
                }
                if (isTest) {
                        initializeTestMap();
                        this.testObject = testsMap.get(problemName);
                }
        }

        public Problem_Manager(String problemName) {
                this(problemName, false);
        }

        public Problem_Manager() {
                this.isTest = false;
                initializeProblemMap();
                this.problem = null;
        }

        private void initializeProblemMap() {
                try {
                        Reflections reflections = new Reflections(
                                        new ConfigurationBuilder()
                                                        .setUrls(ClasspathHelper.forPackage(
                                                                        "DataStructureAndAlgorithms.Problems"))
                                                        .setScanners(Scanners.TypesAnnotated));

                        Set<Class<?>> problemClasses = reflections.getTypesAnnotatedWith(Problem.class);

                        for (Class<?> clazz : problemClasses) {
                                if (Base_Problem.class.isAssignableFrom(clazz)) {
                                        Problem annotation = clazz.getAnnotation(Problem.class);
                                        String problemName = annotation.name();
                                        String category = annotation.category();

                                        try {
                                                Constructor<?> constructor = clazz.getDeclaredConstructor();
                                                Base_Problem<?> problemInstance = (Base_Problem<?>) constructor
                                                                .newInstance();
                                                problemsMap.put(problemName, problemInstance);
                                                problemCategories.put(problemName, category);
                                        } catch (Exception e) {
                                                System.err.println("✗ Failed to instantiate " + clazz.getSimpleName()
                                                                + ": " + e.getMessage());
                                        }
                                }
                        }

                } catch (Exception e) {
                        System.err.println("Error during problem discovery: " + e.getMessage());
                }
        }

        private void initializeTestMap() {

        }

        public List<String> getAvailableProblems() {
                return new ArrayList<>(problemsMap.keySet());
        }

        public Map<String, String> getProblemCategories() {
                return problemCategories;
        }

        public void run() {
                if (problem == null) {
                        System.out.println("No problem selected.");
                        return;
                }

                if (isTest) {
                        System.out.println("Expected answer: " + problem.solve());
                        System.out.println("Your answer : " + testObject.test());
                        System.out.println("Result: " + testObject.compare());
                } else {
                        System.out.println("Answer: " + problem.solve());
                }
        }

        public void runProblem(String problemName) {
                Base_Problem<?> problemToRun = problemsMap.get(problemName);
                if (problemToRun != null) {
                        System.out.println("\n=== Running " + problemName + " ===");
                        System.out.println("Answer: " + problemToRun.solve());
                } else {
                        System.out.println("Problem not found: " + problemName);
                }
        }

        // Get problems grouped by category
        public Map<String, List<String>> getProblemsByCategory() {
                Map<String, List<String>> categorized = new TreeMap<>();

                for (Map.Entry<String, String> entry : problemCategories.entrySet()) {
                        String problemName = entry.getKey();
                        String category = entry.getValue();

                        categorized.computeIfAbsent(category, k -> new ArrayList<>()).add(problemName);
                }

                // Sort problem names within each category
                for (List<String> problems : categorized.values()) {
                        Collections.sort(problems);
                }

                return categorized;
        }
}