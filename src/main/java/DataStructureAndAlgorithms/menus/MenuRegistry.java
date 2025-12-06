package DataStructureAndAlgorithms.menus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuRegistry {
    public static final MenuOption RETURN = new MenuOption(MenuKey.RETURN, "Return Back");
    public static final MenuOption EXIT = new MenuOption(MenuKey.EXIT, "Exit");

    public static final MenuOption RUN_PROBLEMS = new MenuOption(MenuKey.RUN_PROBLEMS, "Run Problems & Practices");
    public static final MenuOption CREATE_PROBLEM = new MenuOption(MenuKey.CREATE_PROBLEM, "Create New Problem");
    public static final MenuOption MANAGE_PRACTICES = new MenuOption(MenuKey.MANAGE_PRACTICES, "Manage Practices");

    public static final MenuOption LIST_ALL_PROBLEMS = new MenuOption(MenuKey.LIST_ALL_PROBLEMS, "List All Problems");
    public static final MenuOption LIST_PROBLEMS_BY_CATEGORY = new MenuOption(MenuKey.LIST_PROBLEMS_BY_CATEGORY,
            "List Problems By Category");

    public static final MenuOption CREATE_PRACTICE = new MenuOption(MenuKey.CREATE_PRACTICE, "Create Practice Class");
    public static final MenuOption RESET_PRACTICE = new MenuOption(MenuKey.RESET_PRACTICE, "Reset Practices");

    public static final MenuOption LIST_ALL_PRACTICES = new MenuOption(MenuKey.LIST_ALL_PRACTICES,
            "List All Practices");
    public static final MenuOption LIST_PRACTICES_BY_CATEGORY = new MenuOption(MenuKey.LIST_PRACTICES_BY_CATEGORY,
            "List Practices By Category");
    public static final MenuOption FIND_SPECIFIC_PRACTICE = new MenuOption(MenuKey.FIND_SPECIFIC_PRACTICE,
            "Find Specific Practice");

    public static final MenuOption FIND_SPECIFIC_PROBLEM = new MenuOption(MenuKey.FIND_SPECIFIC_PROBLEM,
            "Find Specific Problem");

    public static final List<MenuOption> MAIN_MENU = Arrays.asList(
            RUN_PROBLEMS,
            CREATE_PROBLEM,
            MANAGE_PRACTICES,
            EXIT);

    public static final List<MenuOption> PROBLEM_MENU = Arrays.asList(
            LIST_ALL_PROBLEMS,
            LIST_PROBLEMS_BY_CATEGORY,
            FIND_SPECIFIC_PROBLEM,
            LIST_ALL_PRACTICES,
            LIST_PRACTICES_BY_CATEGORY,
            FIND_SPECIFIC_PRACTICE,
            RETURN);

    public static final List<MenuOption> MANAGE_PRACTICES_MENU = Arrays.asList(
            CREATE_PRACTICE,
            RESET_PRACTICE,
            RETURN);

    public static final List<MenuOption> CREATE_PRACTICE_MENU = Arrays.asList(
            LIST_ALL_PROBLEMS,
            LIST_PROBLEMS_BY_CATEGORY,
            FIND_SPECIFIC_PROBLEM,
            RETURN);
     public static final List<MenuOption> RESET_PRACTICE_MENU = Arrays.asList(
        LIST_ALL_PRACTICES,
        LIST_PRACTICES_BY_CATEGORY,
        FIND_SPECIFIC_PRACTICE,
        RETURN
     );

    public static List<String> getLabels(List<MenuOption> menu) {
        return menu.stream()
                .map(MenuOption::getLabel)
                .collect(Collectors.toList());
    }

    public static MenuOption findByLabel(List<MenuOption> menu, String label) {
        return menu.stream()
                .filter(option -> option.getLabel().equals(label))
                .findFirst()
                .orElse(null);
    }
}
