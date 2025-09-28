package app.service;

import app.domain.Attraction;
import app.domain.Visitor;
import app.exceptions.VisitorNotFoundException;
import app.exceptions.VisitorSaveException;
import app.exceptions.VisitorUpdateException;
import app.repository.VisitorRepository;

import java.util.Collection;
import java.util.List;

public class VisitorService {

    private final VisitorRepository repository = new VisitorRepository();
    private final AttractionService attractionService =  AttractionService.getInstance();

    public Visitor save(Visitor visitor) {
        if (visitor == null) {
            throw new VisitorSaveException("Посетитель не может быть nall");
        }

        String name = visitor.getName();
        if (name == null || name.trim().isEmpty()) {
            throw new VisitorSaveException("Имя посетителя не может быть пустым");
        }
        visitor.setActive(true);
        return repository.save(visitor);
    }

    public List<Visitor> getAllActiveVisitors() {
        return repository.findAll()
                .stream()
                .filter(Visitor::isActive)
                .toList();
    }

    public Visitor getActiveVisitorById(Long id) {
        Visitor visitor = repository.findById(id);

        if (visitor == null || !visitor.isActive()) {
            throw new VisitorNotFoundException(id);
        }
        return visitor;
    }

    public void update(Long id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new VisitorUpdateException("Имя посетителя не может быть пустым");
        }
        repository.update(id, newName);
    }

    public void deleteById(Long id) {
        Visitor visitor = getActiveVisitorById(id);
        visitor.setActive(false);
    }
    public void deleteByName(String name) {
        getAllActiveVisitors()
                .stream()
                .filter(x -> x.getName().equals(name))
                .forEach(x -> x.setActive(false));
    }
    public void restoreById(Long id) {
        Visitor visitor = repository.findById(id);

        if (visitor == null) {
            throw new VisitorNotFoundException(id);
        }
        visitor.setActive(true);
    }

    public int getActiveVisitorsNumber() {
        return getAllActiveVisitors().size();
    }


    public double getVisitorsCartTotalCost(Long visitorId) {
        return getActiveVisitorById(visitorId)
                .getCart()
                .stream()
                .filter(Attraction::isActive)
                .mapToDouble(Attraction::getPrice)
                .sum();
    }

    public double getVisitorsCartAveragePrice(Long visitorId) {
        return getActiveVisitorById(visitorId)
                .getCart()
                .stream()
                .filter(Attraction::isActive)
                .mapToDouble(Attraction::getPrice)
                .average()
                .orElse(0.0);
    }
    public void addAttractionToVisitorsCart(Long visitorId, Long attractionId) {
        Visitor visitor = getActiveVisitorById(visitorId);
        Attraction attraction = attractionService.getActiveAttractionById(attractionId);
        visitor.getCart().add(attraction);
    }

    public void removeAttractionFromVisitorsCart(Long visitorId, Long attractionId) {
        Visitor visitor = getActiveVisitorById(visitorId);
        Attraction attraction = attractionService.getActiveAttractionById(attractionId);
        visitor.getCart().remove(attraction);
    }

    public void clearVisitorsCart(Long visitorId) {
        Visitor visitor = getActiveVisitorById(visitorId);
        visitor.getCart().clear();
    }
}
