package outerhaven.cip.listing.four;

import outerhaven.cip.common.GuardedBy;
import outerhaven.cip.common.ThreadSafe;

/**
 * Listing 4.1. Simple Thread-safe Counter Using the Java Monitor Pattern.
 * @author threepwood
 *
 */
@ThreadSafe
public final class Counter {
    @GuardedBy("this")
    private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE)
            throw new IllegalStateException("counter overflow");
        return ++value;
    }
}