package main.actors;

public class SinglePortionGenerator implements PortionGenerator {

  @Override
  public int generatePortion() {
    return 1;
  }
}
