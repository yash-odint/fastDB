package command;

public class SetCommand implements CommandHandler{
    
    @Override
    public String handle(String[] args){
        return "+OK\r\n";
    }
}
