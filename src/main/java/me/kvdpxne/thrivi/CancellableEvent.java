package me.kvdpxne.thrivi;

public class CancellableEvent extends Event implements Cancellable {

  private boolean cancelled;

  protected CancellableEvent(final boolean cancelled) {
    this.cancelled = cancelled;
  }

  protected CancellableEvent() {
    this(false);
  }

  @Override
  public boolean isCancelled() {
    return this.cancelled;
  }

  @Override
  public void cancel() {
    this.cancelled = true;
  }
}