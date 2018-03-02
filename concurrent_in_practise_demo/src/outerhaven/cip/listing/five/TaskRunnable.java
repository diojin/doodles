package outerhaven.cip.listing.five;

import java.util.concurrent.BlockingQueue;

import outerhaven.cip.common.PhantomCode;

/**
 * Listing 5.10. Restoring the Interrupted Status so as Not to Swallow the Interrupt.<br><br>
 *   
 *  Sometimes you cannot throw InterruptedException, for instance when your code is part of a Runnable. 
 *  In these situations, you must catch InterruptedException and restore the interrupted status by 
 *  calling interrupt on the current thread, so that code higher up the call stack can see 
 *  that an interrupt was issued
 *  
 * @author threepwood
 *
 */
public class TaskRunnable implements Runnable {
    BlockingQueue<Task> queue;

    public void run() {
        try {
            processTask(queue.take());
        } catch (InterruptedException e) {
            // restore interrupted status
            Thread.currentThread().interrupt();
        }
    }
    
    @PhantomCode
    private void processTask(Task task){
    	
    }
    @PhantomCode
    private class Task{
    	
    }
}
/**
 * a class trying to simulate the caller, once Thread.currentThread().interrupt(); is called in called class, 
 * caller can't catch InterruptedException, there would be compilation error if the codes are uncommented out. <br><br>
 * 
 * @author threepwood
 *
 */
class Caller{
	public static void main(String[] args){
//		try{
			new Thread(new TaskRunnable()).start();
//		}catch(InterruptedException e){
//			e.printStackTrace();
//		}
	}
}