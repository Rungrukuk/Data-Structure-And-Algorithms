package DataStructureAndAlgorithms.infrastructure.file;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;
import DataStructureAndAlgorithms.utils.naming.NameFormatter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    public FileManager() {
    }

    public String getProblemFilePath(ProblemInfo problemInfo) {
        String categoryFolder = NameFormatter.generateCategoryFolderName(problemInfo.getCategory());
        String simpleClassName = NameFormatter.generateProblemSimpleClassName(problemInfo.getName());

        return Paths.get(
                ApplicationConstants.BASE_PROBLEM_PACKAGE,
                categoryFolder,
                simpleClassName + ApplicationConstants.JAVA_FILE_SUFFIX).toString();
    }

    public String getPracticeFilePath(PracticeInfo practiceInfo) {
        ProblemInfo problemInfo = practiceInfo.getProblemInfo();
        String categoryFolder = NameFormatter.generateCategoryFolderName(problemInfo.getCategory());
        String simpleClassName = NameFormatter.generatePracticeSimpleClassName(problemInfo.getName());

        return Paths.get(
                ApplicationConstants.BASE_PRACTICE_PACKAGE,
                categoryFolder,
                simpleClassName + ApplicationConstants.JAVA_FILE_SUFFIX).toString();
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
