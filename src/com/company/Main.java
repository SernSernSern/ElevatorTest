package com.company;

import java.util.Random;

public class Main {

    private static Random r = new Random();
    public static void main(String[] args) {
        Building building = new Building((r.nextInt(16)+5)); // (0...15) +5 = 5... 20
        System.out.println("START");
        System.out.println(building);
        building.startCycle();
    }
}
