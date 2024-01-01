package me.kvdpxne.thrivi;


public interface Cancellable {

  boolean isCancelled();

  void cancel();
}