import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RecordFileManager {
    private static final Path FILE = Paths.get("accidents.csv");

    public static void save(List<Accident> accidents) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(FILE)) {
            writer.write("id,location,date,time,type,vehicle,injured,severity");
            writer.newLine();
            for (Accident accident : accidents) {
                writer.write(accident.toCsv());
                writer.newLine();
            }
        }
        System.out.println("Records saved to " + FILE.toAbsolutePath());
    }

    public static List<Accident> load() throws IOException {
        List<Accident> accidents = new ArrayList<>();
        if (!Files.exists(FILE)) return accidents;

        try (BufferedReader reader = Files.newBufferedReader(FILE)) {
            String line = reader.readLine(); // Skip CSV header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length != 8) continue;
                try {
                    accidents.add(new Accident(
                        Integer.parseInt(parts[0]), parts[1], parts[2], parts[3],
                        parts[4], parts[5], Integer.parseInt(parts[6]),
                        Integer.parseInt(parts[7])
                    ));
                } catch (NumberFormatException ignored) {
                    // Skip malformed rows rather than stopping the import.
                }
            }
        }
        return accidents;
    }
}
