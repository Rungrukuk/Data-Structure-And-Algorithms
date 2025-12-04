package DataStructureAndAlgorithms.domain.flows;

import DataStructureAndAlgorithms.ui.UIManager;
import DataStructureAndAlgorithms.ui.navigation.SelectionHandler;
import DataStructureAndAlgorithms.ui.navigation.SelectionHandler.ItemFormatter;
import DataStructureAndAlgorithms.ui.prompts.Prompter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        List<TInfo> items = listAllItems();
        if (items.isEmpty()) {
            ui.showError("No items available.");
            ui.waitForEnter();
            return;
        }

        Optional<TInfo> selected = select(items, "Select Item to Run");
        selected.ifPresent(this::runSelectedItem);
    }

    public void runByCategory() {
        Map<String, List<TInfo>> itemsByCategory = groupItemsByCategory();
        if (itemsByCategory.isEmpty()) {
            ui.showError("No items available.");
            ui.waitForEnter();
            return;
        }

        List<String> categories = getAllCategories();
        Optional<String> selectedCategory = selector.selectCategory(categories, "Select Category");
        if (selectedCategory.isEmpty()) {
            return;
        }

        List<TInfo> itemsInCategory = itemsByCategory.get(selectedCategory.get());
        if (itemsInCategory == null || itemsInCategory.isEmpty()) {
            ui.showError("No items found in category: " + selectedCategory.get());
            ui.waitForEnter();
            return;
        }

        Optional<TInfo> selected = select(itemsInCategory, "Select Item from " + selectedCategory.get());
        selected.ifPresent(this::runSelectedItem);
    }

    public final void runByName() {
        Optional<String> nameOptional = promptForName();
        if (shouldReturn(nameOptional)) {
            return;
        }
        // TODO need to implement show suggestions
        String name = nameOptional.get();
        List<TInfo> variants = searchItemsByName(name);
        handleSearchResults(name, variants);
    }

    protected abstract Optional<String> promptForName();

    protected abstract String getNotFoundMessage(String name);

    protected abstract List<TInfo> listAllItems();

    protected abstract Map<String, List<TInfo>> groupItemsByCategory();

    protected abstract List<String> getAllCategories();

    protected abstract List<TInfo> searchItemsByName(String name);

    protected abstract void runSelectedItem(TInfo item);

    protected boolean shouldReturn(Optional<String> nameOptional) {
        return nameOptional.isEmpty();
    }

    protected void handleSearchResults(String name, List<TInfo> variants) {
        if (variants.isEmpty()) {
            ui.showError(getNotFoundMessage(name));
            ui.waitForEnter();
            return;
        }

        if (variants.size() == 1) {
            runSelectedItem(variants.get(0));
            return;
        }

        Optional<TInfo> selected = select(variants, "Select Item Variant");
        selected.ifPresent(this::runSelectedItem);
    }
}