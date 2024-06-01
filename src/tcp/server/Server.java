//package tcp.server;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private int port;
    private int counter;

    public Server(int port) {
        this.port = port;
        this.counter = 0;
    }

    @Override
    public void run() {
        System.out.println("Server started at port: " + this.port);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.port);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Socket socket = null;
        while (true){
            try {
                socket = serverSocket.accept();
                this.incrementCounter();
                Worker worker = new Worker(socket);
                worker.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println(this.counter);
        }
    }
    public synchronized void incrementCounter(){
        this.counter++;
    }
    public static void main(String[] args) {
        Server server  = new Server(Integer.parseInt(System.getenv("SERVER_PORT")));
        server.start();
    }
}
