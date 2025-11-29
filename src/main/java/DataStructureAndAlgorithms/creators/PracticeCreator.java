package DataStructureAndAlgorithms.creators;

import DataStructureAndAlgorithms.models.PracticeInfo;
import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.ProblemPracticeService;

public class PracticeCreator extends BaseClassCreator {
    public PracticeCreator(FileSystemService fileSystemService, ProblemPracticeService problemPracticeService) {
        super(fileSystemService, problemPracticeService);
    }

    public void createNewPractice() {
    }

    public void resetPractice(String practiceName) {
    }

    public void resetPracticesByCategory(String category) {
    }

    public void resetAllPractices() {
    }

    private String generatePracticeTemplate(PracticeInfo info) {
        return null;
    }
}
