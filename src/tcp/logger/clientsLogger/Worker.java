//package tcp.logger.clientsLogger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread{
    private Socket socket;
    String file;
    public static Lock lock = new ReentrantLock();

    public Worker(Socket socket, String file) {
        this.socket = socket;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            Scanner scanner = new Scanner(socket.getInputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
            List<String> list = new ArrayList<>();
            bufferedWriter.write("/////////////////////////////////////////////////////");
            while (scanner.hasNextLine()){
                list.add(scanner.nextLine());
            }
            for (int i = 0; i < list.size(); i++) {
                bufferedWriter.write(list.get(i));
                bufferedWriter.flush();
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedWriter.flush();
            bufferedWriter.flush();
            bufferedWriter.close();
            scanner.close();
            lock.unlock();
        } catch (IOException e) {
            lock.unlock();
            throw new RuntimeException(e);
        }
    }
}
