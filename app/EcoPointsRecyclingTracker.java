package EcoPointsRecyclingTrackerApp.app;

import EcoPointsRecyclingTrackerApp.utils.Household;
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

            // You will handle the choice entered here using switch(case)
            switch (choice) {
                case "1":
                    registerHousehold();
                    break;
                case "2":

                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    public static void registerHousehold() {
        // Register household
        System.out.println("Enter household ID: ");
        String id = scanner.nextLine().trim();

        if (households.containsKey(id)) {
            System.out.println("Error: Household ID already exists.");
            return;  // Stop and return early if duplicate found
        }

        System.out.println("Enter household name");
        String name = scanner.nextLine().trim();

        System.out.println("Enter household address");
        String address = scanner.nextLine().trim();

        Household newHouseHold = new Household(id, name, address);

        households.put(id, newHouseHold);

        // Confirm to the user that the household was registered successfully
        System.out.println("Household registered successfully on " + newHouseHold.getJoinDate());
    }
}
