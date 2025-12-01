package DataStructureAndAlgorithms.infrastructure.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.exceptions.FileCreationException;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;
import DataStructureAndAlgorithms.utils.naming.NameFormatter;

public class FileSystemService_OLD {

    // ========================= FOLDER CREATION =========================
    public void createFolderIfNotExists(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new FileCreationException("Failed to create folder: " + folderPath);
        }
    }

    // ========================= FILE CREATION =========================
    public void createFile(String filePath, String content) {
        File file = new File(filePath);

        // Ensure parent folder exists
        createFolderIfNotExists(file.getParent());

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (IOException e) {
            throw new FileCreationException("Error writing file: " + filePath, e);
        }
    }

    // ========================= PROBLEM FILE PATH =========================
    public String getProblemFilePath(ProblemInfo info) {
        String categoryFolder = NameFormatter.generateCategoryFolderName(info.getCategory());
        String className = NameFormatter.generateSimpleClassName(info.getName());
        return ApplicationConstants.BASE_PROBLEM_PACKAGE + ApplicationConstants.SLASH_SEPERATOR +
                categoryFolder + ApplicationConstants.SLASH_SEPERATOR +
                className + ApplicationConstants.JAVA_FILE_SUFFIX;
    }

    // ========================= PRACTICE FILE PATH =========================
    public String getPracticeFilePath(PracticeInfo info) {
        String categoryFolder = NameFormatter.generateCategoryFolderName(info.getProblemInfo().getCategory());
        String className = NameFormatter
                .generateSimpleClassName(info.getProblemInfo().getName()) + ApplicationConstants.PRACTICE_CLASS_SUFFIX;
        return ApplicationConstants.BASE_PRACTICE_PACKAGE + ApplicationConstants.SLASH_SEPERATOR +
                categoryFolder + ApplicationConstants.SLASH_SEPERATOR +
                className + ApplicationConstants.JAVA_FILE_SUFFIX;
    }

    // ========================= UTILITY =========================
    public boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
}
