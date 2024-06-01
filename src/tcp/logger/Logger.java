
//import java.net.Socket;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Logger extends Thread{
    private int port;
    private String counterFile;

    public Logger(int port, String counterFile) {
        this.port = port;
        this.counterFile = counterFile;
    }

    @Override
    public void run() {
        System.out.println("Logger server started");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            new Worker(socket,new File(counterFile)).start();
        }
    }

    public static void main(String[] args) {
        Logger logger = new Logger(7001,"../data/data.txt");
        logger.start();
    }
}
