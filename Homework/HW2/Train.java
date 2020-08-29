/**
 * The train class that represents the nodes for the linked list
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #2
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */
public class Train {
    private Train next, prev;
    private int trainNumber, arrivalTime, transferTime, departureTime;
    private String destination;

    public Train() {
        trainNumber = 0;
        arrivalTime = 0;
        transferTime = 0;
        destination = "";
    }

    //arg constructor to initialize variables
    public Train(int newTrainNumber, String newDestination, int newArrivalTime, int newTransferTime) {
        trainNumber = newTrainNumber;
        arrivalTime = newArrivalTime;
        transferTime = newTransferTime;
        destination = newDestination;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(int transferTime) {
        this.transferTime = transferTime;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setNext(Train newTrain) {
        next = newTrain;
    }

    public void setPrev(Train prevTrain) {
        prev = prevTrain;
    }

    public Train getNext() {
        return next;
    }

    public Train getPrev() {
        return prev;
    }

    /**
     * Checks if the train currently exists
     *
     * @param obj The train object to be compared to see if equal
     * @return True if there exists a train with the same train number. False if
     * otherwise.
     */
    public boolean equal(Object obj) {
        Train compare = (Train) obj;
        return (this.trainNumber == compare.trainNumber);
    }

    /**
     * Converts the train object into a string format
     *
     * @return String format of all of the train's object information
     */
    public String toString() {
        return "Train Number: " + getTrainNumber() + "\nTrain Destination: " +
                getDestination() + "\nArrivalTime: " + getArrivalTime() +
                "\nDeparture Time: " + String.format("%04d", getDepartureTime());
    }
}
