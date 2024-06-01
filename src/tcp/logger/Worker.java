//package tcp.logger;//package tcp.logger;

import java.io.*;
import java.net.Socket;

public class Worker extends Thread{
    private Socket socket;
    private File counterFile;

    public Worker(Socket socket, File counterFile) {
        this.socket = socket;
        this.counterFile = counterFile;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String numberAsString = bufferedReader.readLine();
//            int number = Integer.parseInt(numberAsString) + 1;
            this.writeToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void writeToFile() throws IOException {
        File counterFile = new File("../data/data.txt");

        // Read the current value
        int currentValue = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(counterFile)))) {
            String line = bufferedReader.readLine();
            if (line != null) {
                currentValue = Integer.parseInt(line);
            }
        }

        // Update the value
        int newValue;
        if (currentValue != 0) {
            newValue = currentValue + 1;
        } else {
            newValue = 1;
        }

        // Write the new value
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(counterFile)))) {
            bufferedWriter.write(String.valueOf(newValue));
            bufferedWriter.flush();  // Flush the writer to ensure all data is written to the file
        }
    }


}
