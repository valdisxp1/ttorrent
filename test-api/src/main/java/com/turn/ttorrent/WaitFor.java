package com.turn.ttorrent;

public abstract class WaitFor {
  public static final long DEFAULT_POLL_INTERVAL = 500;

  private boolean myResult = false;

  protected WaitFor() {
    this(60 * 1000);
  }

  protected WaitFor(long timeout, long pollInterval) {
    long maxTime = System.currentTimeMillis() + timeout;
    try {
      while (System.currentTimeMillis() < maxTime && !condition()) {
        Thread.sleep(pollInterval);
      }
      if (condition()) {
        myResult = true;
      }
    } catch (InterruptedException ignore) {
    }
  }

  protected WaitFor(long timeout) {
    this(timeout, DEFAULT_POLL_INTERVAL);
  }

  public boolean isMyResult() {
    return myResult;
  }

  protected abstract boolean condition();
}
