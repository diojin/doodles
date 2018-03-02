package outerhaven.cip.listing.seven;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import outerhaven.cip.common.GuardedBy;
import outerhaven.cip.common.PhantomCode;

/**
 * 
 * Listing 7.15. Adding Reliable Cancellation to LogWriter.<br><br>
 * 
 * The way to provide reliable shutdown for LogWriter is to fix the race condition, which means making the submission 
 * of a new log message atomic. But we don't want to hold a lock while trying to enqueue the message, since put 
 * could block. Instead, we can atomically check for shutdown and conditionally increment a counter to "reserve" 
 * the right to submit a message, as shown in LogService in Listing 7.15.
 * 
 * @author threepwood
 *
 */
class LogService {
	private final BlockingQueue<String> queue;
	private final LoggerThread loggerThread;
	private final PrintWriter writer;
	@GuardedBy("this")
	private boolean isShutdown;
	@GuardedBy("this")
	private int reservations;

	public void start() {
		loggerThread.start();
	}

	public void stop() {
		synchronized (this) {
			isShutdown = true;
		}
		loggerThread.interrupt();
	}

	public void log(String msg) throws InterruptedException {
		synchronized (this) {
			if (isShutdown)
				throw new IllegalStateException();//...);
			++reservations;
		}
		queue.put(msg);
	}

	private class LoggerThread extends Thread {
		public void run() {
			try {
				while (true) {
					try {
						synchronized (this) {
							if (isShutdown && reservations == 0)
								break;
						}
						String msg = queue.take();
						synchronized (this) {
							--reservations;
						}
						writer.println(msg);
					} catch (InterruptedException e) { /* retry */
					}
				}
			} finally {
				writer.close();
			}
		}
	}
	
	@PhantomCode
	public LogService(){
		queue = null;
		loggerThread = null;
		writer = null;
	}
}

/**
 * Listing 7.16. Logging Service that Uses an ExecutorService.
 * 
 * @author threepwood
 *
 */
class LogService1 {
	private final ExecutorService exec = newSingleThreadExecutor();

	// ...
	public void start() {
	}

	public void stop() throws InterruptedException {
		try {
			exec.shutdown();
			exec.awaitTermination(TIMEOUT, UNIT);
		} finally {
			writer.close();
		}
	}

	public void log(String msg) {
		try {
			exec.execute(new WriteTask(msg));
		} catch (RejectedExecutionException ignored) {
		}
	}
	
	@PhantomCode
	private ExecutorService newSingleThreadExecutor(){
		return null;
	}
	
	@PhantomCode
	private final PrintWriter writer = null;
	
	@PhantomCode
	private class WriteTask implements Runnable{

		public WriteTask(String msg){
			
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
	@PhantomCode
	private final long TIMEOUT = 0;
	@PhantomCode
	private final TimeUnit UNIT = null;
}