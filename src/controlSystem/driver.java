package controlSystem;

import request.direction;

public class driver {

    public static void main(String[] args) {
        elevatorCS ecs = new elevatorCS(15,3);
        
        //case 1
        ecs.addPickUp(2, direction.UP, 6);
        ecs.addPickUp(6, direction.UP, 10);
        ecs.step();
        ecs.addPickUp(5, direction.DOWN, 0);
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.addPickUp(2, direction.UP, 6);
        ecs.step();
        ecs.step();
        //ecs.putUnderMaintanence(2);
        //ecs.setToWorking(2);
        //ecs.emergencyShutdown();
        ecs.step();
        ecs.addPickUp(2, direction.DOWN, 1); // should be clubbed with 5-D-0
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        //ecs.powerUp();
        ecs.step();
        ecs.addPickUp(8, direction.UP, 10);
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        /*
        // case 2
        ecs.addPickUp(5, direction.DOWN, 0);
        ecs.step();
        ecs.addPickUp(2, direction.UP, 4); // no piggyback
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        */
        /*
        //case 3
        ecs.addPickUp(5, direction.DOWN, 0);
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.addPickUp(4, direction.DOWN, 3); // piggyback request
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        ecs.step();
        */
    }
}
