package com.example.snake.game;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point plus(Point p){
      return new Point(x+p.getX(),y+p.getY());
    }

    //Czy dwa punkty sa przeciwne
    public boolean isReverse(Point p){
        return this.getX() == -p.getX() && this.getY() == -p.getY();
    }

    public boolean isAbove(Point p){
        return this.y < p.getY();
    }
    public boolean isBelow(Point p){
        return this.y > p.getY();
    }
    public boolean isRight(Point p){
        return this.x > p.getX();
    }
    public boolean isLeft(Point p){
        return this.x < p.getX();
    }

    public boolean equals(Point p){
      return this.x == p.getX() && this.y == p.getY();
    }

}
