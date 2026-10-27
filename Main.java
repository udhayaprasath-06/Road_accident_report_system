import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final AccidentService SERVICE = new AccidentService();

    public static void main(String[] args) {
        try {
            SERVICE.replaceAll(RecordFileManager.load());
        } catch (IOException e) {
            System.out.println("Could not load saved records: " + e.getMessage());
        }

        int choice;
        do {
            showMenu();
            choice = readInt("Choose an option: ");
            switch (choice) {
                case 1: registerAccident(); break;
                case 2: displayRecords(SERVICE.getAll()); break;
                case 3: searchById(); break;
                case 4: searchByType(); break;
                case 5: updateAccident(); break;
                case 6: deleteAccident(); break;
                case 7:
                    SERVICE.sortBySeverity();
                    displayRecords(SERVICE.getAll());
                    break;
                case 8: showStatistics(); break;
                case 9: saveRecords(); break;
                case 0: saveRecords(); System.out.println("Exiting application."); break;
                default: System.out.println("Invalid option. Try again.");
            }
        } while (choice != 0);
    }

    private static void showMenu() {
        System.out.println("\\n===== ROAD ACCIDENT REPORTING SYSTEM =====");
        System.out.println("1. Register accident");
        System.out.println("2. Display all records");
        System.out.println("3. Search by accident ID");
        System.out.println("4. Search by accident type");
        System.out.println("5. Update accident");
        System.out.println("6. Delete accident");
        System.out.println("7. Sort by severity");
        System.out.println("8. View statistics");
        System.out.println("9. Save records");
        System.out.println("0. Save and exit");
    }

    private static void registerAccident() {
        System.out.println("\\n--- Register Accident ---");
        String location = readText("Location: ");
        String date = readText("Date (DD-MM-YYYY): ");
        String time = readText("Time: ");
        String type = readText("Accident type: ");
        String vehicle = readText("Vehicle type: ");
        int injured = readNonNegativeInt("Number of injured people: ");
        int severity = readSeverity();
        Accident accident = SERVICE.register(location, date, time, type, vehicle, injured, severity);
        System.out.println("Accident registered. ID: " + accident.getId());
        System.out.println("Suggested emergency response: " + accident.getEmergencyService());
    }

    private static void searchById() {
        int id = readInt("Enter accident ID: ");
        Accident accident = SERVICE.findById(id);
        if (accident == null) System.out.println("No accident found with that ID.");
        else accident.display();
    }

    private static void searchByType() {
        String type = readText("Enter accident type: ");
        displayRecords(SERVICE.findByType(type));
    }

    private static void updateAccident() {
        int id = readInt("Enter accident ID to update: ");
        Accident accident = SERVICE.findById(id);
        if (accident == null) {
            System.out.println("Accident not found.");
            return;
        }
        System.out.println("Enter the new details.");
        accident.setLocation(readText("Location: "));
        accident.setDate(readText("Date (DD-MM-YYYY): "));
        accident.setTime(readText("Time: "));
        accident.setType(readText("Accident type: "));
        accident.setVehicle(readText("Vehicle type: "));
        accident.setInjured(readNonNegativeInt("Number of injured people: "));
        accident.setSeverity(readSeverity());
        System.out.println("Accident updated.");
    }

    private static void deleteAccident() {
        int id = readInt("Enter accident ID to delete: ");
        System.out.println(SERVICE.delete(id) ? "Accident deleted." : "Accident not found.");
    }

    private static void displayRecords(List<Accident> accidents) {
        if (accidents.isEmpty()) {
            System.out.println("No accident records found.");
            return;
        }
        for (Accident accident : accidents) accident.display();
    }

    private static void showStatistics() {
        List<Accident> accidents = SERVICE.getAll();
        System.out.println("\\n--- Accident Statistics ---");
        System.out.println("Total records: " + accidents.size());
        String[] names = {"", "MINOR", "MODERATE", "SERIOUS", "CRITICAL", "FATAL"};
        for (int severity = 1; severity <= 5; severity++) {
            System.out.println(names[severity] + ": " + SERVICE.countSeverity(severity));
        }
    }

    private static void saveRecords() {
        try {
            RecordFileManager.save(SERVICE.getAll());
        } catch (IOException e) {
            System.out.println("Could not save records: " + e.getMessage());
        }
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static int readNonNegativeInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value >= 0) return value;
            System.out.println("Value cannot be negative.");
        }
    }

    private static int readSeverity() {
        while (true) {
            System.out.println("Severity: 1-MINOR, 2-MODERATE, 3-SERIOUS, 4-CRITICAL, 5-FATAL");
            int severity = readInt("Severity level (1-5): ");
            if (severity >= 1 && severity <= 5) return severity;
            System.out.println("Severity must be between 1 and 5.");
        }
    }
}
