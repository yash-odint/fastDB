package command;

import java.util.ArrayList;
import store.DataStore;

public class ExpireCommand implements CommandHandler{
    public String handle(ArrayList<String> args){
        if (args.size() < 2) {
            return "-ERR wrong number of arguments for 'EXPIRE' command\r\n";
        }

        String key = args.get(0);
        long timeInSeconds = Integer.parseInt(args.get(1));

        if (!DataStore.getInstance().exists(key)) {
            return "$0\r\n";
        }

        DataStore.getInstance().setExpiry(key, timeInSeconds);

        return "+OK\r\n";
    }
}
