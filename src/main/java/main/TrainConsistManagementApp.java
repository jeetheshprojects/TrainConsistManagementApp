package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Train Consist Management App
 * UC7 through UC14 implementation
 */
public class TrainConsistManagementApp {

    public static void main(String[] args) {
        System.out.println("=== Train Consist Management App ===");
        System.out.println();

        try {
            uc7SortBogies();
            uc8FilterBogies();
            uc9GroupBogies();
            uc10CountTotalSeats();
            uc11ValidateTrainIdAndCargoCodes();
            uc12SafetyComplianceCheck();
            uc13PerformanceComparison();
            uc14HandleInvalidBogieCapacity();
        } catch (InvalidCapacityException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("=== All use cases complete ===");
    }

    static List<Bogie> sortBogiesByCapacity(List<Bogie> bogies, boolean ascending) {
        if (ascending) {
            return bogies.stream()
                    .sorted(Comparator.comparingInt(Bogie::getCapacity))
                    .collect(Collectors.toList());
        }
        return bogies.stream()
                .sorted(Comparator.comparingInt(Bogie::getCapacity).reversed())
                .collect(Collectors.toList());
    }

    static List<Bogie> filterBogiesByMinCapacity(List<Bogie> bogies, int minCapacity) {
        return bogies.stream()
                .filter(b -> b.getCapacity() > minCapacity)
                .collect(Collectors.toList());
    }

    static Map<String, List<Bogie>> groupBogiesByName(List<Bogie> bogies) {
        return bogies.stream()
                .collect(Collectors.groupingBy(Bogie::getName));
    }

    static int countTotalSeats(List<Bogie> bogies) {
        return bogies.stream()
                .mapToInt(Bogie::getCapacity)
                .sum();
    }

    static boolean isValidTrainId(String trainId) {
        return Pattern.compile("TRN-\\d{4}")
                .matcher(trainId)
                .matches();
    }

    static boolean isValidCargoCode(String cargoCode) {
        return Pattern.compile("PET-[A-Z]{2}")
                .matcher(cargoCode)
                .matches();
    }

    static boolean isGoodsBogieListSafe(List<GoodsBogie> goodsBogies) {
        return goodsBogies.stream()
                .allMatch(b -> !"Cylindrical".equals(b.getType()) || "Petroleum".equals(b.getCargo()));
    }

    private static void uc7SortBogies() throws InvalidCapacityException {
        System.out.println("--- UC7: Sort Bogies Using Comparator ---");
        System.out.println();

        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 96));
        bogies.add(new Bogie("First Class", 48));
        bogies.add(new Bogie("General", 120));

        System.out.println("Unsorted bogies:");
        for (int i = 0; i < bogies.size(); i++) {
            System.out.println((i + 1) + ". " + bogies.get(i));
        }
        System.out.println();

        System.out.println("Sorting bogies by capacity (ascending)...");
        bogies.sort(Comparator.comparingInt(Bogie::getCapacity));
        System.out.println("Sorting completed.");
        System.out.println();

        System.out.println("Sorted bogies (by capacity ascending):");
        for (int i = 0; i < bogies.size(); i++) {
            System.out.println((i + 1) + ". " + bogies.get(i));
        }
        System.out.println();

        System.out.println("Sorting bogies by capacity (descending)...");
        bogies.sort(Comparator.comparingInt(Bogie::getCapacity).reversed());
        System.out.println("Sorting completed.");
        System.out.println();

        System.out.println("Sorted bogies (by capacity descending):");
        for (int i = 0; i < bogies.size(); i++) {
            System.out.println((i + 1) + ". " + bogies.get(i));
        }
        System.out.println();

