package main.ao.struct.impls;

import main.ao.struct.interfaces.Promise;
import main.ao.struct.interfaces.PromiseFactory;

public class UnsyncPromiseFactory implements PromiseFactory {
  @Override
  public <T> UnsyncPromise<T> create() {
    return new UnsyncPromise<T>();
  }
}
