package app.repository;

import app.domain.Visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitorRepository {

    private final Map<Long, Visitor> database = new HashMap<>();
    private long maxId;

    public Visitor save(Visitor visitor) {
        visitor.setId(++maxId);
        database.put(maxId, visitor);
        return visitor;
    }
    public List<Visitor> findAll() {
        return new ArrayList<>(database.values());
    }
    public Visitor findById(Long id) {
        return database.get(id);
    }
    public void update(Long id, String newName) {
        Visitor visitor = database.get(id);
        if (visitor != null) {
            visitor.setName(newName);
        }
    }
    public void deleteById(Long id) {
        database.remove(id);
    }
}
