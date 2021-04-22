package com.company;

import java.util.ArrayList;

public class Elevator {
    private static final int MAX_PASSENGERS = 5;
    private ArrayList<Integer> passengers = new ArrayList<>(MAX_PASSENGERS);

    private int currentFloor = 1;
    private int maxFloor;
    private boolean direction = true;

    public Elevator(int maxfloor) {
        this.maxFloor = maxfloor;
    }

    public int move() {
        this.correctDirrection();

        int nextFloor;

        if (!this.isFull()) {
            nextFloor = direction ? currentFloor + 1 : currentFloor - 1;
        } else nextFloor = findClosestPassengerFloorIfElevatorFull();
        currentFloor = nextFloor;

        return nextFloor;
    }

    public boolean isFull() {
        boolean isElevatorFull = true;
        if(passengers.size() != MAX_PASSENGERS){
            isElevatorFull = false;
        }
        return isElevatorFull;
    }


    public boolean isEmpty() {
        for (int i : passengers)
            if (i != 0) return false;
        return true;
    }


    public void addPassenger(int passengerFloor) {


        passengers.add(passengerFloor);

    }

    public int removePassengers() {
        int removedPassengersCount = 0;
        passengers.removeIf(i -> i == currentFloor);

        return removedPassengersCount;
    }


    private int findClosestPassengerFloorIfElevatorFull() {
        int result = 0;
        if (direction) {
            int min = maxFloor + 1;
            for (int i : passengers)
                if (i != 0 && i < min) min = i;
            result = (min != maxFloor + 1) ? min : 0;
        } else {
            int max = 0;
            for (int i : passengers)
                if (i > max) max = i;
            result = max;
        }
        if (result == 0) throw new IllegalStateException("Method can`t find next floor!");
        return result;
    }


    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }


    public void correctDirrection() {
        if (currentFloor == 1) direction = true;
        else if (currentFloor == maxFloor) direction = false;
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int passenger : passengers) {
            if (passenger != 0)
                res.append(passenger + " ");
        }
        if (res.length() > 0) res.deleteCharAt(res.length() - 1);
        return res.toString();
    }

}