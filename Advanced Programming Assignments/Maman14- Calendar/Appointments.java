import java.util.*;

public class Appointments<K, V> {
    private Map<Date, String> treeMap;

    public Appointments() {
        treeMap = new TreeMap<Date, String>();
    }

    public boolean foundInApp(Date key) {
        return this.treeMap.containsKey(key);
    }

    public void add(Date key, String value) {
        if (!foundInApp(key)) {
            treeMap.put(key, value);
        }
    }

    public void update(Date key, String value) {
        if (foundInApp(key)) {
            treeMap.put(key, value);
        }
    }

    public void delete(Date key) {
        if (foundInApp(key))
            treeMap.remove(key);
    }

    public String searchKey(Date key) {
        String value = "";
        for (Map.Entry<Date, String>
                entry : treeMap.entrySet()) {
            if (key.equals(entry.getKey()))
                value = " " + entry.getValue();
        }
        return value;
    }

    public String toString() {
        String appVar = "";
        for (Map.Entry<Date, String>
                entry : treeMap.entrySet()) {
            appVar += (entry.getKey() + "\t" + "-" + "\t" + entry.getValue() + "\n");
        }
        return appVar;
    }
}
