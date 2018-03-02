package outerhaven.cip.listing.seven;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import outerhaven.cip.common.PhantomCode;

/**
 * Listing 7.13. Producer-Consumer Logging Service with No Shutdown Support.<br><br>
 * 
 * For a service like LogWriter to be useful in production, we need a way to terminate the logger thread so 
 * it does not prevent the JVM from shutting down normally. Stopping the logger thread is easy enough, 
 * since it repeatedly calls take, which is responsive to interruption; if the logger thread is modified to exit 
 * on catching InterruptedException, then interrupting the logger thread stops the service.<br><br>
 * 
 * However, simply making the logger thread exit is not a very satifying shutdown mechanism. Such an abrupt shutdown 
 * discards log messages that might be waiting to be written to the log, but, more importantly, threads blocked 
 * in log because the queue is full will never become unblocked. Cancelling a producerconsumer activity requires 
 * cancelling both the producers and the consumers. Interrupting the logger thread deals with the consumer, 
 * but because the producers in this case are not dedicated threads, cancelling them is harder.
 * 
 * @author threepwood
 *
 */

class LogWriter {
	private final BlockingQueue<String> queue;
	private final LoggerThread logger;
	@PhantomCode
	private final int CAPACITY = 42;
	@PhantomCode
	private volatile boolean shutdownRequested;

	public LogWriter(Writer writer) {
		this.queue = new LinkedBlockingQueue<String>(CAPACITY);
		this.logger = new LoggerThread(writer);
	}

	public void start() {
		logger.start();
	}

	public void log(String msg) throws InterruptedException {
		queue.put(msg);
	}

	private class LoggerThread extends Thread {
		private final PrintWriter writer;

		// ...
		public void run() {
			try {
				while (true)
					writer.println(queue.take());
			} catch (InterruptedException ignored) {
			} finally {
				writer.close();
			}
		}
		@PhantomCode
		public LoggerThread(Writer writer){
			this.writer = (PrintWriter)writer;
		}
	}
	
	/**
	 * Listing 7.14. Unreliable Way to Add Shutdown Support to the Logging Service.<br><br>
	 * 
	 * The implementation of log is a check-then-act sequence: producers could observe that the service has not 
	 * yet been shut down but still queue messages after the shutdown, again with the risk that the producer might 
	 * get blocked in log and never become unblocked.<br><br>
	 * 
	 * @param msg
	 * @throws InterruptedException
	 */
	public void log1(String msg) throws InterruptedException {
		if (!shutdownRequested)
			queue.put(msg);
		else
			throw new IllegalStateException("logger is shut down");
	}
}
