package DataStructureAndAlgorithms;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import DataStructureAndAlgorithms.utils.HelperMethods;

import java.lang.reflect.Constructor;
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

        private void initializePracticeMap() {
                try {
                        Reflections reflections = new Reflections(
                                        new ConfigurationBuilder()
                                                        .setUrls(ClasspathHelper.forPackage(
                                                                        "DataStructureAndAlgorithms.Practices"))
                                                        .setScanners(Scanners.TypesAnnotated));

                        // Find all classes annotated with @Practice
                        Set<Class<?>> practiceClasses = reflections.getTypesAnnotatedWith(Practice.class);

                        for (Class<?> practiceClass : practiceClasses) {
                                // Verify it extends Base_Practice
                                if (Base_Practice.class.isAssignableFrom(practiceClass)) {
                                        @SuppressWarnings("unchecked")
                                        Class<? extends Base_Practice<?, ?>> typedClass = (Class<? extends Base_Practice<?, ?>>) practiceClass;
                                        registerPracticeClass(typedClass);
                                } else {
                                        System.err.println("✗ Class " + practiceClass.getSimpleName() +
                                                        " has @Practice annotation but does not extend Base_Practice");
                                }
                        }
                } catch (Exception e) {
                        System.err.println("Error during practice discovery: " + e.getMessage());
                        e.printStackTrace();
                }
        }

        private void registerPracticeClass(Class<? extends Base_Practice<?, ?>> practiceClass) {
                try {
                        Practice annotation = practiceClass.getAnnotation(Practice.class);
                        String problemName = annotation.problemName();
                        String category = annotation.category();

                        // Find the corresponding problem
                        Base_Problem<?> correspondingProblem = problemsMap.get(problemName);
                        if (correspondingProblem == null) {
                                System.err.println("✗ Corresponding problem not found for practice: " +
                                                practiceClass.getSimpleName() + " (problem: " + problemName + ")");
                                return;
                        }

                        // Find the constructor that takes the problem type
                        Constructor<? extends Base_Practice<?, ?>> constructor = findSuitableConstructor(practiceClass,
                                        correspondingProblem.getClass());
                        if (constructor == null) {
                                System.err.println("✗ No suitable constructor found for "
                                                + practiceClass.getSimpleName() +
                                                " that takes " + correspondingProblem.getClass().getSimpleName());
                                return;
                        }

                        // Create the practice instance
                        Base_Practice<?, ?> practiceInstance = constructor.newInstance(correspondingProblem);
                        practicesMap.put(problemName, practiceInstance);
                        practiceCategories.put(problemName, category);

                        System.out.println("✓ Registered practice: " + problemName + " (" + category + ")");

                } catch (Exception e) {
                        System.err.println("✗ Failed to register practice " + practiceClass.getSimpleName() + ": "
                                        + e.getMessage());
                        e.printStackTrace();
                }
        }

        private Constructor<? extends Base_Practice<?, ?>> findSuitableConstructor(
                        Class<? extends Base_Practice<?, ?>> practiceClass,
                        Class<?> problemClass) {

                // Try to find a constructor that takes the exact problem class
                try {
                        return practiceClass.getDeclaredConstructor(problemClass);
                } catch (NoSuchMethodException e1) {
                        // Fallback: try to find a constructor that takes Base_Problem
                        try {
                                return practiceClass.getDeclaredConstructor(Base_Problem.class);
                        } catch (NoSuchMethodException e2) {
                                // Last resort: try any constructor and hope for the best
                                for (Constructor<?> constructor : practiceClass.getDeclaredConstructors()) {
                                        if (constructor.getParameterCount() == 1) {
                                                Class<?> paramType = constructor.getParameterTypes()[0];
                                                if (Base_Problem.class.isAssignableFrom(paramType)) {
                                                        @SuppressWarnings("unchecked")
                                                        Constructor<? extends Base_Practice<?, ?>> safeConstructor = (Constructor<? extends Base_Practice<?, ?>>) constructor;
                                                        return safeConstructor;
                                                }
                                        }
                                }
                                return null;
                        }
                }
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