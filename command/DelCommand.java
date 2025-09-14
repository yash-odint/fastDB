package command;

import java.util.ArrayList;
import store.DataStore;

public class DelCommand implements CommandHandler{
    @Override
    public String handle(ArrayList<String> args){
        if (args.size() < 1) {
            return "-ERR wrong number of arguments for 'DEL' command\r\n";
        }

        String key = args.get(0);

        if (DataStore.getInstance().exists(key)) {
            DataStore.getInstance().delete(key);
            return ":1\r\n";
        } 

        return ":0\r\n";
    }
}
