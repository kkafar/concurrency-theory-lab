package main;

import main.custom.ClassWithExpressiveConstructor;

public class Main {
  private static final int SIZE = 3;

  public static void main(String[] args) {
    ClassWithExpressiveConstructor[] arr = new ClassWithExpressiveConstructor[SIZE];

    for (int i = 0; i < SIZE; ++i) {
      arr[i] = new ClassWithExpressiveConstructor("whatever");
    }

  }
}
