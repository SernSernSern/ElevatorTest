package com.company;

import java.util.*;

public class Building {
    private int floors;
    private Elevator elevator;
    private List<List<Integer>> passengersInFloor;
    private static Random r = new Random();


    public Building(int floors){
        this.floors = floors;
        elevator = new Elevator(floors);
        passengersInFloor = Arrays.asList(new List[floors]);
        fillRandomPassengers();
    }

    public void startCycle(){
        while(true){

            if(elevator.isEmpty() && passengersInFloor.stream().allMatch(List::isEmpty)){
                break;
            }

            int removedPassengers = this.removePassengersFromLift();
            if(elevator.isEmpty() && passengersInFloor.stream().allMatch(List::isEmpty))
            {
                elevator.setDirection(this.getElevatorDirectionByMajorPartOfPeople());
            }

            int addedPassengers = this.addPassengersToElevator();

                this.showInformation( removedPassengers, addedPassengers);



           elevator.move();

        }
    }


    private int addPassengersToElevator(){
        elevator.correctDirrection();

        ArrayList<Integer> indexesToDelete = new ArrayList<Integer>();
        for(int i = 0; i< passengersInFloor.get(elevator.getCurrentFloor() - 1).size() && !elevator.isFull(); i++){
            if(elevator.isDirection()){ //if lift goes up
                if(passengersInFloor.get(elevator.getCurrentFloor() - 1).get(i) > elevator.getCurrentFloor()){
                    indexesToDelete.add(i);
                    elevator.addPassenger(
                            passengersInFloor.get(elevator.getCurrentFloor() - 1).get(i));
                }
            } else{
                if(passengersInFloor.get(elevator.getCurrentFloor() - 1).get(i) < elevator.getCurrentFloor()){
                    indexesToDelete.add(i);
                    elevator.addPassenger(
                            passengersInFloor.get(elevator.getCurrentFloor() - 1).get(i));
                }
            }
        }
        if (indexesToDelete.size() > 0) {
            passengersInFloor.get(elevator.getCurrentFloor() - 1).subList(0, indexesToDelete.size()).clear();
        }

        return indexesToDelete.size();
    }


    private int removePassengersFromLift(){
        return elevator.removePassengers();
    }


    private void fillRandomPassengers(){
        for(int i=0;i<floors;i++){
            passengersInFloor.set(i, fillFloor(i + 1));
        }
    }

    private List<Integer> fillFloor(int currentFloor){
        ArrayList<Integer> floor = new ArrayList<Integer>();
        int passInTheFloor = r.nextInt(11); //0...10
        for(int j=1; j<passInTheFloor; j++) {
            floor.add(createRandomPassenger(currentFloor));
        }
        return floor;
    }

    private int createRandomPassenger(int currentFloor){
        int passengerTargetFloor = currentFloor;
        while(passengerTargetFloor == currentFloor)
            passengerTargetFloor = r.nextInt(floors)+1;

        return passengerTargetFloor;
    }

    private void createRandomPassengers(int count){
        for(int j=0; j<count; j++)
            this.passengersInFloor.get(elevator.getCurrentFloor() - 1).add(
                    createRandomPassenger(elevator.getCurrentFloor()));
    }

    private boolean getElevatorDirectionByMajorPartOfPeople(){
        if(elevator.getCurrentFloor()==1) return true;
        else if(elevator.getCurrentFloor()==floors) return false;
        else {
            int peoplesWhowantUp=0;
            for(int i = 0; i < passengersInFloor.get(elevator.getCurrentFloor() - 1).size(); i++)
                if(passengersInFloor.get(elevator.getCurrentFloor() - 1).get(i) > elevator.getCurrentFloor())
                    peoplesWhowantUp++;

            return passengersInFloor.get(elevator.getCurrentFloor() - 1).size() - peoplesWhowantUp < peoplesWhowantUp;

        }
    }

    public String toString(){
        StringBuilder result= new StringBuilder();

        for(int i=floors-1; i>=0; i--){
            if(elevator.getCurrentFloor() != i+1)
                result.append("" + (i+1) + " floor: " + passengersInFloor.get(i).toString()+ "\n");
            else
                result.append("" + (i+1) + " floor: " + passengersInFloor.get(i).toString()+ " Lift:{" +elevator+"}\n" );
        }
        return result.toString();
    }

    private void showInformation( int removedPassengers, int addedPassengers){
        System.out.println("----------- Step " + " -----------");
        System.out.print(this.toString());
        System.out.println("Leave: "+ removedPassengers + " Entry: " + addedPassengers + "\n");
    }
}
