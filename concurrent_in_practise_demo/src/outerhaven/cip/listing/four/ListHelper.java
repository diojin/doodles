package outerhaven.cip.listing.four;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import outerhaven.cip.common.NotThreadSafe;
import outerhaven.cip.common.ThreadSafe;

/**
 * Listing 4.15. Implementing Put-if-absent with Client-side Locking.<br><br>
 * 
 * To make this approach work, we have to use the same lock that the List uses by using 
 * client-side locking or external locking. <br><br>
 * 
 * @author threepwood
 *
 * @param <E>
 */
@ThreadSafe
public class ListHelper<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent)
                list.add(x);
            return absent;
        }
    }
}

/**
 * Listing 4.14. Non-thread-safe Attempt to Implement Put-if-absent. Don't Do this<br><br>
 * 
 * The problem is that it synchronizes on the wrong lock.
 * 
 * @author threepwood
 *
 * @param <E>
 */
@NotThreadSafe
class ListHelper1<E> {
	public List<E> list = Collections.synchronizedList(new ArrayList<E>());

	// ...
	public synchronized boolean putIfAbsent(E x) {
		boolean absent = !list.contains(x);
		if (absent)
			list.add(x);
		return absent;
	}
}