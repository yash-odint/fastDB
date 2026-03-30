package store;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class DataStore {
    private static final DataStore instance = new DataStore();

    private final Map<String, String> data = new HashMap<>();
    private final Map<String, Long> expiry = new HashMap<>();

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

    public boolean isExpired(String key){
        if (!expiry.containsKey(key)) {
            return false;
        }
        return expiry.get(key) - Instant.now().getEpochSecond() <= 0L;
    }

    public void purgeIfExpired(String key){
        if(isExpired(key)){
            expiry.remove(key);
            data.remove(key);
        }
    }

    public void setExpiry(String key, Long seconds){
        expiry.put(key, Instant.now().getEpochSecond() + seconds);
    }
}
