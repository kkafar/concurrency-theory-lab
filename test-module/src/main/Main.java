package main;

import main.custom.Monitor;

import java.io.*;
import java.util.Arrays;

public class Main {
  private static final int N_ITER = 10;
  private static final int N_THREADS = 5;
  private static final int BUFF_SIZE = 5;
  private static final String DATA_PATH = "test-module/data";

  public static void main(String[] args) throws InterruptedException, IOException {
    fileIO(args);
  }

  public static void simpleMonitor(String[] args) throws InterruptedException {
    Monitor monitorInstance = new Monitor(0);
    Thread[] threadPoolIncrement = new Thread[N_THREADS / 2];
    Thread[] threadPoolDecrement = new Thread[N_THREADS / 2];

    for (int j = 0; j < N_THREADS / 2; ++j) {
      threadPoolIncrement[j] = new Thread(() -> {
        for (int i = 0; i < N_ITER; ++i) {
          monitorInstance.modifyValueBy(1);
        }
      });
      threadPoolDecrement[j] = new Thread(() -> {
        for (int i = 0; i < N_ITER; ++i) {
          monitorInstance.modifyValueBy(-1);
        }
      });
    }

    for (int j = 0; j < N_THREADS / 2; ++j) {
      threadPoolIncrement[j].start();
      threadPoolDecrement[j].start();
    }

    for (int j = 0; j < N_THREADS / 2; ++j) {
      threadPoolIncrement[j].join();
      threadPoolDecrement[j].join();
    }

    System.out.println("Value: " + monitorInstance.getSharedValue());
  }

  public static void fileIO(String[] args) throws IOException {
    File file = new File(DATA_PATH + "/mockData.txt");
    char[] charBuffer = new char[BUFF_SIZE];
    Arrays.fill(charBuffer, (char) 0);

    try {
      FileReader fileReader = new FileReader(file);
      int charactersRead;
      while ((charactersRead = fileReader.read(charBuffer, 0, charBuffer.length)) != -1) {
        String data = String.valueOf(charBuffer);
        System.out.println(data);
        Arrays.fill(charBuffer, (char) 0);
      }
    } catch (FileNotFoundException fnf) {
      System.out.print(fnf.getMessage());
      throw fnf;
    }
  }
}
