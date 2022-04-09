package com.example.snake.vectors;

/**
 * Klasa reprezentująca wektor dwu wymiarowy
 */
public class Vector2D {
    /**
     * Punkt x
     */
    private int x;
    /**
     * Punkt y
     */
    private int y;

    /**
     * @param x tworzonego wektora
     * @param y tworzonego wektora
     */
    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * {@link Vector2D#x}
     */
    public int getX() {
        return x;
    }

    /**
     * {@link Vector2D#x}
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * {@link Vector2D#y}
     */
    public int getY() {
        return y;
    }
    /**
     * {@link Vector2D#y}
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Liczy sume wektora obecnego i przekazengo przez argument
     * @param v Wektor
     * @return suma wektorów obecnego i przekazanego jako argument
     */
    public Vector2D plus(Vector2D v){
      return new Vector2D(x+v.getX(),y+v.getY());
    }


    /**
     * Sprawdza czy obecny punkt jest powyzej punktu p
     * @param p punkt porówywany z obecnym
     * @return Zwraca prawde jeśli punkt obecny jest powyżej punktu p w przeciwnym wypadku fałsz
     */
    public boolean isAbove(Vector2D p){
        return this.y < p.getY();
    }
    /**
     * Sprawdza czy obecny punkt jest poniżej punktu p
     * @param p punkt porówywany z obecnym
     * @return Zwraca prawde jeśli punkt obecny jest poniżej punktu p w przeciwnym wypadku fałsz
     */
    public boolean isBelow(Vector2D p){
        return this.y > p.getY();
    }
    /**
     * Sprawdza czy obecny punkt jest po prawo od punktu p
     * @param p punkt porównywany z obecnym
     * @return Zwraca prawde jeśli punkt obecny jest po prawo od punktu p w przeciwnym wypadku fałsz
     */
    public boolean isRight(Vector2D p){
        return this.x > p.getX();
    }
    /**
     * Sprawdza czy obecny punkt jest po lewo od punktu p
     * @param p punkt porównywany z obecnym
     * @return Zwraca prawde jeśli punkt obecny jest po lewo od punktu p w przeciwnym wypadku fałsz
     */
    public boolean isLeft(Vector2D p){
        return this.x < p.getX();
    }

    /**
     * Sprawdza czy oba punkty są sobie równe
     * @return Zwraca prawde jeśli oba punkty mają równe x i y
     */
    public boolean equals(Vector2D p){
      return this.x == p.getX() && this.y == p.getY();
    }

}
