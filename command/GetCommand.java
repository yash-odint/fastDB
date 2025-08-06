package command;

import java.util.ArrayList;
import store.DataStore;

public class GetCommand implements CommandHandler{
    @Override
    public String handle(ArrayList<String> args){
        if (args.size() < 1) {
            return "-ERR wrong number of arguments for 'GET' command\r\n";
        }

        String key = args.get(0);
        String value = DataStore.getInstance().get(key);

        if (value == null) {
            return "$-1\r\n";
        }

        return "$" + value.length() + "\r\n" + value + "\r\n";
    }
}
