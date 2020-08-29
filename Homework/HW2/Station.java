/**
 * Station class that contains methods and is a linked list of tracks
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #2
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Station {
    private Track head;
    private Track tail;
    private Track cursor;

    public Track getHead() {
        return head;
    }

    public void setHead(Track head) {
        this.head = head;
    }

    public Track getTail() {
        return tail;
    }

    public void setTail(Track tail) {
        this.tail = tail;
    }

    public Track getCursor() {
        return cursor;
    }

    public void setCursor(Track cursor) {
        this.cursor = cursor;
    }


    public Station() {
    }

    /**
     * Sorts and adds the track to the station based on track number
     * @param newTrack The new track to be added to the station
     * @throws IllegalArgumentException Thrown if track with the same number exists
     */
    public void addTrack(Track newTrack) throws IllegalArgumentException {
        Track tempCursor = head;
        while (tempCursor != null) {
            if (tempCursor.getTrackNumber() == newTrack.getTrackNumber())
                throw new IllegalArgumentException("Track already exists");
            tempCursor = tempCursor.getNext();
        }
        if (head == null) {
            head = newTrack;
            cursor = newTrack;
            tail = newTrack;
        } else if (head == tail) {
            if (newTrack.getTrackNumber() > head.getTrackNumber()) {
                cursor.setNext(newTrack);
                newTrack.setPrev(cursor);
                tail = newTrack;
                setCursor(newTrack);
            } else {
                newTrack.setNext(head);
                head.setPrev(newTrack);
                head = newTrack;
                cursor = newTrack;
            }
        } else {
            tempCursor = head;
            while (tempCursor != null) {
                if (tempCursor == head && tempCursor.getTrackNumber() > newTrack.
                  getTrackNumber()) {
                    newTrack.setNext(head);
                    head.setPrev(newTrack);
                    head = newTrack;
                    cursor = newTrack;
                    break;
                } else if (tempCursor == tail && tempCursor.getTrackNumber() <
                  newTrack.getTrackNumber()) {
                    tail.setNext(newTrack);
                    newTrack.setPrev(tail);
                    tail = newTrack;
                    cursor = newTrack;
                    break;
                } else if (tempCursor.getTrackNumber() < newTrack.getTrackNumber()
                  && tempCursor.getNext().getTrackNumber() > newTrack.
                  getTrackNumber()) {
                    newTrack.setNext(tempCursor.getNext());
                    tempCursor.getNext().setPrev(newTrack);
                    newTrack.setPrev(tempCursor);
                    tempCursor.setNext(newTrack);
                    cursor = newTrack;
                    break;
                }
                tempCursor = tempCursor.getNext();
            }
        }
        System.out.println("Track " + newTrack.getTrackNumber() + " added to " +
          "the station.");
    }

    /**
     * Removes the track that cursor is at
     * @return the track that is removed from the station
     */
    public Track removeSelectedTrack() {
        Track tempCursor = cursor;
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
            Track tempCursor2 = cursor.getNext();
            cursor.getNext().setPrev(cursor.getPrev());
            cursor.getPrev().setNext(cursor.getNext());
            cursor.setNext(null);
            cursor.setPrev(null);
            cursor = tempCursor2;
        }
        return tempCursor;
    }

    /**
     * Prints the cursor's track information.
     * If there is no track then it tells the user.
     */
    public void printSelectedTrack() {
        if (toString().equals(""))
            System.out.println("There are no tracks");
        else
            System.out.println(toString());
    }

    /**
     * Prints the information of all the tracks
     * If there is no track then it tells the user.
     */
    public void printAllTracks() {
        if (head == null)
            System.out.println("There is no tracks");
        Track tempCursor = head;
        while (tempCursor != null) {
            System.out.println("Track " + tempCursor.getTrackNumber() + ": (" +
              tempCursor.getUtilizationRate() + "% Utilization Rate)");
            tempCursor.printTableHeading();
            System.out.println(tempCursor.toString());
            tempCursor = tempCursor.getNext();
        }
    }

    /**
     * Prints out all the tracks that exists in the station and their utilization
     * rate but not the trains within the tracks
     */
    public void stationInformation() {
        Track tempCursor = head;
        int count = 0;
        while (tempCursor != null) {
            count++;
            tempCursor = tempCursor.getNext();
        }
        System.out.println("Station (" + count + " Tracks)");
        tempCursor = head;
        while (tempCursor != null) {
            System.out.println("Track " + tempCursor.getTrackNumber() + ": " +
              tempCursor.getAmountOfTrains() + " trains arriving (" + tempCursor.
              getUtilizationRate() + "% Utilization Rate)");
            tempCursor = tempCursor.getNext();
        }
        System.out.println("\n");
    }

    /**
     * Moves the cursor to a specific track
     * @param trackToSelect the track number that you want to move the cursor to
     * @return True if there exists a train with that number, false otherwise.
     */
    public boolean selectTrack(int trackToSelect) {
        Track tempCursor = head;
        while (tempCursor != null) {
            if (tempCursor.getTrackNumber() == trackToSelect) {
                cursor = tempCursor;
                return true;
            }
            tempCursor = tempCursor.getNext();
        }
        return false;
    }

    /**
     * Converts the tracks information into a string
     * @return a string representation of the track and the trains within it
     */
    public String toString() {
        String answer = "";
        if (cursor != null) {
            answer += "Track " + cursor.getTrackNumber() + "(" + cursor.
              getUtilizationRate() + "% Utilization Rate)\n";
            answer += (String.format("%-10s%-17s%-25s%-17s%-15s", "Selected",
              "Train Number", "Destination", "Arrival Time", "Departure Time") +
              "\n");
            for (int i = 0; i < 90; i++)
                answer += "-";
            answer += "\n";
            answer += cursor.toString() + "\n";
        }
        return answer;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        Station station = new Station();
        while (!exit) {
            try {
                System.out.println("|--------------------------------------" +
                  "---------------------------------------|");
                System.out.println("| Train Options                       |" +
                  " Track Options                         |");
                System.out.println("|    A. Add new Train                 |" +
                  "    TA. Add Track                      |");
                System.out.println("|    N. Select next Train             |" +
                  "    TR. Remove selected Track          |");
                System.out.println("|    V. Select previous Train         |" +
                  "    TS. Switch Track                   |");
                System.out.println("|    R. Remove selected Train         |" +
                  "   TPS. Print selected Track           |");
                System.out.println("|    P. Print selected Train          |" +
                  "   TPA. Print all Tracks               |");
                System.out.println("|--------------------------------------" +
                  "---------------------------------------|");
                System.out.println("| Station Options                      " +
                  "                                       |");
                System.out.println("|   SI. Print Station Information      " +
                  "                                       |");
                System.out.println("|    Q. Quit                           " +
                  "                                       |");
                System.out.println("|--------------------------------------" +
                  "---------------------------------------|");
                System.out.println("\nEnter a selection: ");
                String x = scan.nextLine();
                x = x.toLowerCase();
                switch (x) {
                    case "a":
                        System.out.println("Enter train number: ");
                        int trainNumber = scan.nextInt();
                        scan.nextLine();
                        System.out.println("Enter train destination: ");
                        String destination = scan.nextLine();
                        System.out.println("Enter train arrival time: ");
                        int arrivalTime = scan.nextInt();
                        scan.nextLine();
                        System.out.println("Enter train transfer time: ");
                        int transferTime = scan.nextInt();
                        scan.nextLine();
                        if (station.getHead() == null)
                            System.out.println("Train not added: There is no " +
                              "Track to add the Train to!");
                        else {
                            Train train = new Train(trainNumber, destination,
                              arrivalTime, transferTime);
                            station.getCursor().addTrain(train);
                            System.out.println("Train No. " + trainNumber +
                              " to " + destination + " added to Track " + station.
                              getCursor().getTrackNumber());
                        }
                        break;
                    case "n":
                        if (station.getCursor() != null) {
                            if (station.getCursor().selectNextTrain())
                                System.out.println("Cursor has been moved to " +
                                  "next train.");
                            else
                                System.out.println("Selected train not updated:" +
                                  " Already at end of Track list.\n");
                        } else
                            System.out.println("There is no track");
                        break;
                    case "v":
                        if (station.getCursor() != null) {
                            if (station.getCursor().selectPrevTrain())
                                System.out.println("Cursor has been moved to " +
                                  "previous train.");
                            else
                                System.out.println("Selected train not updated" +
                                  ": Already at beginning of Track list.\n");
                        } else
                            System.out.println("There is no track");
                        break;
                    case "r":
                        if (station.getCursor() != null) {
                            int selectedTrainNumber = station.getCursor().
                              removeSelectedTrain().getTrainNumber();
                            String selectedDestination = station.getCursor().
                              removeSelectedTrain().getDestination();
                            if (station.getCursor().removeSelectedTrain() == null)
                                System.out.println("There is no train to be removed");
                            else
                                System.out.println("Train No. " + selectedTrainNumber
                                  + " to " + selectedDestination + " has been" +
                                  " removed from Track " + station.getCursor().
                                  getTrackNumber());
                        } else
                            System.out.println("There is no track");
                        break;
                    case "p":
                        if (station.getCursor() != null)
                            station.getCursor().printSelectedTrain();
                        else
                            System.out.println("There is no track");
                        break;
                    case "tps":
                        station.printSelectedTrack();
                        break;
                    case "tpa":
                        station.printAllTracks();
                        break;
                    case "ts":
                        System.out.println("Enter the track number:");
                        int trackNumber = scan.nextInt();
                        scan.nextLine();
                        if (station.selectTrack(trackNumber))
                            System.out.println("Switch to Track " + trackNumber);
                        else
                            System.out.println("Could not switch tracks. Track "
                              + trackNumber + " does not exist.");

                        break;
                    case "ta":
                        System.out.println("Enter track number: ");
                        int addTrackNumber = scan.nextInt();
                        scan.nextLine();
                        station.addTrack(new Track(addTrackNumber));
                        break;
                    case "tr":
                        if (station.removeSelectedTrack() == null)
                            System.out.println("No track to be removed");
                        else
                            System.out.println("Closed Track " + station.
                              removeSelectedTrack().getTrackNumber());
                        break;
                    case "si":
                        station.stationInformation();
                        break;
                    case "q":
                        System.out.println("Program terminating successfully " +
                                "...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            } catch (InputMismatchException | IllegalArgumentException ex) {
                System.out.println(ex.toString() + "\n");
            }
        }

    }
}
