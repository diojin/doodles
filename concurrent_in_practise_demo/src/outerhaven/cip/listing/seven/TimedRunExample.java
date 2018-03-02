package outerhaven.cip.listing.seven;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import outerhaven.cip.common.GuardedBy;
import outerhaven.cip.common.PhantomCode;
import outerhaven.cip.common.ThreadSafe;

@PhantomCode
public class TimedRunExample {

}

/**
 * Listing 7.1. Using a Volatile Field to Hold Cancellation State.
 * @author threepwood
 *
 */
@ThreadSafe
class PrimeGenerator implements Runnable {
	@GuardedBy("this")
	private final List<BigInteger> primes = new ArrayList<BigInteger>();
	private volatile boolean cancelled;

	public void run() {
		BigInteger p = BigInteger.ONE;
		while (!cancelled) {
			p = p.nextProbablePrime();
			synchronized (this) {
				primes.add(p);
			}
		}
	}

	public void cancel() {
		cancelled = true;
	}

	public synchronized List<BigInteger> get() {
		return new ArrayList<BigInteger>(primes);
	}
}

/**
 * Listing 7.2. Generating a Second's Worth of Prime Numbers.<br><br>
 * 
 * The aSecondOfPrimes method in Listing 7.2 starts a PrimeGenerator and interrupts it after a second. 
 * While the PrimeGenerator might take somewhat longer than a second to stop, it will eventually notice 
 * the interrupt and stop, allowing the thread to terminate. But another aspect of executing a task is that 
 * you want to find out if the task throws an exception. If PrimeGenerator throws an unchecked exception 
 * before the timeout expires, it will probably go unnoticed, since the prime generator runs in a separate thread 
 * that does not explicitly handle exceptions 
 * 
 * @author threepwood
 *
 */
class SecondOfPrimes {
	List<BigInteger> aSecondOfPrimes() throws InterruptedException {
		PrimeGenerator generator = new PrimeGenerator();
		new Thread(generator).start();
		try {
			//SECONDS.sleep(1);
		} finally {
			generator.cancel();
		}
		return generator.get();
	}
}

/**
 * Listing 7.8. Scheduling an Interrupt on a Borrowed Thread. Don't Do this<br><br>
 * 
 * This is an appealingly simple approach, but it violates the rules: you should know a thread's interruption policy 
 * before interrupting it. Since timedRun can be called from an arbitrary thread, it cannot know the calling thread's 
 * interruption policy. If the task completes before the timeout, the cancellation task that interrupts the thread 
 * in which timedRun was called could go off after timedRun has returned to its caller. We don't know what code will 
 * be running when that happens, but the result won't be good. (It is possible but surprisingly tricky to eliminate 
 * this risk by using the ScheduledFuture returned by schedule to cancel the cancellation task.) <br><br>
 * 
 * Further, if the task is not responsive to interruption, timedRun will not return until the task finishes, 
 * which may be long after the desired timeout (or even not at all). A timed run service that doesn't return 
 * after the specified time is likely to be irritating to its callers.
 * 
 * @author threepwood
 *
 */
class TimedRun1 {
	private static final ScheduledExecutorService cancelExec = null;// ...;

	public static void timedRun(Runnable r, long timeout, TimeUnit unit) {
		final Thread taskThread = Thread.currentThread();
		cancelExec.schedule(new Runnable() {
			public void run() {
				taskThread.interrupt();
			}
		}, timeout, unit);
		r.run();
	}
}

/**
 * Listing 7.9. Interrupting a Task in a Dedicated Thread.<br><br>
 * 
 * Listing 7.9 addresses the exception-handling problem of aSecondOfPrimes and the problems with the previous attempt. 
 * The thread created to run the task can have its own execution policy, and even if the task doesn't respond to 
 * the interrupt, the timed run method can still return to its caller. After starting the task thread, timedRun 
 * executes a timed join with the newly created thread. After join returns, it checks if an exception was thrown from 
 * the task and if so, rethrows it in the thread calling timedRun. The saved Throwable is shared between the two 
 * threads, and so is declared volatile to safely publish it from the task thread to the timedRun thread.
 * 
 * @author threepwood
 *
 */
class TimedRun2 {
	private static final ScheduledExecutorService cancelExec = null; //...;

	public static void timedRun(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
		class RethrowableTask implements Runnable {
			private volatile Throwable t;

			public void run() {
				try {
					r.run();
				} catch (Throwable t) {
					this.t = t;
				}
			}

			void rethrow() {
				if (t != null)
					throw launderThrowable(t);
			}
			@PhantomCode
			RuntimeException launderThrowable(Throwable t){
				return null;
			}
		}
		RethrowableTask task = new RethrowableTask();
		final Thread taskThread = new Thread(task);
		taskThread.start();
		cancelExec.schedule(new Runnable() {
			public void run() {
				taskThread.interrupt();
			}
		}, timeout, unit);
		taskThread.join(unit.toMillis(timeout));
		task.rethrow();
	}
}
/**
 * Listing 7.10. Cancelling a Task Using Future<br><br>
 * 
 * Listing 7.10 shows a version of timedRun that submits the task to an ExecutorService and retrieves the result 
 * with a timed Future.get.If get terminates with a TimeoutException, the task is cancelled via its Future. 
 * (To simplify coding, this version calls Future.cancel unconditionally in a finally block, taking advantage of the fact that cancelling a completed task has no effect.) If the underlying
computation throws an exception prior to cancellation, it is rethrown from timedRun, which is the most convenient way for the caller to
deal with the exception. Listing 7.10 also illustrates another good practice: cancelling tasks whose result is no longer needed.
 * 
 * @author threepwood
 *
 */
class TimedRun3 {
	public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
		Future<?> task = taskExec.submit(r);
		try {
			task.get(timeout, unit);
		} catch (TimeoutException e) {
			// task will be cancelled below
		} catch (ExecutionException e) {
			// exception thrown in task; rethrow
			throw launderThrowable(e.getCause());
		} finally {
			// Harmless if task already completed
			task.cancel(true); // interrupt if running
		}
	}
	@PhantomCode
	static RuntimeException launderThrowable(Throwable t){
		return null;
	}
	@PhantomCode
	private static final ExecutorService taskExec = null; //...;
}
