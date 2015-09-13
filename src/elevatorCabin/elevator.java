package elevatorCabin;

import java.util.Comparator;
import java.util.PriorityQueue;

import request.direction;

class decreasingOrder implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return Integer.compare(o2, o1);
    }
}

public class elevator {
    private door doorStatus;
    private int currentFloor;
    private state currentState;
    private PriorityQueue<Integer> destinations;

    public elevator() {
        this.currentFloor = 0;
        this.currentState = state.IDLE;
        this.doorStatus = door.CLOSED;
    }

    public void addDestination(int floor) {
        if (!this.destinations.contains(floor))
            destinations.add(floor);
    }

    // to mobilize an idle lift in a given direction
    public void deploy(direction destination) {
        if (destination == direction.DOWN) {
            currentState = state.MovingDOWN;
            destinations = new PriorityQueue<>(new decreasingOrder());
        } else {
            currentState = state.MovingUP;
            destinations = new PriorityQueue<>();
        }
    }
    
    // move elevator one step ahead
    // if current floor is a stop, elevator will wait, else it will move ahead to next floor
    public void move() {
        if (!(isIdle() || isUnderMaintenance())) {
            if (currentFloor == destinations.peek()) {
                openDoor();
                currentFloor = destinations.poll();
                closeDoor();
                if (destinations.isEmpty())
                    setCurrentState(state.IDLE);
            } else {
                if (currentFloor - destinations.peek() > 0)
                    currentFloor--;
                else
                    currentFloor++;
            }
        }
    }

    //put elevator under maintanence
    public void underMaintanence() {
        if (!isUnderMaintenance()) {
            setCurrentState(state.UnderMaintenance);
            openDoor();
        }
    }
    
    // to bring back up elevator after maintanence
    public void working() {
        if (isUnderMaintenance()) {
            setCurrentState(state.IDLE);
            closeDoor();
            resetDestinations();
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public state getCurrentState() {
        return currentState;
    }

    private void setCurrentState(state s) {
        this.currentState = s;
    }

    public int getDestination() {
        return destinations.peek();
    }

    public boolean isIdle() {
        return (getCurrentState() == state.IDLE) ? true : false;
    }

    public boolean isMovingUp() {
        return (getCurrentState() == state.MovingUP) ? true : false;
    }

    public boolean isMovingDown() {
        return (getCurrentState() == state.MovingDOWN) ? true : false;
    }

    public boolean isUnderMaintenance() {
        return (getCurrentState() == state.UnderMaintenance) ? true : false;
    }

    private void openDoor() {
        this.doorStatus = door.OPEN;
    }

    private void closeDoor() {
        this.doorStatus = door.CLOSED;
    }

    private void resetDestinations() {
        this.destinations = new PriorityQueue<>();
    }

}
