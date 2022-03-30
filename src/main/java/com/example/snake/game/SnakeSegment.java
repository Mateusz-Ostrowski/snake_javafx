package com.example.snake.game;

import java.util.Objects;

public class SnakeSegment {
    private Point location;
    private SnakeSegment prev;

    public SnakeSegment(Point location, SnakeSegment prev) {
        this.location = location;
        this.prev = prev;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public SnakeSegment getPrev() {
        return prev;
    }

    public void setPrev(SnakeSegment prev) {
        this.prev = prev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnakeSegment that = (SnakeSegment) o;
        return Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}
