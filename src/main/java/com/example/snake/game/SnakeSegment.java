package com.example.snake.game;

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
}
