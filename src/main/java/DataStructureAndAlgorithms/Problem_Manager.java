package DataStructureAndAlgorithms;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import DataStructureAndAlgorithms.utils.HelperMethods;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class Problem_Manager {
        private final Map<String, Base_Problem<?>> problemsMap = new HashMap<>();
        private final Map<String, Base_Practice<?, ?>> practicesMap = new HashMap<>();
        private final Map<String, String> problemCategories = new HashMap<>();
        private final Map<String, String> practiceCategories = new HashMap<>();

        public Problem_Manager() {
                initializeProblemMap();
                initializePracticeMap();
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

        @SuppressWarnings("rawtypes")
        private void initializePracticeMap() {
                try {
                        Reflections reflections = new Reflections(
                                        new ConfigurationBuilder()
                                                        .setUrls(ClasspathHelper.forPackage(
                                                                        "DataStructureAndAlgorithms.Practices"))
                                                        .setScanners(Scanners.SubTypes));

                        Set<Class<? extends Base_Practice>> practiceClasses = reflections
                                        .getSubTypesOf(Base_Practice.class);

                        for (Class<? extends Base_Practice> practiceClass : practiceClasses) {
                                try {
                                        Type genericSuperclass = practiceClass.getGenericSuperclass();

                                        if (genericSuperclass instanceof ParameterizedType) {
                                                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                                                Type[] typeArguments = parameterizedType.getActualTypeArguments();

                                                if (typeArguments.length >= 2) {
                                                        Type problemType = typeArguments[1];
                                                        if (problemType instanceof Class) {
                                                                Class<?> problemClass = (Class<?>) problemType;

                                                                String problemName = extractProblemNameFromClass(
                                                                                problemClass);

                                                                Base_Problem<?> correspondingProblem = problemsMap
                                                                                .get(problemName);
                                                                if (correspondingProblem != null) {
                                                                        Constructor<? extends Base_Practice> constructor = practiceClass
                                                                                        .getDeclaredConstructor(
                                                                                                        problemClass);
                                                                        Base_Practice<?, ?> practiceInstance = constructor
                                                                                        .newInstance(correspondingProblem);
                                                                        practicesMap.put(problemName, practiceInstance);
                                                                        practiceCategories.put(problemName,
                                                                                        problemCategories.get(
                                                                                                        problemName));
                                                                } else {
                                                                        System.err.println(
                                                                                        "✗ Corresponding problem not found for practice: "
                                                                                                        +
                                                                                                        practiceClass.getSimpleName());
                                                                }
                                                        }
                                                }
                                        }
                                } catch (Exception e) {
                                        System.err.println("✗ Failed to instantiate practice "
                                                        + practiceClass.getSimpleName() + ": " + e.getMessage());
                                        e.printStackTrace();
                                }
                        }
                } catch (Exception e) {
                        System.err.println("Error during practice discovery: " + e.getMessage());
                        e.printStackTrace();
                }
        }

        private String extractProblemNameFromClass(Class<?> problemClass) {
                if (problemClass.isAnnotationPresent(Problem.class)) {
                        Problem annotation = problemClass.getAnnotation(Problem.class);
                        return annotation.name();
                }

                // Fallback: use class name without underscores and with proper casing
                String className = problemClass.getSimpleName();
                return className.replace("_", "");
        }

        // Getters for problems
        public List<String> getAvailableProblems() {
                return new ArrayList<>(problemsMap.keySet());
        }

        public Map<String, String> getProblemCategories() {
                return problemCategories;
        }

        public Map<String, List<String>> getProblemsByCategory() {
                return categorizeItems(problemsMap.keySet(), problemCategories);
        }

        // Getters for practices
        public List<String> getAvailablePractices() {
                return new ArrayList<>(practicesMap.keySet());
        }

        public Map<String, String> getPracticeCategories() {
                return practiceCategories;
        }

        public Map<String, List<String>> getPracticesByCategory() {
                return categorizeItems(practicesMap.keySet(), practiceCategories);
        }

        private Map<String, List<String>> categorizeItems(Set<String> items, Map<String, String> categories) {
                Map<String, List<String>> categorized = new TreeMap<>();

                for (String item : items) {
                        String category = categories.get(item);
                        if (category != null) {
                                categorized.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
                        }
                }

                // Sort items within each category
                for (List<String> itemList : categorized.values()) {
                        Collections.sort(itemList);
                }

                return categorized;
        }

        // Run methods
        public void runProblem(String problemName) {
                Base_Problem<?> problemToRun = problemsMap.get(problemName);
                if (problemToRun != null) {
                        System.out.println("\n=== Running " + HelperMethods.formatProblemName(problemName) + " ===");
                        System.out.println("Solution: " + problemToRun.solve());
                } else {
                        System.out.println("Problem not found: " + problemName);
                }
        }

        public void runPractice(String problemName) {
                Base_Practice<?, ?> practiceToRun = practicesMap.get(problemName);
                if (practiceToRun != null) {
                        System.out.println("\n=== Practicing " + HelperMethods.formatProblemName(problemName) + " ===");

                        // Get both results
                        Object practiceResult = practiceToRun.getPracticeResult();
                        Object solutionResult = practiceToRun.getSolutionResult();

                        System.out.println("Your answer: " + practiceResult);
                        System.out.println("Expected answer: " + solutionResult);
                        System.out.println("Result: " + (practiceToRun.compare() ? "✅ CORRECT" : "❌ INCORRECT"));
                } else {
                        System.out.println("Practice not found for: " + problemName);
                        System.out.println("Available practices: " + getAvailablePractices());
                }
        }
}