package app.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Visitor {

    private Long id;
    private String name;
    private boolean active;
    private List<Attraction> cart = new ArrayList<>();

    public Visitor(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Attraction> getCart() {
        return cart;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return active == visitor.active
                && Objects.equals(id, visitor.id)
                && Objects.equals(name, visitor.name)
                && Objects.equals(cart, visitor.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active, cart);
    }

    @Override
    public String toString() {
        return String.format("Visitor: id - %d, name - %s, active - %b", id, name,active);
    }
}