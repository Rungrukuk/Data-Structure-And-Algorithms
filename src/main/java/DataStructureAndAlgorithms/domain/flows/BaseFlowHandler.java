package DataStructureAndAlgorithms.domain.flows;

import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.ui.navigation.SelectionHandler;
import DataStructureAndAlgorithms.ui.navigation.SelectionHandler.ItemFormatter;
import DataStructureAndAlgorithms.ui.prompts.Prompter;
import DataStructureAndAlgorithms.utils.constants.ApplicationConstants;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class BaseFlowHandler<TInfo> {
    protected final UIManager ui;
    protected final SelectionHandler selector;
    protected final Prompter prompter;
    protected final ItemFormatter<TInfo> formatter;

    protected BaseFlowHandler(UIManager ui, SelectionHandler selector, Prompter prompter,
            ItemFormatter<TInfo> formatter) {
        this.ui = ui;
        this.selector = selector;
        this.prompter = prompter;
        this.formatter = formatter;
    }

    protected Optional<TInfo> select(List<TInfo> items, String prompt) {
        return selector.selectItem(items, prompt, formatter);
    }

    public void runFromAll() {
        listAndSelectFromAll().ifPresent(this::runSelectedItem);
    }

    public Optional<TInfo> listAndSelectFromAll() {
        List<TInfo> items = listAllItems();
        if (items.isEmpty()) {
            ui.showError("No items available.");
            ui.waitForEnter();
            return Optional.empty();
        }

        return select(items, "Select Item to Run");
    }

    public Optional<TInfo> listAndSelectByCategory() {
        Optional<List<TInfo>> itemsOpt = getItemsBySelectedCategory();
        if (itemsOpt.isEmpty()) {
            return Optional.empty();
        }

        List<TInfo> items = itemsOpt.get();
        return select(items, "Select Item");
    }



    public Optional<List<TInfo>> getItemsBySelectedCategory() {
        Map<String, List<TInfo>> itemsByCategory = groupItemsByCategory();
        if (itemsByCategory.isEmpty()) {
            ui.showError("No items available.");
            ui.waitForEnter();
            return Optional.empty();
        }

        List<String> categories = getAllCategories();
        Optional<String> selectedCategory = selector.selectCategory(categories);

        if (selectedCategory.isEmpty()) {
            return Optional.empty();
        }

        List<TInfo> itemsInCategory = itemsByCategory.get(selectedCategory.get());
        if (itemsInCategory == null || itemsInCategory.isEmpty()) {
            ui.showError("No items found in category: " + selectedCategory.get());
            ui.waitForEnter();
            return Optional.empty();
        }

        return Optional.of(itemsInCategory);
    }



    public void runByCategory() {
        listAndSelectByCategory().ifPresent(this::runSelectedItem);
    }

    public Optional<TInfo> listAndSelectByName() {
        Optional<String> nameOptional = Optional.empty();
        while (nameOptional.isEmpty()) {
            nameOptional = promptForName();
        }
        if (shouldReturn(nameOptional.get())) {
            return Optional.empty();
        }
        String name = nameOptional.get();
        List<TInfo> variants = searchItemsByName(name);
        return getSuggestionOptional(name, variants);
    }

    public final void runByName() {
        listAndSelectByName().ifPresent(this::runSelectedItem);
    }

    protected abstract Optional<String> promptForName();

    protected abstract String getNotFoundMessage(String name);

    protected abstract List<TInfo> listAllItems();

    protected abstract Map<String, List<TInfo>> groupItemsByCategory();

    protected abstract List<String> getAllCategories();

    protected abstract List<TInfo> searchItemsByName(String name);

    protected abstract void runSelectedItem(TInfo item);

    protected abstract Function<TInfo, String> getNameExtractor();

    protected boolean shouldReturn(String nameOptional) {
        return nameOptional.equals(ApplicationConstants.RETURN_BACK);
    }

    protected Optional<TInfo> getSuggestionOptional(String name, List<TInfo> variants) {
        if (variants.isEmpty()) {
            ui.showError(getNotFoundMessage(name));
            return handleSimilarItems(name);
        }

        if (variants.size() == 1) {
            return Optional.of(variants.getFirst());
        }

        ui.showInfo("Multiple items found with name: " + name);
        return select(variants, "Select which item to run");
    }

    private Optional<TInfo> handleSimilarItems(String name) {
        List<TInfo> similarItems = selector.showSimilarSuggestions(name, listAllItems(),
                getNameExtractor(), formatter);

        if (similarItems.isEmpty()) {
            return Optional.empty();
        }

        String message = similarItems.size() == 1
                ? "Would you like to run this item?"
                : "Would you like to run one of these items?";

        if (!getUserConfirmation(message)) {
            ui.showInfo("Operation cancelled.");
            return Optional.empty();
        }

        if (similarItems.size() == 1) {
            return Optional.of(similarItems.getFirst());
        } else {
            return select(similarItems, "Select which item to run");
        }
    }

    private boolean getUserConfirmation(String message) {
        while (true) {
            Optional<Boolean> confirmation = prompter.promptForConfirmationOptional(message);
            if (confirmation.isPresent()) {
                return confirmation.get();
            }
        }
    }
}
