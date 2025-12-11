package DataStructureAndAlgorithms.domain.flows;

import DataStructureAndAlgorithms.core.exceptions.ValidationException;
import DataStructureAndAlgorithms.core.models.ProblemInfo;
import DataStructureAndAlgorithms.domain.problems.ProblemOrchestrator;
import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.ui.navigation.SelectionHandler;
import DataStructureAndAlgorithms.ui.prompts.Prompter;
import DataStructureAndAlgorithms.utils.ApplicationConstants;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ProblemFlowHandler extends BaseFlowHandler<ProblemInfo> {
    private final ProblemOrchestrator orchestrator;

    public ProblemFlowHandler(
            ProblemOrchestrator orchestrator,
            UIManager ui,
            SelectionHandler selector,
            Prompter prompter) {
        super(ui, selector, prompter, selector::formatProblem);
        this.orchestrator = orchestrator;
    }

    @Override
    protected Optional<String> promptForName() {
        return prompter.promptForProblemNameOptional();
    }

    @Override
    protected String getNotFoundMessage(String name) {
        return ApplicationConstants.DIDNOT_FIND_PROBLEM_NAME + name;
    }

    @Override
    protected List<ProblemInfo> listAllItems() {
        return orchestrator.listAllProblems();
    }

    @Override
    protected Map<String, List<ProblemInfo>> groupItemsByCategory() {
        return orchestrator.listProblemsByCategory();
    }

    @Override
    protected List<String> getAllCategories() {
        return orchestrator.getAllCategories();
    }

    @Override
    protected List<ProblemInfo> searchItemsByName(String name) {
        return orchestrator.listProblemVariants(name);
    }

    @Override
    protected void runSelectedItem(ProblemInfo problem) {
        orchestrator.runProblem(problem)
                .ifPresentOrElse(
                        result -> {
                            System.out.println(result);
                            ui.waitForEnter();
                        },
                        () -> {
                            ui.showError("Failed to run problem: " + problem);
                            ui.waitForEnter();
                        });
    }

    @Override
    protected Function<ProblemInfo, String> getNameExtractor() {
        return ProblemInfo::getName;
    }

    public void createProblem() {
        ui.showSectionTitle("CREATE PROBLEM");
        try {
            Optional<String> name = Optional.empty();
            Optional<String> category = Optional.empty();
            Optional<String> difficulty = Optional.empty();
            Optional<String> returnType = Optional.empty();

            while (name.isEmpty()) {
                name = prompter.promptForProblemNameOptional();
            }
            if (shouldReturn(name.get())) {
                return;
            }
            while (category.isEmpty()) {
                category = prompter.promptForCategoryNameOptional();
            }
            if (shouldReturn(category.get())) {
                return;
            }
            while (difficulty.isEmpty()) {
                difficulty = prompter.promptForDifficultyOptional();
            }
            if (shouldReturn(difficulty.get())) {
                return;
            }

            while (returnType.isEmpty()) {
                returnType = prompter.promptForReturnTypeOptional();
            }
            if (shouldReturn(returnType.get())) {
                return;
            }

            if (orchestrator.problemExists(name.get(), category.get())) {
                ui.showError("A problem with this name and category already exists.");
                ui.waitForEnter();
                return;
            }

            orchestrator.createNewProblem(name.get(), category.get(), returnType.get(), difficulty.get());
            ui.showSuccess("Problem created successfully!");
        } catch (ValidationException e) {
            ui.showError("Validation error: " + e.getMessage());
        } catch (Exception e) {
            ui.showError("Failed to create problem: " + e.getMessage());
        }
        ui.waitForEnter();
    }
}
