package com.example.snake.game;

import java.util.Objects;

public class SnakeSegment {
    private Point location;
    private SnakeSegment prev;
    private SnakeSegment next;

    public SnakeSegment(Point location, SnakeSegment prev) {
        this.location = location;
        this.prev = prev;
        if(prev!=null){
          prev.setNext(this);
        }
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
        prev.setNext(this);
    }

  public SnakeSegment getNext() {
    return next;
  }

  public void setNext(SnakeSegment next) {
    this.next = next;
  }

  public boolean contains(Point point){
        SnakeSegment segment = this;
        while(segment!=null){
            if(segment.getLocation().equals(point)){
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
