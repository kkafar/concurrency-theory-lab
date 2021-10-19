package main.lab;

public class SynchronizedBuffer {
  int buffer;

  public SynchronizedBuffer() {
    buffer = 1;
  }

  public synchronized void put() throws InterruptedException {
    System.out.println("PRODUCER STARTED TO PUT ITEM");
    while (buffer == 1){
      System.out.println("PRODUCER WAITS TO PUT ITEM BUFFOR: " + buffer);
      this.wait();
      System.out.println("PRODUCER AWAKED: " + buffer);
    }

    buffer = 1;

    System.out.println("PRODUCER PUT ITEM");
    this.notify();
  }

  public synchronized int take() throws InterruptedException {
    System.out.println("CONSUMER WANTS TO TAKE ITEM");
    while (buffer == 0) {
      System.out.println("CONSUMER WAITS TO TAKE ITEM BUFOR: " + buffer);
      this.wait();
      System.out.println("CONSUMER AWAKED: " + buffer);
    }

    buffer = 0;
    System.out.println("CONSUMER TOOK ITEM");
    this.notify();

    return 1;
  }
}
