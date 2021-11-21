package main.buffer.interfaces;

public interface BufferFactory<DataType, PutRequestAnswerType, TakeRequestAnswerType> {
  Buffer<DataType, PutRequestAnswerType, TakeRequestAnswerType> create(final int size, final boolean log);
}
