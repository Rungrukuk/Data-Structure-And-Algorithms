package DataStructureAndAlgorithms.domain.practices;

import DataStructureAndAlgorithms.core.models.PracticeInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PracticeSelector {
    private final PracticeRepository practiceRepository;

    public PracticeSelector(PracticeRepository practiceRepository) {
        this.practiceRepository = practiceRepository;
    }

    public List<String> getAllPracticeDisplays() {
        return practiceRepository.findAll().stream()
                .map(this::formatPracticeDisplay)
                .sorted()
                .toList();
    }

    public Map<String, List<String>> getPracticesByCategoryDisplays() {
        Map<String, List<PracticeInfo>> practicesByCategory = practiceRepository.findAllGroupedByCategory();

        return practicesByCategory.entrySet().stream()
                .collect(java.util.stream.Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(this::formatPracticeDisplay)
                                .sorted()
                                .toList()));
    }

    public List<String> findPracticeDisplaysByName(String name) {
        return practiceRepository.findByName(name).stream()
                .map(this::formatPracticeDisplay)
                .sorted()
                .toList();
    }

    public Optional<PracticeInfo> findPracticeByDisplay(String displayString) {
        return practiceRepository.findAll().stream()
                .filter(practice -> formatPracticeDisplay(practice).equals(displayString))
                .findFirst();
    }

    private String formatPracticeDisplay(PracticeInfo practice) {
        return String.format("%s [Category: %s, Practice]",
                practice.getProblemInfo().getName(),
                practice.getProblemInfo().getCategory());
    }

    public List<String> getAllCategories() {
        return practiceRepository.findAllGroupedByCategory().keySet().stream()
                .sorted()
                .toList();
    }
}