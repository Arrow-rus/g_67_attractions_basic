package app.repository;

import app.domain.Attraction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AttractionRepository {

    private final List<Attraction> database = new ArrayList<>();
    private long maxId;


    public Attraction save(Attraction attraction) {
        attraction.setId(++maxId);
        database.add(attraction);
        return attraction;
    }

    public List<Attraction> findAll() {
        return database;
    }

    public Attraction findById(Long id) {
        for (Attraction attraction : database) {
            if (attraction.getId().equals(id)) {
                return attraction;
            }
        }

        return null;
    }
    public void update(Long id, double newPrice) {
        for (Attraction attraction : database) {
            if (attraction.getId().equals(id)) {
                attraction.setPrice(newPrice);
                break;

            }
        }
    }

    public void deleteById(Long id) {
        Iterator<Attraction> iterator = database.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }
}