        System.out.println("Key Benefits of Comparator:");
        System.out.println("✓ Flexible sorting without modifying the class");
        System.out.println("✓ Chain comparators for multiple criteria");
        System.out.println("✓ Reusable and composable");
        System.out.println("✓ Improves code readability and maintainability");
        System.out.println("✓ Supports both ascending and descending order");
        System.out.println();
    }

    private static void uc8FilterBogies() throws InvalidCapacityException {
        System.out.println("--- UC8: Filter Passenger Bogies Using Streams ---");
        System.out.println();

        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 96));
        bogies.add(new Bogie("First Class", 48));

        System.out.println("All passenger bogies:");
        for (int i = 0; i < bogies.size(); i++) {
            System.out.println((i + 1) + ". " + bogies.get(i));
        }
        System.out.println();

        System.out.println("Filtering bogies with seating capacity > 60...");
        List<Bogie> filteredBogies = bogies.stream()
                .filter(b -> b.getCapacity() > 60)
                .collect(Collectors.toList());
        System.out.println("Filtering completed.");
        System.out.println();

        System.out.println("Filtered bogies (capacity > 60):");
        if (filteredBogies.isEmpty()) {
            System.out.println("No bogies match the filter criteria.");
        } else {
            for (int i = 0; i < filteredBogies.size(); i++) {
                System.out.println((i + 1) + ". " + filteredBogies.get(i));
            }
        }
        System.out.println();

        System.out.println("Key Benefits of Stream API:");
        System.out.println("✓ Declarative filtering without manual loops");
        System.out.println("✓ Concise and readable business logic");
        System.out.println("✓ Functional programming style");
        System.out.println("✓ Improves code maintainability");
        System.out.println("✓ Prepares for advanced stream operations");
        System.out.println();
    }

    private static void uc9GroupBogies() throws InvalidCapacityException {
        System.out.println("--- UC9: Group Bogies by Type (Collectors.groupingBy) ---");
        System.out.println();

        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 96));
        bogies.add(new Bogie("First Class", 48));
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 96));

        System.out.println("All bogies in the train:");
        for (int i = 0; i < bogies.size(); i++) {
            System.out.println((i + 1) + ". " + bogies.get(i));
        }
        System.out.println();

        System.out.println("Grouping bogies by type...");
        Map<String, List<Bogie>> groupedBogies = bogies.stream()
                .collect(Collectors.groupingBy(Bogie::getName));
        System.out.println("Grouping completed.");
        System.out.println();

        System.out.println("Grouped bogies by type:");
        for (Map.Entry<String, List<Bogie>> entry : groupedBogies.entrySet()) {
            String type = entry.getKey();
            List<Bogie> group = entry.getValue();
            System.out.println("Type: " + type + " (" + group.size() + " bogies)");
            for (int i = 0; i < group.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + group.get(i));
            }
            System.out.println();
        }

        System.out.println("Key Benefits of Collectors.groupingBy:");
        System.out.println("✓ Transforms flat data into categorized structures");
        System.out.println("✓ Enables structured reporting and analysis");
        System.out.println("✓ Introduces advanced stream collectors");
        System.out.println("✓ Improves data organization for dashboards");
        System.out.println("✓ Builds foundation for analytics operations");
        System.out.println();
    }

    private static void uc10CountTotalSeats() throws InvalidCapacityException {
        System.out.println("--- UC10: Count Total Seats in Train (reduce) ---");
        System.out.println();

        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 96));
        bogies.add(new Bogie("First Class", 48));

        System.out.println("Bogies in the train:");
        for (int i = 0; i < bogies.size(); i++) {
            System.out.println((i + 1) + ". " + bogies.get(i));
        }
        System.out.println();

        System.out.println("Calculating total seating capacity...");
        int totalSeats = bogies.stream()
                .mapToInt(Bogie::getCapacity)
                .sum();
        System.out.println("Total seating capacity: " + totalSeats + " seats");
        System.out.println();

        System.out.println("Key Benefits of Stream reduce:");
        System.out.println("✓ Enables functional aggregation of data");
        System.out.println("✓ Provides quantitative metrics for planning");
        System.out.println("✓ Replaces manual summation loops");
        System.out.println("✓ Improves code readability and safety");
        System.out.println("✓ Builds foundation for advanced analytics");
        System.out.println();
    }

    private static void uc11ValidateTrainIdAndCargoCodes() {
        System.out.println("--- UC11: Validate Train ID & Cargo Codes (Regex) ---");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        String trainId = readLineOrDefault(scanner, "Enter Train ID (format: TRN-1234): ", "TRN-1234");
        String cargoCode = readLineOrDefault(scanner, "Enter Cargo Code (format: PET-AB): ", "PET-AB");

        Pattern trainIdPattern = Pattern.compile("TRN-\\d{4}");
        boolean isTrainIdValid = trainIdPattern.matcher(trainId).matches();

        Pattern cargoCodePattern = Pattern.compile("PET-[A-Z]{2}");
        boolean isCargoCodeValid = cargoCodePattern.matcher(cargoCode).matches();

        System.out.println();
        System.out.println("Validation Results:");
        System.out.println("Train ID '" + trainId + "': " + (isTrainIdValid ? "Valid" : "Invalid"));
        System.out.println("Cargo Code '" + cargoCode + "': " + (isCargoCodeValid ? "Valid" : "Invalid"));

        if (isTrainIdValid && isCargoCodeValid) {
            System.out.println("✓ All inputs are valid. Proceeding with train operations.");
        } else {
            System.out.println("✗ Invalid input detected. Please correct and try again.");
        }
        System.out.println();
    }

    private static String readLineOrDefault(Scanner scanner, String prompt, String defaultValue) {
        System.out.print(prompt);
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
        }
        System.out.println(defaultValue + " (default)");
        return defaultValue;
    }

    private static void uc12SafetyComplianceCheck() {
        System.out.println("--- UC12: Safety Compliance Check for Goods Bogies ---");
        System.out.println();

        List<GoodsBogie> goodsBogies = new ArrayList<>();
        goodsBogies.add(new GoodsBogie("Cylindrical", "Petroleum"));
        goodsBogies.add(new GoodsBogie("Rectangular", "Coal"));
        goodsBogies.add(new GoodsBogie("Cylindrical", "Petroleum"));
        goodsBogies.add(new GoodsBogie("Open", "Grain"));

        System.out.println("Goods bogies in the train:");
        for (int i = 0; i < goodsBogies.size(); i++) {
            System.out.println((i + 1) + ". " + goodsBogies.get(i));
        }
        System.out.println();

        System.out.println("Checking safety compliance...");
        boolean isSafe = goodsBogies.stream()
                .allMatch(b -> !"Cylindrical".equals(b.getType()) || "Petroleum".equals(b.getCargo()));
        System.out.println("Safety compliance check result: " + (isSafe ? "SAFE" : "UNSAFE"));
        System.out.println();

        if (isSafe) {
            System.out.println("✓ All goods bogies comply with safety rules.");
        } else {
            System.out.println("✗ Safety violation detected! Cylindrical bogies must carry Petroleum only.");
        }
        System.out.println();
    }

    private static void uc13PerformanceComparison() throws InvalidCapacityException {
        System.out.println("--- UC13: Performance Comparison (Loops vs Streams) ---");
        System.out.println();

        List<Bogie> bogies = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            String name = (i % 3 == 0) ? "Sleeper" : (i % 3 == 1) ? "AC Chair" : "First Class";
            int capacity = 30 + (i % 100);
            bogies.add(new Bogie(name, capacity));
        }
        System.out.println("Created " + bogies.size() + " bogies for performance testing.");
        System.out.println();

        long startTime = System.nanoTime();
        List<Bogie> loopFiltered = new ArrayList<>();
        for (Bogie bogie : bogies) {
            if (bogie.getCapacity() > 60) {
                loopFiltered.add(bogie);
            }
        }
        long endTime = System.nanoTime();
        long loopTime = endTime - startTime;

        startTime = System.nanoTime();
        List<Bogie> streamFiltered = bogies.stream()
                .filter(b -> b.getCapacity() > 60)
                .collect(Collectors.toList());
        endTime = System.nanoTime();
        long streamTime = endTime - startTime;

        System.out.println("Performance Comparison Results:");
        System.out.println("Loop-based filtering: " + loopFiltered.size() + " bogies, Time: " + loopTime + " ns");
        System.out.println("Stream-based filtering: " + streamFiltered.size() + " bogies, Time: " + streamTime + " ns");
        System.out.println();

        if (loopTime < streamTime) {
            System.out.println("✓ Loop-based approach was faster in this test.");
        } else if (streamTime < loopTime) {
            System.out.println("✓ Stream-based approach was faster in this test.");
        } else {
            System.out.println("✓ Both approaches had similar performance in this test.");
        }
        System.out.println();

        System.out.println("Key Benefits of Performance Benchmarking:");
        System.out.println("✓ Introduces measurement-driven optimization");
        System.out.println("✓ Compares imperative vs declarative styles");
        System.out.println("✓ Avoids premature performance assumptions");
        System.out.println("✓ Builds awareness of execution costs");
        System.out.println("✓ Encourages evidence-based development decisions");
        System.out.println();
    }

    private static void uc14HandleInvalidBogieCapacity() {
        System.out.println("--- UC14: Handle Invalid Bogie Capacity (Custom Exception) ---");
        System.out.println();

        List<Bogie> validBogies = new ArrayList<>();

        try {
            Bogie sleeper = new Bogie("Sleeper", 72);
            validBogies.add(sleeper);
            System.out.println("✓ Created valid bogie: " + sleeper);
        } catch (InvalidCapacityException e) {
            System.out.println("✗ Unexpected error: " + e.getMessage());
        }

        try {
            Bogie invalidBogie = new Bogie("Invalid", -10);
            System.out.println("✗ This should not print: " + invalidBogie);
        } catch (InvalidCapacityException e) {
            System.out.println("✓ Caught invalid capacity: " + e.getMessage());
        }

        try {
            Bogie zeroBogie = new Bogie("Zero", 0);
            System.out.println("✗ This should not print: " + zeroBogie);
        } catch (InvalidCapacityException e) {
            System.out.println("✓ Caught invalid capacity: " + e.getMessage());
        }

        try {
            Bogie acChair = new Bogie("AC Chair", 96);
            validBogies.add(acChair);
            System.out.println("✓ Created valid bogie: " + acChair);
        } catch (InvalidCapacityException e) {
            System.out.println("✗ Unexpected error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Valid bogies created: " + validBogies.size());
        for (Bogie b : validBogies) {
            System.out.println("- " + b);
        }
        System.out.println();

        System.out.println("Key Benefits of Custom Exceptions:");
        System.out.println("✓ Enforces business rules at object creation");
        System.out.println("✓ Prevents invalid data from entering the system");
        System.out.println("✓ Provides clear error messages");
        System.out.println("✓ Encourages fail-fast validation");
        System.out.println("✓ Improves system reliability");
        System.out.println();
    }
}

class InvalidCapacityException extends Exception {
    public InvalidCapacityException(String message) {
        super(message);
    }
}

class Bogie {
    private String name;
    private int capacity;

    public Bogie(String name, int capacity) throws InvalidCapacityException {
        if (capacity <= 0) {
            throw new InvalidCapacityException("Capacity must be greater than zero");
        }
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return name + " (" + capacity + " seats)";
    }
}

class GoodsBogie {
    private String type;
    private String cargo;

    public GoodsBogie(String type, String cargo) {
        this.type = type;
        this.cargo = cargo;
    }

    public String getType() {
        return type;
    }

    public String getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        return type + " bogie carrying " + cargo;
    }
}
