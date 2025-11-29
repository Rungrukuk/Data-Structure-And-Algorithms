package DataStructureAndAlgorithms.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.exceptions.FileCreationException;
import DataStructureAndAlgorithms.models.PracticeInfo;
import DataStructureAndAlgorithms.utils.Constants;
import DataStructureAndAlgorithms.utils.NamingUtils;

public class FileSystemService {

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
        String categoryFolder = NamingUtils.generateCategoryFolderName(info.getCategory());
        String className = NamingUtils.generateSimpleClassName(info.getName());
        return Constants.BASE_PROBLEM_PACKAGE + Constants.SLASH_SEPERATOR +
                categoryFolder + Constants.SLASH_SEPERATOR +
                className + Constants.JAVA_FILE_SUFFIX;
    }

    // ========================= PRACTICE FILE PATH =========================
    public String getPracticeFilePath(PracticeInfo info) {
        String categoryFolder = NamingUtils.generateCategoryFolderName(info.getProblemInfo().getCategory());
        String className = NamingUtils
                .generateSimpleClassName(info.getProblemInfo().getName()) + Constants.PRACTICE_CLASS_SUFFIX;
        return Constants.BASE_PRACTICE_PACKAGE + Constants.SLASH_SEPERATOR +
                categoryFolder + Constants.SLASH_SEPERATOR +
                className + Constants.JAVA_FILE_SUFFIX;
    }

    // ========================= UTILITY =========================
    public boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
}
