package com.app;

import com.utils.Household;
import com.utils.RecyclingEvent;
import java.io.*;
import java.util.*;

/**
 * Main app to run the Eco-Points Recycling Tracker.
 */
public class EcoPointsRecyclingTracker {
    //This is where the rest of the code will be added

    private static Scanner scanner = new Scanner(System.in);

    // Create a HashMap to collect all the Household objects
    private static Map<String, Household> households = new HashMap<>();

    public static void main(String[] args) {
        boolean running = true;

        loadHouseholdsFromFile();

        while (running) {
            System.out.println("\n=== Eco-Points Recycling Tracker ===");
            System.out.println("1. Register Household");
            System.out.println("2. Log Recycling Event");
            System.out.println("3. Display Households");
            System.out.println("4. Display Household Recycling Events");
            System.out.println("5. Generate Reports");
            System.out.println("6. Save and Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerHousehold();
                    break;
                case "2":
                    logRecyclingEvent();
                    break;
                case "3":
                    displayHouseholds();
                    break;
                case "4":
                    displayHouseholdEvents();
                    break;
                case "5":
                    generateReports();
                    break;
                case "6":
                    saveHouseholdsToFile();
                    running = false;
                    System.out.println("Data saved. Goodbye!");
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    public static void registerHousehold() {
        System.out.println("Enter household ID: ");
        String id = scanner.nextLine().trim();

        if (households.containsKey(id)) {
            System.out.println("Error: Household ID already exists.");
            return;  
        }

        System.out.println("Enter household name: ");
        String name = scanner.nextLine().trim();

        System.out.println("Enter household address: ");
        String address = scanner.nextLine().trim();

        Household newHouseHold = new Household(id, name, address);

        households.put(id, newHouseHold);

        System.out.println("Household registered successfully on " + newHouseHold.getJoinDate());
    }

    public static void logRecyclingEvent() {
        System.out.println("Enter household ID: ");
        String id = scanner.nextLine().trim();

        if (!households.containsKey(id)) {
            System.out.println("Error:  Household ID not found.");
            return;
        }

        Household userHousehold = households.get(id);

        System.out.println("Enter material type (plastic/glass/metal/paper): ");
        String material = scanner.nextLine();

        double weight = 0.0;

        while (true) {
            try {
                System.out.println("Enter weight in kilograms: ");
                weight = scanner.nextDouble();

                if (weight <= 0) {
                    throw new IllegalArgumentException();
                }

                scanner.nextLine();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid weight. Must be a number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid weight. Must be a positive number.");
            }
        }

        RecyclingEvent newEvent = new RecyclingEvent(material, weight);
        userHousehold.addEvent(newEvent);

        System.out.println("Recycling event logged! Points earned: " + newEvent.getEcoPoints());
    }

    public static void displayHouseholds() {
        if (households.isEmpty()) {
            System.out.println("No households registered.");
            return;
        }

        System.out.println("\nRegistered Households:");

        for (Household h : households.values()) {
            System.out.println("ID: " + h.getId()
                    + ", Name: " + h.getName()
                    + ", Address: " + h.getAddress()
                    + ", Joined: " + h.getJoinDate());

        }
    }

    public static void displayHouseholdEvents() {
        // Get user ID
        System.out.println("Enter household ID: ");
        String id = scanner.nextLine().trim();

        if (!households.containsKey(id)) {
            System.out.println("Error:  Household ID not found.");
            return;
        }

        // Find the user and log it's events
        Household userHousehold = households.get(id);

        if (userHousehold.getEvents().isEmpty()) {
            System.out.println("No events logged.");
        } else {
            for (RecyclingEvent e : userHousehold.getEvents()) {
                System.out.println(e);
            }

            System.out.println("Total Weight: " + userHousehold.getTotalWeight() + " kg");

            System.out.println("Total Points: " + userHousehold.getTotalPoints() + " pts");
        }

    }

    public static void generateReports() {
        if (households.isEmpty()) {
            System.out.println("No households registered.");
            return; // Exit if there's nothing to report on
        }

        // Find and print the household with the highest points
        Household topHousehold = new Household(null, null, null);
        for (Household h : households.values()) {
            if (h.getTotalPoints() > topHousehold.getTotalPoints()) {
                topHousehold = h;
            }
        }

        System.out.println("\nHousehold with Highest Points:");
        System.out.println("ID: " + topHousehold.getId()
                + ", Name: " + topHousehold.getName()
                + ", Points: " + topHousehold.getTotalPoints());

        // Calculate and print total community recycling weight
        double totalWeight = 0.0;

        for (Household h : households.values()) {
            totalWeight += h.getTotalWeight();
        }

        System.out.println("Total Community Recycling Weight: " + totalWeight + " kg");

    }

    public static void saveHouseholdsToFile() {
        try {
            ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream("households.ser"));
            obj.writeObject(households);
            obj.close();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadHouseholdsFromFile() {
        // Use a try-with-resources block to automatically close the input stream
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("households.ser"))) {
            households = (Map<String, Household>) in.readObject();
            System.out.println("Household data loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

}
