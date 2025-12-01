package DataStructureAndAlgorithms.infrastructure.file;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;
import DataStructureAndAlgorithms.utils.naming.NameFormatter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private final String basePackage;

    public FileManager(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getProblemFilePath(ProblemInfo problemInfo) {
        String categoryFolder = NameFormatter.generateCategoryFolderName(problemInfo.getCategory());
        String simpleClassName = NameFormatter.generateSimpleClassName(problemInfo.getName());

        return Paths.get(
                ApplicationConstants.BASE_SOURCE_PATH,
                basePackage.replace(".", "/"),
                "Problems",
                categoryFolder,
                simpleClassName + ApplicationConstants.JAVA_FILE_SUFFIX).toString();
    }

    public String getPracticeFilePath(PracticeInfo practiceInfo) {
        ProblemInfo problemInfo = practiceInfo.getProblemInfo();
        String categoryFolder = NameFormatter.generateCategoryFolderName(problemInfo.getCategory());
        String simpleClassName = NameFormatter.generateSimpleClassName(problemInfo.getName()) +
                ApplicationConstants.PRACTICE_CLASS_SUFFIX;

        return Paths.get(
                ApplicationConstants.BASE_SOURCE_PATH,
                basePackage.replace(".", "/"),
                "Practices",
                categoryFolder,
                simpleClassName + ApplicationConstants.JAVA_FILE_SUFFIX).toString();
    }

    public String getFullPackagePath(String packageName) {
        return Paths.get(
                ApplicationConstants.BASE_SOURCE_PATH,
                packageName.replace(".", "/")).toString();
    }

    public boolean exists(String filePath) {
        return Paths.get(filePath).toFile().exists();
    }

    public void createDirectories(String filePath) {
        Path parentDir = Paths.get(filePath).getParent();
        if (parentDir != null) {
            parentDir.toFile().mkdirs();
        }
    }
}