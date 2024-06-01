//package tcp.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread{
    private int port;
    private String host;

    public Client(String host, int port) {
        this.port = port;
        this.host = host;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getByName(host),port);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("GET / HTTP/1.1\n\n");
            bufferedWriter.flush();
            bufferedWriter.write("login\n");
            bufferedWriter.flush();
            bufferedWriter.write("Hello, world!\n");
            bufferedWriter.flush();
            bufferedWriter.write("end\n");
            bufferedWriter.flush();
            bufferedReader.lines().forEach(line -> System.out.println(line));


            bufferedWriter.flush();
            bufferedWriter.close();
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client(System.getenv("SERVER_HOST"),Integer.parseInt(System.getenv("SERVER_PORT")));
        client.start();
    }
}
