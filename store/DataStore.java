package store;

import java.util.HashMap;
import java.util.Map;

public class DataStore {
    private static final DataStore instance = new DataStore();

    private final Map<String, String> data = new HashMap<>();

    private DataStore() {}

    public static DataStore getInstance(){
        return instance;
    }

    public void set(String key, String value){
        data.put(key, value);
    }

    public String get(String key){
        return data.get(key);
    }

    public boolean exists(String key){
        return data.containsKey(key);
    }

    public void delete(String key){
        data.remove(key);
    }
}
