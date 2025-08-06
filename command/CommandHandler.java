package command;

import java.util.ArrayList;

public interface CommandHandler {

    String handle(ArrayList<String> args);
    
}