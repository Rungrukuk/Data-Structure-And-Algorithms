package DataStructureAndAlgorithms.services;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import DataStructureAndAlgorithms.core.BasePractice;
import DataStructureAndAlgorithms.core.BaseProblem;
import DataStructureAndAlgorithms.core.Practice;
import DataStructureAndAlgorithms.core.Problem;
import DataStructureAndAlgorithms.exceptions.ClassDiscoveryException;
import DataStructureAndAlgorithms.models.PracticeInfo;
import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.utils.Constants;
import DataStructureAndAlgorithms.utils.NamingUtils;

public class ClassDiscoveryService {

    @FunctionalInterface
    private interface DiscoveryFactory<A extends Annotation, T> {
        T create(Class<?> clazz, A annotation, Type[] genericTypes);
    }

    // ========================= DISCOVER PROBLEMS =========================
    public Map<String, List<ProblemInfo>> discoverProblems() {
        return discoverAnnotatedClasses(
                Constants.PROBLEM_PACKAGE,
                Problem.class,
                BaseProblem.class,
                Constants.NUMBER_OF_GENERICS_PROBLEM,
                (clazz, annotation, genericTypes) -> {

                    String filePath = buildFilePath(
                            clazz.getName());

                    Type returnType = genericTypes[Constants.RETURN_TYPE_POSITION];

                    return new ProblemInfo(
                            annotation.name(),
                            annotation.category(),
                            clazz.getName(),
                            returnType.getTypeName(),
                            filePath);
                },
                Problem::name,
                ProblemInfo::getUniqueId,
                ProblemInfo::getFilePath,
                true);
    }

    // ========================= DISCOVER PRACTICES =========================
    public Map<String, List<PracticeInfo>> discoverPractices(Map<String, List<ProblemInfo>> problemInfoMap) {
        return discoverAnnotatedClasses(
                Constants.PRACTICE_PACKAGE,
                Practice.class,
                BasePractice.class,
                Constants.NUMBER_OF_GENERICS_PRACTICE,
                (clazz, annotation, genericTypes) -> {

                    List<ProblemInfo> candidates = problemInfoMap.getOrDefault(annotation.problemName(), List.of());
                    ProblemInfo matchedProblem = candidates.stream()
                            .filter(p -> p.getCategory().equals(annotation.category()))
                            .findFirst()
                            .orElseThrow(() -> new ClassDiscoveryException(
                                    "No problem found for practice: " + clazz.getName() +
                                            ", problem name: " + annotation.problemName() +
                                            ", category: " + annotation.category()));

                    String filePath = buildFilePath(
                            clazz.getName());

                    return new PracticeInfo(matchedProblem, clazz.getName(), filePath);
                },
                Practice::problemName,
                PracticeInfo::getUniqueId,
                PracticeInfo::getFilePath,
                false);
    }

    // ========================= HELPER: GENERIC DISCOVERY =========================
    private <A extends Annotation, T> Map<String, List<T>> discoverAnnotatedClasses(
            String packageName,
            Class<A> annotationType,
            Class<?> requiredSuperclass,
            int requiredGenerics,
            DiscoveryFactory<A, T> factory,
            Function<A, String> keyExtractor,
            Function<T, String> idExtractor,
            Function<T, String> filePathExtractor,
            boolean isProblem) {

        Map<String, List<T>> result = new HashMap<>();

        for (Class<?> clazz : getAnnotatedClasses(packageName, annotationType)) {

            validateSuperclass(clazz, requiredSuperclass);
            Type[] generics = extractGenerics(clazz, requiredGenerics);

            A annotation = clazz.getAnnotation(annotationType);
            T info = factory.create(clazz, annotation, generics);

            String key = keyExtractor.apply(annotation);
            validateDuplicate(result, key, info, isProblem, idExtractor, filePathExtractor);
            result.computeIfAbsent(key, k -> new ArrayList<>()).add(info);
        }

        return result;
    }

    // ========================= VALIDATION HELPERS =========================
    private void validateSuperclass(Class<?> clazz, Class<?> requiredSuperclass) {
        if (!requiredSuperclass.isAssignableFrom(clazz)) {
            throw new ClassDiscoveryException(
                    "Class " + clazz.getName() + " must extend " + requiredSuperclass.getSimpleName());
        }
    }

    private Type[] extractGenerics(Class<?> clazz, int requiredGenerics) {
        Type genericParent = clazz.getGenericSuperclass();

        if (!(genericParent instanceof ParameterizedType parameterizedType)) {
            throw new ClassDiscoveryException(
                    "Class " + clazz.getName() + " must specify generic types for "
                            + clazz.getSuperclass().getSimpleName());
        }

        Type[] typeArgs = parameterizedType.getActualTypeArguments();

        if (typeArgs.length != requiredGenerics) {
            throw new ClassDiscoveryException(
                    "Class " + clazz.getName() + " must declare " + requiredGenerics + " generic types");
        }

        return typeArgs;
    }

    private <T> void validateDuplicate(Map<String, List<T>> map, String key, T newInfo, boolean isProblem,
            Function<T, String> idExtractor, Function<T, String> filePathExtractor) {
        List<T> existing = map.get(key);
        if (existing == null)
            return;

        String newId = idExtractor.apply(newInfo);

        Optional<T> duplicate = existing.stream()
                .filter(e -> idExtractor.apply(e).equals(newId))
                .findFirst();

        if (duplicate.isPresent()) {
            String type = isProblem ? "Problem" : "Practice";
            String existingPath = filePathExtractor.apply(duplicate.get());
            String newPath = filePathExtractor.apply(newInfo);

            throw new ClassDiscoveryException(
                    "Duplicate " + type + " detected: " + NamingUtils.convertId(newId) + "\n" +
                            "Existing file: " + existingPath + "\n" +
                            "New file: " + newPath);
        }
    }

    private String buildFilePath(String className) {
        return Constants.BASE_SOURCE_PATH +
                NamingUtils.convertClassNameToPath(className);
    }

    private Set<Class<?>> getAnnotatedClasses(String packageName, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(packageName))
                        .setScanners(Scanners.TypesAnnotated));

        return reflections.getTypesAnnotatedWith(annotation);
    }

    // ========================= CATEGORY HELPERS =========================
    public Map<String, List<ProblemInfo>> getProblemsByCategory(Map<String, List<ProblemInfo>> problems) {
        return problems.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(ProblemInfo::getCategory));
    }

    public Map<String, List<PracticeInfo>> getPracticesByCategory(Map<String, List<PracticeInfo>> practices) {
        return practices.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(p -> p.getProblemInfo().getCategory()));
    }
}
