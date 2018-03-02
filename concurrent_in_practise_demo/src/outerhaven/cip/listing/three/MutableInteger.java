package outerhaven.cip.listing.three;

import outerhaven.cip.common.NotThreadSafe;

/**
 * Listing 3.2
 * @author threepwood
 *
 */
@NotThreadSafe
public class MutableInteger {
	private int value;

	public int get() {
		return value;
	}

	public void set(int value) {
		this.value = value;
	}
}
