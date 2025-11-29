package DataStructureAndAlgorithms.creators;

import DataStructureAndAlgorithms.models.ProblemInfo;
import DataStructureAndAlgorithms.services.FileSystemService;
import DataStructureAndAlgorithms.services.ProblemPracticeService;

public class ProblemCreator extends BaseClassCreator {

    public ProblemCreator(FileSystemService fileSystemService, ProblemPracticeService problemPracticeService) {
        super(fileSystemService, problemPracticeService);
    }

    public void createNewProblem() {
    }

    private String generateProblemTemplate(ProblemInfo info) {
        return null;
    }
}
