//package tcp.logger.clientsLogger;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientLogger extends Thread{
    public int port;

    public ClientLogger(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("CLIENT LOGGER: STARTED");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = null;
            while (true){
                socket = serverSocket.accept();
                Worker worker = new Worker(socket,"../infoForClients.txt");
                worker.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ClientLogger clientLogger = new ClientLogger(7502);
        clientLogger.start();
    }
}
