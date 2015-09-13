package request;

public class userRequest {
    private int sourceFloor;
    private direction requestedDirection;
    private int destinationFloor;

    public userRequest(int sf, direction d, int df) {
        this.sourceFloor = sf;
        this.requestedDirection = d;
        this.destinationFloor = df;
    }

    public int getSource() {
        return sourceFloor;
    }

    public direction getDirection() {
        return requestedDirection;
    }

    public int getDestination() {
        return destinationFloor;
    }

    public boolean validate() {
        boolean isValid = true;
        if (getDirection() == direction.DOWN && getSource() < getDestination())
            isValid = false;
        if (getDirection() == direction.UP && getSource() > getDestination())
            isValid = false;
        return isValid;
    }
}
