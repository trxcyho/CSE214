/**
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #7
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 *
 * Contains adjacency matrix for the organ donors and recipients
 */

import java.io.*;
import java.util.ArrayList;

public class TransplantGraph extends Patient implements Serializable {
    private ArrayList<Patient> donors, recipients;
    private boolean[][] connections;
    public static final int MAX_PATIENTS = 100;

    public TransplantGraph() {
        donors = new ArrayList<>();
        recipients = new ArrayList<>();
        connections = new boolean[1][1];
    }

    public ArrayList<Patient> getDonors() {
        return donors;
    }

    public void setDonors(ArrayList<Patient> donors) {
        this.donors = donors;
    }

    public ArrayList<Patient> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<Patient> recipients) {
        this.recipients = recipients;
    }

    public boolean[][] getConnections() {
        return connections;
    }

    public void setConnections(boolean[][] connections) {
        this.connections = connections;
    }

    /**
     * Creates and returns a new TransplantGraph object, initialized with donor
     * and recipient information found in files
     *
     * @param donorFile     the file that contains the donors information
     * @param recipientFile the file that contains the recipients information
     * @return a new Transplant Graph object
     * @throws IOException thrown since we are dealing with files
     */
    public static TransplantGraph buildFromFiles(String donorFile, String recipientFile) throws IOException {
        TransplantGraph transplantGraph = new TransplantGraph();
        //donor file
        FileInputStream fis = new FileInputStream(donorFile);
        InputStreamReader in = new InputStreamReader(fis);
        BufferedReader reader = new BufferedReader(in);
        String donor;
        while ((donor = reader.readLine()) != null) {
            String[] info = donor.split(", ");
            int id = Integer.parseInt(info[0]);
            String name = info[1];
            int age = Integer.parseInt(info[2]);
            String organ = info[3];
            String bloodType = info[4];
            Patient patient = new Patient(name, organ, age, bloodType);
            patient.setDonor(true);
            patient.setID(id);
            transplantGraph.getDonors().add(patient);

        }
        //recipient file
        FileInputStream fileInputStream = new FileInputStream(recipientFile);
        InputStreamReader isr = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(isr);
        String recipient;
        while ((recipient = bufferedReader.readLine()) != null) {
            String[] info = recipient.split(", ");
            int id = Integer.parseInt(info[0]);
            String name = info[1];
            int age = Integer.parseInt(info[2]);
            String organ = info[3];
            String bloodType = info[4];
            Patient patient = new Patient(name, organ, age, bloodType);
            patient.setID(id);
            patient.setDonor(false);
            transplantGraph.getRecipients().add(patient);
        }
        transplantGraph.updateTable();
        return transplantGraph;
    }

    /**
     * Adds the specified Patient to the recipients list
     *
     * @param patient the patient object to be added to the recipients list
     */
    public void addRecipient(Patient patient) {
        if (recipients.size() >= MAX_PATIENTS)
            throw new IllegalArgumentException("Patients are filled to max capacity.");
        else {
            patient.setID(recipients.size());
            recipients.add(patient);
            if (donors.size() != 0)
                updateTable();
        }
    }

    /**
     * Adds the Patient to the donors list
     *
     * @param patient the patient object to be added to the donors list
     */
    public void addDonor(Patient patient) {
        if (donors.size() >= MAX_PATIENTS)
            throw new IllegalArgumentException("Patients are filled to max capacity.");
        else {
            patient.setID(donors.size());
            donors.add(patient);
            if (recipients.size() != 0)
                updateTable();
        }
    }

    /**
     * Removes the specified Patient from the recipients list
     *
     * @param name the name of the specified Patient
     */
    public void removeRecipient(String name) {
        name = name.trim();
        int index = -1;
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).getName().equalsIgnoreCase(name)) {
                recipients.remove(i);
                index = i;
                break;
            }
        }
        if (index == -1)
            throw new IllegalArgumentException(name + " is not on the recipients list.");
        for (int j = index; j < recipients.size(); j++)
            recipients.get(j).setID(j);
        updateTable();
    }

    /**
     * Removes the specified Patient from the donors list
     *
     * @param name the name of the specified Patient
     */
    public void removeDonor(String name) {
        name = name.trim();
        int index = -1;
        for (int i = 0; i < donors.size(); i++) {
            if (donors.get(i).getName().equalsIgnoreCase(name)) {
                donors.remove(i);
                index = i;
                break;
            }
        }
        if (index == -1)
            throw new IllegalArgumentException(name + " is not on the donors list.");
        for (int j = index; j < donors.size(); j++)
            donors.get(j).setID(j);
        updateTable();
    }

    /**
     * Updates the adjacency matrix, connections, in this class
     */
    public void updateTable() {
        boolean[][] temp = new boolean[donors.size()][recipients.size()];
        for (int i = 0; i < donors.size(); i++) {
            for (int j = 0; j < recipients.size(); j++) {
                Patient tempDonor = donors.get(i);
                Patient tempRecipient = recipients.get(j);
                temp[i][j] = tempDonor.getOrgan().equalsIgnoreCase(tempRecipient.
                  getOrgan()) && isCompatible(recipients.get(j).getBloodType(),
                  donors.get(i).getBloodType());
            }
        }
        connections = temp;
    }

    /**
     * Prints all the information of patients on the recipients list
     */
    public void printAllRecipients() {
        updateTable();
        System.out.println(String.format("%5s | %-18s | %3s | %-13s | %-10s | %s",
          "Index", "Recipient Name", "Age", "Organ Needed", "Blood Type", "Donor IDs"));
        for (int j = 0; j < 72; j++)
            System.out.print("=");
        boolean comma = false;
        for (int i = 0; i < recipients.size(); i++) {
            System.out.println();
            System.out.print(recipients.get(i).toString());
            for (int j = 0; j < donors.size(); j++) {
                if (connections[j][i]) {
                    if (comma)
                        System.out.print(", ");
                    System.out.print(j);
                    comma = true;
                }
            }
            comma = false;
        }
    }

    /**
     * Prints all the information of patients on the donors list
     */
    public void printAllDonors() {
        updateTable();
        System.out.println(String.format("%5s | %-18s | %3s | %-13s | %-10s | %s",
          "Index", "Donor Name", "Age", "Organ Donated", "Blood Type", "Recipient IDs"));
        for (int j = 0; j < 72; j++)
            System.out.print("=");
        boolean comma = false;
        for (int i = 0; i < donors.size(); i++) {
            System.out.println();
            System.out.print(donors.get(i).toString());
            for (int j = 0; j < recipients.size(); j++) {
                if (connections[i][j]) {
                    if (comma)
                        System.out.print(", ");
                    System.out.print(j);
                    comma = true;
                }
            }
            comma = false;
        }
    }

    /**
     * Updates the number of connections of each patient
     */
    public void updateNumConnections() {
        //update connections of each array list
        int count = 0;
        for (int i = 0; i < donors.size(); i++) {
            for (int j = 0; j < recipients.size(); j++) {
                if (connections[i][j])
                    count++;
                if (j == recipients.size() - 1) {
                    donors.get(i).setNumConnections(count);
                    count = 0;

                }
            }
        }
        for (int i = 0; i < recipients.size(); i++) {
            for (int j = 0; j < donors.size(); j++) {
                if (connections[j][i])
                    count++;
                if (j == donors.size() - 1) {
                    recipients.get(i).setNumConnections(count);
                    count = 0;
                }
            }
        }


    }
}
