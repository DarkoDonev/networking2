//package tcp.server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Worker extends Thread{
    private Socket socket;
    public Worker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        List<String> commands = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("HTTP/1.1 200 OK\n\n");
            bufferedWriter.flush();
            String line;
            bufferedReader.readLine();
            bufferedReader.readLine();
             commands = new ArrayList<>();
            while (!(line = bufferedReader.readLine()).equals("end") ){
                commands.add(line);
            }
            for (String command : commands) {
                bufferedWriter.write(command + "\n");
                bufferedWriter.flush();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.sharedLogger();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.clientLogger(commands);
    }
    private void clientLogger(List<String> commands) {
        Socket loggerSocket = null;
        try {
            loggerSocket = new Socket(InetAddress.getByName("clientlogger"),7502);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(loggerSocket.getOutputStream()));
            bufferedWriter.write(loggerSocket.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.write(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            bufferedWriter.newLine();
            bufferedWriter.write("Client Sent: ");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            for (int i = 0; i < commands.size(); i++) {
                bufferedWriter.write(commands.get(i));
                bufferedWriter.flush();
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void sharedLogger() throws IOException {
        String serverName = System.getenv("LOGGER_SERVER_NAME");
        String serverPort = System.getenv("LOGGER_SERVER_PORT");
        Socket loggerSocket = null;
        try {
            loggerSocket = new Socket(InetAddress.getByName(serverName),Integer.parseInt(serverPort));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(loggerSocket.getOutputStream()));
        bufferedWriter.write(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
