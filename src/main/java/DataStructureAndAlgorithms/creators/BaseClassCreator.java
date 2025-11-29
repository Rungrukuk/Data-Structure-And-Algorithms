package DataStructureAndAlgorithms.creators;

import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.ProblemPracticeService;

public class BaseClassCreator {
    protected final FileSystemService fileSystemService;
    protected final ProblemPracticeService problemPracticeService;

    public BaseClassCreator(FileSystemService fileSystemService, ProblemPracticeService problemPracticeService) {
        this.fileSystemService = fileSystemService;
        this.problemPracticeService = problemPracticeService;
    }

    protected String generateClassContent(ProblemInfo problemInfo, String template) {
        return null;
    }

    protected void showSuccessMessage(String className, String filePath) {
    }

    protected void showErrorMessage(String message) {
    }
}
