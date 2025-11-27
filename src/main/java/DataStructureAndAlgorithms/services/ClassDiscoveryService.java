package DataStructureAndAlgorithms.services;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import DataStructureAndAlgorithms.core.BaseProblem;
import DataStructureAndAlgorithms.core.Problem;
import DataStructureAndAlgorithms.exceptions.InvalidProblemClassException;
import DataStructureAndAlgorithms.models.PracticeInfo;
import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.utils.Constants;
import DataStructureAndAlgorithms.utils.NamingUtils;

public class ClassDiscoveryService {

    public Map<String, ProblemInfo> discoverProblems() {
        Map<String, ProblemInfo> discoveredProblems = new HashMap<>();
        for (Class<?> clazz : getSetOfClasses(Constants.PROBLEM_PACKAGE, Problem.class)) {

            if (!BaseProblem.class.isAssignableFrom(clazz)) {
                throw new InvalidProblemClassException(
                        "Class " + clazz.getName() + " in Problems package must extend BaseProblem");
            }

            Type genericSuperClass = clazz.getGenericSuperclass();
            if (!(genericSuperClass instanceof ParameterizedType)) {
                throw new InvalidProblemClassException(
                        "Class " + clazz.getName() + " must declare a generic type for BaseProblem");
            }

            Type[] typeArgs = ((ParameterizedType) genericSuperClass).getActualTypeArguments();
            if (typeArgs.length == 0) {
                throw new InvalidProblemClassException(
                        "Class " + clazz.getName() + " must declare the generic return type");
            }

            Type returnType = typeArgs[Constants.RETURN_TYPE_POSITION];
            Problem annotation = clazz.getAnnotation(Problem.class);
            String problemName = annotation.name();
            String category = annotation.category();

            String filePath = Constants.BASE_SOURCE_PATH +
                    Constants.BASE_PACKAGE +
                    Constants.PROBLEM_PACKAGE +
                    NamingUtils.generateCategoryFolderName(category) +
                    clazz.getSimpleName();

            discoveredProblems.put(problemName,
                    new ProblemInfo(problemName, category, clazz.getName(), returnType.getTypeName(), filePath));
        }

        return discoveredProblems;
    }

    public Map<String, PracticeInfo> discoverPractices() {
        return null;
    }

    public Map<String, List<ProblemInfo>> getProblemsByCategory(Map<String, ProblemInfo> problems) {
        return problems.values().stream()
                .collect(Collectors.groupingBy(ProblemInfo::getCategory));
    }

    public Map<String, List<PracticeInfo>> getPracticesByCategory() {
        return null;
    }

    // public ProblemInfo findProblem(String problemName) {
    // return null;
    // }

    // public PracticeInfo findPractice(String practiceName) {
    // return null;
    // }

    private Set<Class<?>> getSetOfClasses(String packageName, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(
                                packageName))
                        .setScanners(Scanners.TypesAnnotated));

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(annotation);
        return classes;
    }
}
