package com.company;

import static org.junit.jupiter.api.Assertions.*;

class boardTest {

    @org.junit.jupiter.api.Test
    void pathfinder() {
        char[][] r1 = {
                {'+', '+', '+', '.', '.', '+', '+', '+', '+'},
                {'+', '+', '.', '.', '.', '.', '+', '+', '+'},
                {'+', '+', '.', '.', 'Z', '.', '+', '+', '+'},
                {'+', '+', '.', '.', '.', '.', '+', '+', '+'},
                {'+', '+', '.', '.', '.', '.', '.', '+', '+'},
                {'+', '.', '.', '.', '.', '.', '.', '+', '+'},
                {'.', 'P', '.', '.', '.', '.', '+', '+', '+'},
                {'.', '.', '+', '+', '+', '.', '.', '+', '+'},
                {'+', '+', '+', '+', '+', '+', '+', '+', '+'}};
        board b = new board(r1);
        point start = new point(2, 4);
        point end = new point(6, 3);
        point result = b.pathfinder(start, end, 10);
        assertEquals(end.getX(), result.getX());
        assertEquals(end.getY(), result.getY());
    }

    @org.junit.jupiter.api.Test
    void test2() {
        char[][] r1 = {
                {'+', '+', '+', '.', '.', '+', '+', '+', '+'},
                {'+', '+', '.', '.', '.', '.', '+', '+', '+'},
                {'+', '+', '.', '.', 'Z', '.', '+', '+', '+'},
                {'+', '+', '.', '.', '.', '.', '+', '+', '+'},
                {'+', '+', '.', '.', '.', '.', '.', '+', '+'},
                {'+', '.', '.', '.', '.', '.', '.', '+', '+'},
                {'.', 'P', '.', '.', '.', '.', '+', '+', '+'},
                {'.', '.', '+', '+', '+', '.', '.', '+', '+'},
                {'+', '+', '+', '+', '+', '+', '+', '+', '+'}};
        board b = new board(r1);
        point start = new point(2, 4);
        point end = new point(5, 3);
        point result = b.pathfinder(start, end, 3);
        assertEquals(end.getX(), result.getX());
        assertEquals(end.getY(), result.getY());
    }

}