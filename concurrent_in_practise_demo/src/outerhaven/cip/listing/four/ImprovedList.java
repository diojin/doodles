package outerhaven.cip.listing.four;

import java.util.ArrayList;
import java.util.List;

import outerhaven.cip.common.ConditionalThreadSafe;
import outerhaven.cip.common.ThreadSafe;

/**
 * Listing 4.16. Implementing Put-if-absent Using Composition.<br><br>
 * 
 * In effect, we've used the Java monitor pattern to encapsulate an existing List, and this is 
 * guaranteed to provide thread safety so long as our class holds the only outstanding reference 
 * to the underlying List.<br><br>
 * 
 * Like Collections.synchronizedList and other collections wrappers, ImprovedList assumes that 
 * once a list is passed to its constructor, the client will not use the underlying list directly again,!!! 
 * accessing it only through the ImprovedList.
 * 
 * @author threepwood
 *
 * @param <T>
 */
@ConditionalThreadSafe
public abstract class ImprovedList<T> implements List<T> {
    private final List<T> list;
    public ImprovedList(List<T> list) {
        this.list = list;
    }
    public synchronized boolean putIfAbsent(T x) {
        boolean contains = list.contains(x);
        if (contains)
            list.add(x);
		return !contains;
    }
        
    public synchronized void clear() {
        list.clear();
    }
    // ... similarly delegate other List methods
}

/**
 * Now it is thread safe since delegated List is not used by any other codes
 * 
 * @author threepwood
 *
 * @param <T>
 */
@ThreadSafe
abstract class ImprovedList1<T> implements List<T> {
    private final List<T> list;
    public ImprovedList1() {
        this.list = new ArrayList<T>();
    }
    public synchronized boolean putIfAbsent(T x) {
        boolean contains = list.contains(x);
        if (!contains)
            list.add(x);
		return !contains;
    }
        
    public synchronized void clear() {
        list.clear();
    }
    // ... similarly delegate other List methods
}

