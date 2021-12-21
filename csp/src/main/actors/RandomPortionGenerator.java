package main.actors;

public class RandomPortionGenerator implements PortionGenerator {
  private final int mLowerBound;
  private final int mUpperBound;

  public RandomPortionGenerator(int lowerBound, int upperBound) {
    mLowerBound = lowerBound;
    mUpperBound = upperBound;
  }

  @Override
  public int generatePortion() {
    return (int)(Math.random() * (mUpperBound - mLowerBound) + mLowerBound);
  }
}
