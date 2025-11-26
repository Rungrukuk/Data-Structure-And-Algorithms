package DataStructureAndAlgorithms.services;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private Map<String, ProblemInfo> problemsMap; // TODO Maybe need to be method variable

    public Map<String, ProblemInfo> discoverProblems() {
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

            problemsMap.put(problemName,
                    new ProblemInfo(problemName, category, clazz.getName(), returnType.getTypeName(), filePath));
        }

        return problemsMap;
    }

    public Map<String, PracticeInfo> discoverPractices() {
        return null;
    }

    public Map<String, List<ProblemInfo>> getProblemsByCategory() {
        return null;
    }

    public Map<String, List<PracticeInfo>> getPracticesByCategory() {
        return null;
    }

    public ProblemInfo findProblem(String problemName) {
        return problemsMap.get(problemName);
    }

    public PracticeInfo findPractice(String practiceName) {
        return null;
    }

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
