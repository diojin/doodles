package outerhaven.cip.listing.five;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import outerhaven.cip.common.GuardedBy;
import outerhaven.cip.common.PhantomCode;
import outerhaven.cip.common.ThreadSafe;

interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}

class ExpensiveFunction implements Computable<String, BigInteger> {
    public BigInteger compute(String arg) {
        // after deep thought...
        return new BigInteger(arg);
    }
}

/**
 * Listing 5.16. Initial Cache Attempt Using HashMap and Synchronization. <br><br>
 * 
 * HashMap is not thread-safe, so to ensure that two threads do not access the HashMap at the same time, 
 * Memoizer1 takes the conservative approach of synchronizing the entire compute method. This ensures 
 * thread safety but has an obvious scalability problem: only one thread at a time can execute compute at all. 
 * If another thread is busy computing a result, other threads calling compute may be blocked for a long time. 
 * If multiple threads are queued up waiting to compute values not already computed, compute may actually 
 * take longer than it would have without memoization. This is not the sort of performance improvement 
 * we had hoped to achieve through caching.
 * 
 * @author threepwood
 *
 * @param <A>
 * @param <V>
 */
@ThreadSafe
class Memoizer1<A, V> implements Computable<A, V> {
    @GuardedBy("this")
    private final Map<A, V> cache = new HashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer1(Computable<A, V> c) {
        this.c = c;
    }

    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}

/**
 * Listing 5.17. Replacing HashMap with ConcurrentHashMap.   <br><br>
 * 
 * Memoizer2 certainly has better concurrent behavior than Memoizer1: multiple threads can actually 
 * use it concurrently. But it still has some defects as a cache there is a window of vulnerability 
 * in which two threads calling compute at the same time could end up computing the same value. 
 * In the case of memoization, this is merely inefficient. the purpose of a cache is to prevent the same data 
 * from being calculated multiple times. For a more general-purpose caching mechanism, it is far worse; 
 * for an object cache that is supposed to provide once-and-only-once initialization, this vulnerability 
 * would also pose a safety risk.
 * 
 * @author threepwood
 *
 * @param <A>
 * @param <V>
 */
@ThreadSafe
class Memoizer2<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}

/**
 * Listing 5.18. Memoizing Wrapper Using FutureTask.<br><br>
 * 
 * It has only one defect there is still a small window of vulnerability in which two threads might compute the same 
 * value. This window is far smaller than in Memoizer2, but because the if block in compute is still 
 * a nonatomic check-then-act sequence, it is possible for two threads to call compute with the same value 
 * at roughly the same time, both see that the cache does not contain the desired value, and both start 
 * the computation. <br><br>
 * 
 * Memoizer3 is vulnerable to this problem because a compound action (put-if-absent) is performed on the 
 * backing map that cannot be made atomic using locking. Memoizer in Listing 5.19 takes advantage of the atomic 
 * putIfAbsent method of ConcurrentMap, closing the window of vulnerability in Memoizer3.
 * 
 * @author threepwood
 *
 * @param <A>
 * @param <V>
 */
@ThreadSafe
class Memoizer3<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> eval = new Callable<V>() {
                public V call() throws InterruptedException {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(eval);
            f = ft;
            cache.put(arg, ft);
            ft.run(); // call to c.compute happens here
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }
    
    @PhantomCode
    private RuntimeException launderThrowable(Throwable t){
    	return null;
    }
}

/**
 * Listing 5.19. Final Implementation of Memoizer.<br><br>
 * 
 * Caching a Future instead of a value creates the possibility of cache pollution: if a computation 
 * is cancelled or fails, future attempts to compute the result will also indicate cancellation or failure. 
 * To avoid this, Memoizer removes the Future from the cache if it detects that the computation was cancelled; 
 * it might also be desirable to remove the Future upon detecting a RuntimeException if the computation might 
 * succeed on a future attempt. Memoizer also does not address cache expiration, but this could be 
 * accomplished by using a subclass of FutureTask that associates an expiration time with each result 
 * and periodically scanning the cache for expired entries. (Similarly, it does not address cache eviction, 
 * where old entries are removed to make room for new ones so that the cache does not consume too much memory.)
 * 
 * @author threepwood
 *
 * @param <A>
 * @param <V>
 */
@ThreadSafe
public class Memoizer<A, V> implements Computable<A, V> {
    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = new Callable<V>() {
                    public V call() throws InterruptedException {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(eval);
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                throw launderThrowable(e.getCause());
            }
        }
    }
    
    @PhantomCode
    private RuntimeException launderThrowable(Throwable t){
    	return null;
    }
}

