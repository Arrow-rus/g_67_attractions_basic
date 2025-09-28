package app.service;

import app.domain.Attraction;
import app.exceptions.AttractionNotFoundException;
import app.exceptions.AttractionSaveException;
import app.exceptions.AttractionUpdateException;
import app.repository.AttractionRepository;

import java.util.List;

public class AttractionService {

    private static AttractionService instance;
    private final AttractionRepository repository = new AttractionRepository();

    private AttractionService() {

    }

    public static AttractionService getInstance() {
        if (instance == null) {
            instance = new AttractionService();
        }
        return instance;
    }

    public Attraction save(Attraction attraction) {
        if (attraction == null) {
            throw new AttractionSaveException("Аттракцион не может быть null");
        }

        String title = attraction.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new AttractionSaveException("Наименование аттракциона не должно быть пуствм");
        }
        if (attraction.getPrice() < 0) {
            throw new AttractionSaveException("Цена аттракциона не должна быть отрицательной");
        }
        attraction.setActive(true);
        return repository.save(attraction);
    }
    public List<Attraction> getAllActiveAttractions() {
        return repository.findAll()
                .stream()
                .filter(Attraction::isActive)
                .toList();
    }
    public Attraction getActiveAttractionById(Long id) {
        Attraction attraction = repository.findById(id);

        if (attraction == null || !attraction.isActive()) {
            throw new AttractionNotFoundException(id);
        }
        return attraction;
    }

    public void update(Long id, double newPrice) {
        if (newPrice < 0) {
            throw new AttractionUpdateException("Цена аттракциона не должна быть отрицательной");
        }
        repository.update(id, newPrice);
    }
    public void deleteById(Long id) {
        Attraction attraction = getActiveAttractionById(id);
        attraction.setActive(false);
    }
    public void deleteByTitle(String title) {
        getAllActiveAttractions()
                .stream()
                .filter(x -> x.getTitle().equals(title))
                .forEach(x -> x.setActive(false));
    }
    public void restoreById(Long id) {
        Attraction attraction = repository.findById(id);

        if (attraction == null) {
            throw new AttractionNotFoundException(id);
        }
        attraction.setActive(true);
    }

    public int getActiveAttractionsCount() {
        return getAllActiveAttractions().size();
    }

    public double getActiveAttractionsTotalCost() {
        return getAllActiveAttractions()
                .stream()
                .mapToDouble(Attraction::getPrice)
                .sum();
    }

    public double getActiveAttractionsAveragePrice() {
        return getAllActiveAttractions()
                .stream()
                .mapToDouble(Attraction::getPrice)
                .average()
                .orElse(0.0);
    }
}
