package main.ao.struct.interfaces;

public interface PromiseFactory {
  <T> Promise<T> create();
}
