package outerhaven.cip.listing.seven;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import outerhaven.cip.common.GuardedBy;
import outerhaven.cip.common.PhantomCode;

/**
 * Listing 7.21. ExecutorService that Keeps Track of Cancelled Tasks After Shutdown.<br><br>
 * 
 * TRackingExecutor has an unavoidable race condition that could make it yield false positives: 
 * tasks that are identified as cancelled but actually completed. This arises because the thread pool 
 * could be shut down between when the last instruction of the task executes and when the pool records the task 
 * as complete. This is not a problem if tasks are idempotent (if performing them twice has the same effect 
 * as performing them once), as they typically are in a web crawler. Otherwise, the application retrieving 
 * the cancelled tasks must be aware of this risk and be prepared to deal with false positives.
 * 
 * @author threepwood
 *
 */
class TrackingExecutor extends AbstractExecutorService {
	private final ExecutorService exec;
	private final Set<Runnable> tasksCancelledAtShutdown = Collections
			.synchronizedSet(new HashSet<Runnable>());

	// ...
	public List<Runnable> getCancelledTasks() {
		if (!exec.isTerminated())
			throw new IllegalStateException();//...);
		return new ArrayList<Runnable>(tasksCancelledAtShutdown);
	}

	public void execute(final Runnable runnable) {
		exec.execute(new Runnable() {
			public void run() {
				try {
					runnable.run();
				} finally {
					if (isShutdown() && Thread.currentThread().isInterrupted())
						tasksCancelledAtShutdown.add(runnable);
				}
			}
		});
	}
	// delegate other ExecutorService methods to exec
	@PhantomCode
	public TrackingExecutor(ExecutorService service){
		exec = service;
	}
	
	@PhantomCode
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	@PhantomCode
	@Override
	public List<Runnable> shutdownNow() {
		// TODO Auto-generated method stub
		return null;
	}

	@PhantomCode
	@Override
	public boolean isShutdown() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@PhantomCode
	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@PhantomCode
	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}
}

/**
 * Listing 7.22. Using TRackingExecutorService to Save Unfinished Tasks for Later Execution.<br><br>
 *
 * @author threepwood
 *
 */
abstract class WebCrawler {
	private volatile TrackingExecutor exec;
	@GuardedBy("this")
	private final Set<URL> urlsToCrawl = new HashSet<URL>();

	// ...
	public synchronized void start() {
		exec = new TrackingExecutor(Executors.newCachedThreadPool());
		for (URL url : urlsToCrawl)
			submitCrawlTask(url);
		urlsToCrawl.clear();
	}

	public synchronized void stop() throws InterruptedException {
		try {
			saveUncrawled(exec.shutdownNow());
			if (exec.awaitTermination(TIMEOUT, UNIT))
				saveUncrawled(exec.getCancelledTasks());
		} finally {
			exec = null;
		}
	}

	protected abstract List<URL> processPage(URL url);

	private void saveUncrawled(List<Runnable> uncrawled) {
		for (Runnable task : uncrawled)
			urlsToCrawl.add(((CrawlTask) task).getPage());
	}

	private void submitCrawlTask(URL u) {
		exec.execute(new CrawlTask(u));
	}

	private class CrawlTask implements Runnable {
		private final URL url;

		// ...
		public void run() {
			for (URL link : processPage(url)) {
				if (Thread.currentThread().isInterrupted())
					return;
				submitCrawlTask(link);
			}
		}

		public URL getPage() {
			return url;
		}
		
		@PhantomCode
		public CrawlTask(URL u){
			url = u;
		}
	}
	
	@PhantomCode
	private final long TIMEOUT = 0;
	@PhantomCode
	private final TimeUnit UNIT = null;
}
