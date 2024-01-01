package me.kvdpxne.thrivi;

public final class StandardEventPriorities {

  public static final int MONITOR = Integer.MAX_VALUE;
  public static final int HIGHEST = 2_000_000;
  public static final int HIGH = 1_000_000;
  public static final int NORMAL = 0;
  public static final int LOW = -1_000_000;
  public static final int LOWEST = -2_000_000;

  private StandardEventPriorities() {
    super();
  }
}