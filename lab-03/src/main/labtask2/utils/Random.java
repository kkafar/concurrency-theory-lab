package main.labtask2.utils;

public class Random {
  public static int getRandomIndexInRange(
      final int lowerBound,
      final int upperBound
  ) {
    return Random.getRandomIntInRange(lowerBound, upperBound);
  }

  public static int getRandomIntInRange(
      final int lowerBound,
      final int upperBound
  ) {
    return (int) (Math.random() * (upperBound - lowerBound)) + lowerBound;
  }
}
