import java.util.Scanner;

public class Accident {
    private int id;
    private String location;
    private String date;
    private String time;
    private String type;
    private String vehicle;
    private int injured;
    private int severity;

    public Accident(int id, String location, String date, String time,
                    String type, String vehicle, int injured, int severity) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.time = time;
        this.type = type;
        this.vehicle = vehicle;
        this.injured = injured;
        this.severity = severity;
    }

    public int getId() { return id; }
    public String getLocation() { return location; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getType() { return type; }
    public String getVehicle() { return vehicle; }
    public int getInjured() { return injured; }
    public int getSeverity() { return severity; }

    public void setLocation(String location) { this.location = location; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setType(String type) { this.type = type; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }
    public void setInjured(int injured) { this.injured = injured; }
    public void setSeverity(int severity) { this.severity = severity; }

    public String getSeverityType() {
        switch (severity) {
            case 1: return "MINOR";
            case 2: return "MODERATE";
            case 3: return "SERIOUS";
            case 4: return "CRITICAL";
            case 5: return "FATAL";
            default: return "UNKNOWN";
        }
    }

    public String getEmergencyService() {
        if (severity >= 4 || injured > 2) return "Ambulance, Police and Fire/Rescue";
        if (severity == 3 || injured > 0) return "Ambulance and Police";
        return "Police / Traffic Authority";
    }

    public void display() {
        System.out.println("----------------------------------------");
        System.out.println("Accident ID : " + id);
        System.out.println("Location    : " + location);
        System.out.println("Date        : " + date);
        System.out.println("Time        : " + time);
        System.out.println("Accident    : " + type);
        System.out.println("Vehicle     : " + vehicle);
        System.out.println("Injured     : " + injured);
        System.out.println("Severity    : " + severity + " - " + getSeverityType());
        System.out.println("Suggested response: " + getEmergencyService());
    }

    public String toCsv() {
        return id + "," + clean(location) + "," + clean(date) + "," + clean(time) + ","
                + clean(type) + "," + clean(vehicle) + "," + injured + "," + severity;
    }

    private String clean(String value) {
        return value == null ? "" : value.replace(",", " ");
    }
}
