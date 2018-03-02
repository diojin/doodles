package outerhaven.cip.listing.seven;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import outerhaven.cip.common.GuardedBy;
import outerhaven.cip.common.PhantomCode;
import outerhaven.cip.common.ThreadSafe;

/**
 * Listing 7.12. Encapsulating Nonstandard Cancellation in a Task with Newtaskfor.<br><br>
 * 
 * SocketUsingTask implements CancellableTask and defines Future.cancel to close the socket as well as call 
 * super.cancel. If a SocketUsingTask is cancelled through its Future, the socket is closed and the executing thread 
 * is interrupted. This increases the task's responsiveness to cancellation: not only can it safely call interruptible 
 * blocking methods while remaining responsive to cancellation, but it can also call blocking socket I/O methods.
 *  
 * @author threepwood
 *
 * @param <T>
 */
interface CancellableTask<T> extends Callable<T> {
	void cancel();

	RunnableFuture<T> newTask();
}

@ThreadSafe
public class CancellingExecutor extends ThreadPoolExecutor {
	// ...
	protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
		if (callable instanceof CancellableTask)
			return ((CancellableTask<T>) callable).newTask();
		else
			return super.newTaskFor(callable);
	}
	@PhantomCode
	public CancellingExecutor(){
		super(0, 0, 0, TimeUnit.HOURS, null);
	}
}

abstract class SocketUsingTask<T> implements CancellableTask<T> {
	@GuardedBy("this")
	private Socket socket;

	protected synchronized void setSocket(Socket s) {
		socket = s;
	}

	public synchronized void cancel() {
		try {
			if (socket != null)
				socket.close();
		} catch (IOException ignored) {
		}
	}

	public RunnableFuture<T> newTask() {
		return new FutureTask<T>(this) {
			public boolean cancel(boolean mayInterruptIfRunning) {
				try {
					SocketUsingTask.this.cancel();
				} finally {
					return super.cancel(mayInterruptIfRunning);
				}
			}
		};
	}
}