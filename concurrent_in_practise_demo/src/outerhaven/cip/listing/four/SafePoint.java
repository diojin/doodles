package outerhaven.cip.listing.four;

import outerhaven.cip.common.GuardedBy;
import outerhaven.cip.common.NotThreadSafe;
import outerhaven.cip.common.ThreadSafe;

/**
 * Listing 4.11. Thread-safe Mutable Point Class.<br><br>
 * 
 * private constructor capture idiom<br><br>
 * 
 * The private constructor exists to avoid the race condition that would occur 
 * if the copy constructor were implemented as this(p.x, p.y); this is an example of the private 
 * constructor capture idiom (Bloch and Gafter, 2005).
 * 
 * @author threepwood
 *
 */
@ThreadSafe
public class SafePoint {
	@GuardedBy("this")
	private int x, y;

	// can't be public, since variable of type int[] has synchronous issue
	private SafePoint(int[] a) {
		this(a[0], a[1]);
	}

	public SafePoint(SafePoint p) {
		this(p.get());
	}

	// it is alright this constructor is public, cuz due to minimal knowledge principle, 
	// it doesn't assume if parameter x, y is a valid point or not in the caller's context
	// and x, y is primitive type, no synchronous issue.
	public SafePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// key is no separate method getX or getY is declared
	public synchronized int[] get() {
		return new int[] { x, y };
	}

	public synchronized void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

/**
 * try to rewrite it in another way, not thread safe now
 * @author threepwood
 *
 */
@NotThreadSafe
class SafePoint1 {
	@GuardedBy("this")
	private int x, y;

	// not thread safe, since p.x and p.y are not synchronized, could get stale value
	public SafePoint1(SafePoint1 p) {
		this(p.x, p.y);
	}

	public SafePoint1(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// key is no separate method getX or getY is declared
	public synchronized int[] get() {
		return new int[] { x, y };
	}

	public synchronized void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

/**
 * rewrite it again, thread safe now???<br><br>
 * still not, separately get either x or y even with synchronization still can't guarantee atomicity
 * @author threepwood
 *
 */
@NotThreadSafe
class SafePoint2 {
	@GuardedBy("this")
	private int x, y;

	// not thread safe, no atomicity
	public SafePoint2(SafePoint2 p) {
		this(p.getX(), p.getY());
	}

	public SafePoint2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private synchronized int getX(){
		return x;
	}
	
	private synchronized int getY(){
		return y;
	}
	
	// key is no separate method getX or getY is declared
	public synchronized int[] get() {
		return new int[] { x, y };
	}

	public synchronized void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

/**
 * rewrite the 3rd time following original SafePoint's principle, 
 * get compilation error since "Constructor call must be the first statement in a constructor"
 * so the idea is right and thread safe, however, there is grammar limitation
 * @author threepwood
 *
 */
@ThreadSafe
class SafePoint3 {
	@GuardedBy("this")
	private int x, y;

	public SafePoint3(SafePoint3 p) {
//		int[] point = p.get(); 
//		this(point[0], point[1]);
	}

	// it is alright this constructor is public, cuz due to minimal knowledge principle, 
	// it doesn't assume if parameter x, y is a valid point or not in the caller's context
	// and x, y is primitive type, no synchronous issue.
	public SafePoint3(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// key is no separate method getX or getY is declared
	public synchronized int[] get() {
		return new int[] { x, y };
	}

	public synchronized void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
