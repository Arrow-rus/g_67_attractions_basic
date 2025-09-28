package app.domain;

import java.util.Objects;

public class Attraction {

    private Long id;
    private String title;
    private double price;
    private boolean active;

    public Attraction(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Attraction attraction = (Attraction) o;
        return Double.compare(price, attraction.price) == 0
                && active == attraction.active
                && Objects.equals(id, attraction.id)
                && Objects.equals(title, attraction.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, active);
    }

    @Override
    public String toString() {
        return String.format("Attraction: id - %d, title - %s, price - %.2f, active - %b", id, title, price, active);
    }
}
