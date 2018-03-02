package outerhaven.cip.listing.three;

import outerhaven.cip.common.GuardedBy;
import outerhaven.cip.common.ThreadSafe;

/**
 * Listing 3.3
 * @author threepwood
 *
 */
@ThreadSafe
public class SynchronizedInteger {
	@GuardedBy("this") private int value;
    public synchronized int get() { return value; }
    public synchronized void set(int value) { this.value = value; }
}
