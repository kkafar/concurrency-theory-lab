package main.utils;

public class UnitOfWork {
  private final int numberOfRepeats;

  public UnitOfWork(final int numberOfRepeats) {
    this.numberOfRepeats = numberOfRepeats;
  }

  private void work(long seed) {
    double res = Math.sin(seed * 31 + 17);
  }

  public void run() {
    for (int i = 0 ; i < numberOfRepeats; ++i) {
      work(i);
    }
  }
}
