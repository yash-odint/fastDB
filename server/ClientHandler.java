package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;

import command.CommandHandler;
import command.SetCommand;

import java.util.Map;
import java.util.HashMap;

public class ClientHandler implements Runnable{
    
    private final Socket socket;
    private final Map<String, CommandHandler> commandRegistry = new HashMap<>();


    public ClientHandler(Socket socket){
        this.socket = socket;
        registerCommands();
    }

    private void registerCommands(){
        commandRegistry.put("SET", new SetCommand());
        commandRegistry.put("COMMAND", args -> "+OK");

    }

    @Override
    public void run(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                String command = parts[0].toUpperCase();
                String[] args = new String[parts.length - 1];
                System.arraycopy(parts, 1, args, 0, args.length);

                CommandHandler handler = commandRegistry.get(command);
                if (handler != null) {
                    String response = handler.handle(args);
                    writer.write(response + "\r\n");
                } else {
                    writer.write("-ERR unknown command\r\n");
                }
                writer.flush();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
}
