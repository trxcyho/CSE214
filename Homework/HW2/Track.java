/**
 * The track class that is a linked list of trains
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #2
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */
public class Track {
    private Train head, tail, cursor;
    private Track next, prev;
    private double utilizationRate;
    private int trackNumber;
    private int amountOfTrains;

    public Track() {
    }

    public Track(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    /**
     * Tells how much train objects are in the specified track
     *
     * @return Number of trains in th track
     */
    public int getAmountOfTrains() {
        Train tempCursor = head;
        int count = 0;
        while (tempCursor != null) {
            count++;
            tempCursor = tempCursor.getNext();
        }
        amountOfTrains = count;
        return amountOfTrains;
    }

    public Train getHead() {
        return head;
    }

    public void setHead(Train head) {
        this.head = head;
    }

    public Train getTail() {
        return tail;
    }

    public void setTail(Train tail) {
        this.tail = tail;
    }

    public Train getCursor() {
        return cursor;
    }

    public void setCursor(Train cursor) {
        this.cursor = cursor;
    }

    public Track getNext() {
        return next;
    }

    public void setNext(Track next) {
        this.next = next;
    }

    public Track getPrev() {
        return prev;
    }

    public void setPrev(Track prev) {
        this.prev = prev;
    }

    /**
     * Calculates the utilization rate of the track
     *
     * @return the percentage of how often the track is occupied with its trains
     */
    public double getUtilizationRate() {
        Train tempCursor = head;
        int transferTime = 0;
        while (tempCursor != null) {
            transferTime += tempCursor.getTransferTime();
            tempCursor = tempCursor.getNext();
        }
        utilizationRate = transferTime / 1440.0;
        utilizationRate = Math.round(utilizationRate * 10000.0) / 100.0;
        return utilizationRate;
    }

    public void setUtilizationRate(double utilizationRate) {
        this.utilizationRate = utilizationRate;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    /**
     * Sorts and adds the train to the track based on arrival time of the trains.
     * The new train becomes the cursor.
     *
     * @param newTrain The new train being added to the track
     * @throws IllegalArgumentException Thrown if time of the train overlaps
     * with another or if the train already exists
     */
    public void addTrain(Train newTrain) throws IllegalArgumentException {
        if (!isValidTime(newTrain.getArrivalTime()) || departureTime(newTrain.
          getArrivalTime(), newTrain.getTransferTime()) < 0)
            throw new IllegalArgumentException("Time is not valid");
        newTrain.setDepartureTime(departureTime(newTrain.getArrivalTime(),
          newTrain.getTransferTime()));
        Train tempCursor = head;
        while (tempCursor != null) {
            if (tempCursor.getTrainNumber() == (newTrain.getTrainNumber()))
                throw new IllegalArgumentException("Train number already exists");
            tempCursor = tempCursor.getNext();
        }
        //No Train at the track
        if (head == null) {
            setHead(newTrain);
            setCursor(newTrain);
            setTail(newTrain);
        }
        //One train at the track
        else if (head == tail) {
            if (head.getDepartureTime() > newTrain.getDepartureTime() && head.
              getArrivalTime() > newTrain.getArrivalTime()) {
                newTrain.setNext(head);
                head.setPrev(newTrain);
                head = newTrain;
                setCursor(newTrain);
            } else if (head.getDepartureTime() < newTrain.getDepartureTime() &&
              head.getArrivalTime() < newTrain.getArrivalTime()) {
                head.setNext(newTrain);
                newTrain.setPrev(head);
                tail = newTrain;
                setCursor(newTrain);
            } else
                throw new IllegalArgumentException("Train time is overlapping");
        } else {
            tempCursor = head;
            while (tempCursor != null) {
                if (tempCursor == head && tempCursor.getDepartureTime() >
                  newTrain.getDepartureTime() && tempCursor.getArrivalTime() >
                  newTrain.getArrivalTime()) {
                    newTrain.setNext(tempCursor);
                    tempCursor.setPrev(newTrain);
                    head = newTrain;
                    setCursor(newTrain);
                    break;
                } else if (tempCursor == tail && tempCursor.getDepartureTime() <
                  newTrain.getDepartureTime() && tempCursor.getArrivalTime() <
                  newTrain.getArrivalTime()) {
                    tempCursor.setNext(newTrain);
                    newTrain.setPrev(tempCursor);
                    tail = newTrain;
                    setCursor(newTrain);
                    break;
                } else if (tempCursor.getDepartureTime() < newTrain.
                  getDepartureTime() && tempCursor.getArrivalTime() < newTrain.
                  getArrivalTime() && tempCursor.getNext().getArrivalTime() >
                  newTrain.getDepartureTime()) {
                    newTrain.setNext(tempCursor.getNext());
                    newTrain.setPrev(tempCursor);
                    tempCursor.setNext(newTrain);
                    tempCursor.getNext().getNext().setPrev(newTrain);
                    setCursor(newTrain);
                    break;
                }
                tempCursor = tempCursor.getNext();
            }
            if (cursor != newTrain)
                throw new IllegalArgumentException("Time is overlapping");
        }
    }

    /**
     * Prints out the information of the train that cursor is at
     */
    public void printSelectedTrain() {
        if (cursor == null)
            System.out.println("There is no train");
        else
            System.out.println(cursor.toString());
    }

    /**
     * Removes the train that cursor is at
     *
     * @return the train that is removed
     */
    public Train removeSelectedTrain() {
        Train tempCursor = cursor;
        if (cursor == null)
            System.out.println("Nothing to remove.");
        else if (head == tail) {
            cursor = null;
            head = null;
            tail = null;
        } else if (cursor == tail) {
            cursor.getPrev().setNext(null);
            tail = cursor.getPrev();
            cursor.setPrev(null);
            cursor = tail;

        } else if (cursor == head) {
            cursor.getNext().setPrev(null);
            head = cursor.getNext();
            cursor.setNext(null);
            cursor = head;
        } else {
            Train tempCursor2 = cursor.getNext();
            cursor.getNext().setPrev(cursor.getPrev());
            cursor.getPrev().setNext(cursor.getNext());
            cursor.setNext(null);
            cursor.setPrev(null);
            cursor = tempCursor2;
        }
        return tempCursor;
    }

    /**
     * Moves the cursor to the next train in the track
     *
     * @return True if the cursor can be moved, else false.
     */
    public boolean selectNextTrain() {
        if (cursor == null)
            throw new NullPointerException("There is no train");
        else if (cursor.getNext() == null)
            return false;
        else
            cursor = cursor.getNext();
        return true;
    }

    /**
     * Moves the cursor to the previous train in the track
     *
     * @return True if the cursor can be moved, else false.
     */
    public boolean selectPrevTrain() {
        if (cursor == null)
            throw new NullPointerException("Train does not exist");
        else if (cursor.getPrev() == null)
            throw new NullPointerException("Train does not exist");
        else
            setCursor(this.cursor.getPrev());
        return true;
    }

    /**
     * Converts the track and its trains into a string format
     *
     * @return a string that has all the track's information
     */
    public String toString() {
        String printTrack = "";
        Train tempCursor = head;
        while (tempCursor != null) {
            if (tempCursor != cursor)
                printTrack += (String.format("%-10s%-17d%-25s%-17s%-15s", "",
                  tempCursor.getTrainNumber(), tempCursor.getDestination(),
                  String.format("%04d", tempCursor.getArrivalTime()),
                  String.format("%04d", tempCursor.getDepartureTime())) + "\n");
            else
                printTrack += (String.format("%-10s%-17d%-25s%-17s%-15s", "*",
                  tempCursor.getTrainNumber(), tempCursor.getDestination(),
                  String.format("%04d", tempCursor.getArrivalTime()),
                  String.format("%04d", tempCursor.getDepartureTime())) + "\n");
            tempCursor = tempCursor.getNext();
        }
        return printTrack;
    }

    /**
     * Prints the table heading for trains
     */
    public void printTableHeading() {
        System.out.println(String.format("%-10s%-17s%-25s%-17s%-15s", "Selected",
          "Train Number", "Destination", "Arrival Time", "Departure Time"));
        for (int i = 0; i < 90; i++)
            System.out.print("-");
        System.out.println();
    }

    /**
     * Checks if the time that is valid
     *
     * @param time The arrival time that is to be checked
     * @return true if the arrival time is in the right format, otherwise false.
     */
    public boolean isValidTime(int time) {
        if(time > 2400 || time < 0)
            return false;
        String stringTime;
        stringTime = String.format("%04d", time);
        if (stringTime.length() != 4)
            return false;
        String hour = stringTime.substring(0, 2);
        String minute = stringTime.substring(2, 4);
        if (Integer.parseInt(hour) < 0 || Integer.parseInt(hour) > 24 || Integer.
          parseInt(minute) < 0 || Integer.parseInt(minute) > 60)
            return false;
        return true;
    }

    /**
     * checks if the departure time of the train is valid
     *
     * @param arrivalTime  the time in 24 hour format that the train arrives to the track
     * @param transferTime the amount of time in minutes that the train is at the track
     * @return Returns departure time. If it is not valid it will return a negative value.
     */
    public int departureTime(int arrivalTime, int transferTime) {
        if (transferTime > 1440 || transferTime < 0)
            return -1;
        String stringTime = String.format("%04d", arrivalTime);
        int hour = Integer.parseInt(stringTime.substring(0, 2));
        int minute = Integer.parseInt(stringTime.substring(2, 4));
        int timeAtStation = minute + transferTime;
        hour = hour + (timeAtStation / 60);
        minute = timeAtStation % 60;
        String stringDepartureTime = String.format("%02d", hour) + String.format("%02d", minute);
        int departureTime = Integer.parseInt(stringDepartureTime);
        if (!isValidTime(departureTime))
            return -1;
        return departureTime;
    }

}
