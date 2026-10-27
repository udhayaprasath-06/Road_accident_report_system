import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AccidentService {
    private final List<Accident> accidents = new ArrayList<>();
    private int nextId = 1;

    public Accident register(String location, String date, String time, String type,
                             String vehicle, int injured, int severity) {
        Accident accident = new Accident(nextId++, location, date, time, type,
                vehicle, injured, severity);
        accidents.add(accident);
        return accident;
    }

    public List<Accident> getAll() {
        return new ArrayList<>(accidents);
    }

    public Accident findById(int id) {
        for (Accident accident : accidents) {
            if (accident.getId() == id) return accident;
        }
        return null;
    }

    public List<Accident> findByType(String type) {
        List<Accident> matches = new ArrayList<>();
        for (Accident accident : accidents) {
            if (accident.getType().equalsIgnoreCase(type)) matches.add(accident);
        }
        return matches;
    }

    public boolean delete(int id) {
        Accident accident = findById(id);
        return accident != null && accidents.remove(accident);
    }

    public void sortBySeverity() {
        accidents.sort(Comparator.comparingInt(Accident::getSeverity).reversed());
    }

    public int countSeverity(int severity) {
        int count = 0;
        for (Accident accident : accidents) {
            if (accident.getSeverity() == severity) count++;
        }
        return count;
    }

    public void replaceAll(List<Accident> loaded) {
        accidents.clear();
        accidents.addAll(loaded);
        nextId = 1;
        for (Accident accident : accidents) {
            if (accident.getId() >= nextId) nextId = accident.getId() + 1;
        }
    }
}
