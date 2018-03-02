package outerhaven.cip.listing.three;

import java.util.HashSet;
import java.util.Set;

import outerhaven.cip.common.Immutable;
import outerhaven.cip.common.ThreadSafe;

/**
 * Listing 3.11. Immutable Class Built Out of Mutable Underlying Objects.<br><br>
 * 
 * Immutable objects can still use mutable objects internally to manage their state, as illustrated 
 * by ThreeStooges in Listing 3.11. While the Set that stores the names is mutable, the design of 
 * ThreeStooges makes it impossible to modify that Set after construction. The stooges reference is final, 
 * so all object state is reached through a final field. The last requirement, proper construction, 
 * is easily met since the constructor does nothing that would cause the this reference to become accessible 
 * to code other than the constructor and its caller.
 * 
 * @author threepwood
 *
 */
@Immutable
@ThreadSafe
final class ThreeStooges {
	private final Set<String> stooges = new HashSet<String>();

	public ThreeStooges() {
		stooges.add("Moe");
		stooges.add("Larry");
		stooges.add("Curly");
	}

	public boolean isStooge(String name) {
		return stooges.contains(name);
	}
}