package com.example.snake;

import com.example.snake.vectors.Vector2D;

import java.util.Objects;

/**
 * Klasa reprezentująca każdą część węża
 * Zaimplementowana w postaci listy dwukierunkowej
 * Każdy segment posiada odwołanie do kolejnego i poprzedniego elementu oraz swoje położenie
 */

public class SnakeSegment {
    /**
     * Lokalizacja obecnego segmentu
     */
    private Vector2D location;

    /**
     * Poprzedni segment węża
     */
    private SnakeSegment prev;

    /**
     * Następny segment węża
     */
    private SnakeSegment next;

    public SnakeSegment(Vector2D location, SnakeSegment prev) {
        this.location = location;
        this.prev = prev;
        if (prev != null) {
            prev.setNext(this);
        }
    }

    public SnakeSegment(Vector2D location) {
        this(location, null);
    }

    /**
     * {@link SnakeSegment#location}
     */
    public Vector2D getLocation() {
        return location;
    }

    /**
     * {@link SnakeSegment#location}
     */
    public void setLocation(Vector2D location) {
        this.location = location;
    }

    /**
     * {@link SnakeSegment#prev}
     */
    public SnakeSegment getPrev() {
        return prev;
    }

    /**
     * {@link SnakeSegment#prev}
     */
    public void setPrev(SnakeSegment prev) {
        this.prev = prev;
        if (prev != null) {
            prev.setNext(this);
        }
    }

    /**
     * {@link SnakeSegment#next}
     */
    public SnakeSegment getNext() {
        return next;
    }

    /**
     * {@link SnakeSegment#next}
     */
    public void setNext(SnakeSegment next) {
        this.next = next;
    }

    /**
     * Sprawdza czy punkt wskazany przez parametr zawiera się w obecnym segmencie węża lub do niego poprzednich
     * @param point punkt szukany w wężu
     * @return zwraca prawde jeśli point zawiera się w obecnym segmencie lub poprzednich fałsz w przeciwnym razie
     */
    public boolean contains(Vector2D point) {
        SnakeSegment segment = this;
        while (segment != null) {
            if (segment.getLocation().equals(point)) {
                return true;
            }
            segment = segment.getPrev();
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnakeSegment that = (SnakeSegment) o;
        return Objects.equals(location, that.location);
    }

}
