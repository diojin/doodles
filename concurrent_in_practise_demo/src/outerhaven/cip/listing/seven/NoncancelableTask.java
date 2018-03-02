package outerhaven.cip.listing.seven;

import java.util.concurrent.BlockingQueue;

import outerhaven.cip.common.PhantomCode;

/**
 * Listing 7.7. Noncancelable Task that Restores Interruption Before Exit.
 * 
 * Activities that do not support cancellation but still call interruptible blocking methods will have to 
 * call them in a loop, retrying when interruption is detected. In this case, they should save the 
 * interruption status locally and restore it just before returning, as shown in Listing 7.7, rather than 
 * immediately upon catching InterruptedException. Setting the interrupted status too early could result in 
 * an infinite loop, because most interruptible blocking methods check the interrupted status on entry 
 * and throw InterruptedException immediately if it is set. (Interruptible methods usually poll for interruption 
 * before blocking or doing any significant work, so as to be as responsive to interruption as possible.)
 * @author threepwood
 *
 */
public class NoncancelableTask {
	public Task getNextTask(BlockingQueue<Task> queue) {
		boolean interrupted = false;
		try {
			while (true) {
				try {
					return queue.take();
				} catch (InterruptedException e) {
					interrupted = true;
					// fall through and retry
				}
			}
		} finally {
			if (interrupted)
				Thread.currentThread().interrupt();
		}
	}
	@PhantomCode
	public class Task{
		
	}
}
