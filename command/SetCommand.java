package command;

import java.util.ArrayList;
import store.DataStore;

public class SetCommand implements CommandHandler{
    
    @Override
    public String handle(ArrayList<String> args){
        if (args.size() < 2) {
            return "-ERR wrong number of arguments for 'SET' command\r\n";
        }

        String key = args.get(0);
        String value = args.get(1);

        DataStore.getInstance().set(key, value);    

        return "+OK\r\n";
    }
}
