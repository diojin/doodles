package outerhaven.cip.listing.five;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import outerhaven.cip.common.GuardedBy;
import outerhaven.cip.common.NotThreadSafe;
/**
 * Listing 5.6. Iteration Hidden within String Concatenation. Don't Do this.
 * 
 * @author threepwood
 *
 */
@NotThreadSafe
public class HiddenIterator {
    @GuardedBy("this")
    private final Set<Integer> set = new HashSet<Integer>();
    public synchronized void add(Integer i) {
        set.add(i);
    }
    public synchronized void remove(Integer i) {
        set.remove(i);
    }
    public void addTenThings() {
        Random r = new Random();
        for (int i = 0; i < 10; i++)
            add(r.nextInt());
        System.out.println("DEBUG: added ten elements to " + set);  // this line, synchronization error
    }
}