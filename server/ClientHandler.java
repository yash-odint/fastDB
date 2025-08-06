package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;

import command.CommandHandler;
import command.SetCommand;
import command.GetCommand;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class ClientHandler implements Runnable{
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    private final Socket socket;
    private final Map<String, CommandHandler> commandRegistry = new HashMap<>();


    public ClientHandler(Socket socket){
        this.socket = socket;
        registerCommands();
    }

    private void registerCommands(){
        commandRegistry.put("SET", new SetCommand());
        commandRegistry.put("GET", new GetCommand());
        commandRegistry.put("COMMAND", args -> "+OK\r\n");
    }


    private String executeCommand(String command, ArrayList<String> args){
        CommandHandler handler = commandRegistry.get(command);
        if (handler == null) {
            return "-ERR unknown command\r\n";
        }
        return handler.handle(args);
    }

    private ArrayList<String> readRESPCommand(BufferedReader reader) throws IOException {
        ArrayList<String> result = new ArrayList<>();

        String line = reader.readLine();
        if (line == null || !line.startsWith("*")) {
            return result; 
        }

        int parts = Integer.parseInt(line.substring(1));
        for (int i = 0; i < parts; i++) {
            line = reader.readLine(); 
            if (line == null || !line.startsWith("$")) {
                return result;
            }

            String value = reader.readLine(); 
            result.add(value);
        }

        return result;
    }

    @Override
    public void run(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

            while (true) {
                ArrayList<String> parsedInput = readRESPCommand(reader);
            
                String command = parsedInput.get(0).toUpperCase();

                ArrayList<String> args = new ArrayList<>();

                for (int i = 1; i < parsedInput.size(); i++) {
                    args.add(parsedInput.get(i));
                }

                logger.info(command + " " + args.toString());

                String response = executeCommand(command, args);

                writer.write(response);
                writer.flush();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
}
