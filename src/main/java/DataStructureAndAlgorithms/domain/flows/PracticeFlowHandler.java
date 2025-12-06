package DataStructureAndAlgorithms.domain.flows;

import DataStructureAndAlgorithms.core.models.PracticeInfo;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.practices.PracticeOrchestrator;
import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.ui.navigation.SelectionHandler;
import DataStructureAndAlgorithms.ui.prompts.Prompter;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class PracticeFlowHandler extends BaseFlowHandler<PracticeInfo> {
    private final PracticeOrchestrator orchestrator;

    public PracticeFlowHandler(
            PracticeOrchestrator orchestrator,
            UIManager ui,
            SelectionHandler selector,
            Prompter prompter) {
        super(ui, selector, prompter, selector::formatPractice);
        this.orchestrator = orchestrator;
    }

    @Override
    protected Optional<String> promptForName() {
        return prompter.promptForPracticeNameOptional();
    }

    @Override
    protected String getNotFoundMessage(String name) {
        return ApplicationConstants.DIDNOT_FIND_PRACTICE_NAME + name;
    }

    @Override
    protected List<PracticeInfo> listAllItems() {
        return orchestrator.listAllPractices();
    }

    @Override
    protected Map<String, List<PracticeInfo>> groupItemsByCategory() {
        return orchestrator.listPracticesByCategory();
    }

    @Override
    protected List<String> getAllCategories() {
        return orchestrator.getAllCategories();
    }

    @Override
    protected List<PracticeInfo> searchItemsByName(String name) {
        return orchestrator.listPracticeVariants(name);
    }

    @Override
    protected void runSelectedItem(PracticeInfo practice) {
        orchestrator.runPractice(practice)
                .ifPresentOrElse(
                        result -> {
                            System.out.println(result);
                            ui.waitForEnter();
                        },
                        () -> {
                            ui.showError("Failed to run practice: " + practice);
                            ui.waitForEnter();
                        });
    }

    @Override
    protected Function<PracticeInfo, String> getNameExtractor() {
        return practice -> practice.getProblemInfo().getName();
    }

    public void resetPractice(PracticeInfo practiceInfo) {
        orchestrator.resetPractice(practiceInfo);
        ui.showSuccess("Practice resetted successfully!");
    }

    public void createPractice(ProblemInfo problemInfo) {
        orchestrator.createPracticeForProblem(problemInfo);
        ui.showSuccess("Practice created successfully!");
    }

    public void resetAllPractices() {
        orchestrator.bulkResetPractices(this.listAllItems());
        ui.showSuccess("Practices resetted successfully!");

    }
    public void resetPracticesByCategory(List<PracticeInfo> practiceInfos){
        orchestrator.bulkResetPractices(practiceInfos);
        ui.showSuccess("Practices resetted successfully!");
    }

}
