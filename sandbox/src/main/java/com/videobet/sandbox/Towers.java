package com.videobet.sandbox;

import java.io.IOException;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Towers {
    Deque<Integer> towerA = new LinkedList<>();
    Deque<Integer> towerB = new LinkedList<>();
    Deque<Integer> towerC = new LinkedList<>();

    public Towers(int[] rings) {
        Arrays.stream(rings).forEach(towerA::add);
    }

    private boolean canMove(Deque<Integer> source, Deque<Integer> destination) {
        if (source.isEmpty()) return false;
        int ring = source.peekLast();
        Integer base = destination.peekLast();
        if (base != null && ring > base) {
            return false;
        }
        return true;
    }

    private void moveRing(Deque<Integer> source, Deque<Integer> destination) {
        if (canMove(source, destination)) {
            destination.add(source.pollLast());
            log();
        } else {
            throw new IllegalStateException();
        }
    }

    private void moveRings(Deque<Integer> source, Deque<Integer> destination, Deque<Integer> buffer, int count) {
        if (count == 0) {
            return;
        }
        moveRings(source, buffer, destination, count - 1);
        moveRing(source, destination);
        moveRings(buffer, destination, source, count - 1);
    }

    private void moveRings(Deque<Integer> source, Deque<Integer> destination, Deque<Integer> buffer) {
        moveRings(source, destination, buffer, source.size());
    }

    private void moveRings() {
        log();
        moveRings(towerA, towerB, towerC);
    }

    private void log() {
        clrscr();
        System.out.println(towerA);
        System.out.println(towerB);
        System.out.println(towerC);
        try {
            TimeUnit.MILLISECONDS.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void clrscr() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Towers towers = new Towers(new int[]{5, 4, 3, 2, 1} );
        towers.moveRings();
    }

}
