package controlSystem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import request.direction;
import request.userRequest;
import elevatorCabin.elevator;

public class elevatorCS {

    private final int noOfFloors;
    private final int noOfElevators;
    private List<userRequest> incoming;
    private elevator[] elevators;

    public elevatorCS(int noOfFloors, int noOfElevators) {
        this.noOfFloors = noOfFloors;
        this.noOfElevators = noOfElevators;
        this.incoming = new LinkedList<>();
        initialiseElevators(noOfElevators);
    }
    
    public void putUnderMaintanence(int id) {
        elevators[id].underMaintanence();
    }

    public void setToWorking(int id) {
        elevators[id].working();
    }

    public void emergencyShutdown() {
        for (int i = 0; i < noOfElevators; i++)
            putUnderMaintanence(i);
    }

    public void powerUp() {
        for (int i = 0; i < noOfElevators; i++)
            setToWorking(i);
    }

    public void addPickUp(int source, direction dir, int destination) {
        incoming.add(new userRequest(source, dir, destination));
    }
    
    // move all lifts one step ahead.
    public void step() {
        processPickUp();
        for (int i = 0; i < elevators.length; i++) {
            elevators[i].move();
        }
        printStatus();
    }

    //process pickup and assign elevators wherever possible
    public void processPickUp() {
        for (Iterator<userRequest> it = incoming.iterator(); it.hasNext();) {
            userRequest req = it.next();

            //check validity of request
            if (!validateRequest(req)) {
                System.out.println("Invalid Request : " + req.getSource() + "|"
                        + req.getDirection() + "|" + req.getDestination());
                //remove invalid requests from input queue
                it.remove();
                continue;
            }

            // find closest elevator : friendElevator or piggybackElevator or IdleElevator
            int id = findClosestElevator(req);
            if (id != -1) {
                if (elevators[id].isIdle())
                    elevators[id].deploy(req.getDirection());
                elevators[id].addDestination(req.getSource());
                elevators[id].addDestination(req.getDestination());
                // remove request successfully processed
                it.remove();
            }
        }
    }

    private boolean validateRequest(userRequest req) {
        boolean isValid = req.validate();
        if (req.getSource() < 0 || req.getSource() > noOfFloors)
            isValid = false;
        if (req.getDestination() < 0 || req.getDestination() > noOfFloors)
            isValid = false;
        return isValid;
    }

    private int findClosestElevator(userRequest req) {
        int elevatorId = -1;
        int closest = noOfFloors + 1;
        int distance = closest;

        // find friend elevator
        if (req.getDirection() == direction.DOWN) {
            for (int i = 0; i < elevators.length; i++) {
                distance = elevators[i].getCurrentFloor() - req.getSource();
                if (elevators[i].isMovingDown() && distance >= 0 && distance < closest) {
                    closest = distance;
                    elevatorId = i;
                }
            }
        } else {
            for (int i = 0; i < elevators.length; i++) {
                distance = req.getSource() - elevators[i].getCurrentFloor();
                if (elevators[i].isMovingUp() && distance >= 0 && distance < closest) {
                    closest = distance;
                    elevatorId = i;
                }
            }
        }

        // find piggybank elevator
        if (req.getDirection() == direction.DOWN) {
            for (int i = 0; i < elevators.length; i++) {
                if (elevators[i].isMovingDown()
                        && elevators[i].getCurrentFloor() < elevators[i].getDestination()) {
                    distance = 2 * elevators[i].getDestination() 
                                - elevators[i].getCurrentFloor() 
                                - req.getSource();
                    if (elevators[i].getDestination() >= req.getSource() && distance < closest) {
                        closest = distance;
                        elevatorId = i;
                    }
                }
            }
        } else {
            for (int i = 0; i < elevators.length; i++) {
                if (elevators[i].isMovingUp()
                        && elevators[i].getCurrentFloor() > elevators[i].getDestination()) {
                    distance = Math.abs(2 * elevators[i].getDestination()
                                - elevators[i].getCurrentFloor() 
                                - req.getSource());
                    if (elevators[i].getDestination() <= req.getSource() && distance < closest) {
                        closest = distance;
                        elevatorId = i;
                    }
                }
            }
        }

        // find Idle elevator
        for (int i = 0; i < elevators.length; i++) {
            distance = Math.abs(elevators[i].getCurrentFloor() - req.getSource());
            if (elevators[i].isIdle() && distance < closest) {
                closest = distance;
                elevatorId = i;
            }
        }

        return elevatorId;
    }

    private void initialiseElevators(int noOfElevators) {
        this.elevators = new elevator[noOfElevators];
        for (int i = 0; i < noOfElevators; i++)
            elevators[i] = new elevator();
    }

    private void printStatus() {
        for (int i = 0; i < elevators.length; i++) {
            System.out.print(elevators[i].getCurrentFloor() + " | ");
        }
        System.out.println();
    }
}
