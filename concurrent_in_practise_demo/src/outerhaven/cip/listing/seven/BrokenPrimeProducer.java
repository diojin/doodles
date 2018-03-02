package outerhaven.cip.listing.seven;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

import outerhaven.cip.common.PhantomCode;

/**
 * Listing 7.3. Unreliable Cancellation that can Leave Producers Stuck in a Blocking Operation. Don't Do this.<br><br>
 * 
 * The cancellation mechanism in PrimeGenerator will eventually cause the primeseeking task to exit, 
 * but it might take a while. If, however, a task that uses this approach calls a blocking method such as 
 * BlockingQueue.put, we could have a more serious problemthe task might never check the cancellation flag 
 * and therefore might never terminate.
 * 
 * @author threepwood
 *
 */
class BrokenPrimeProducer extends Thread {
	private final BlockingQueue<BigInteger> queue;
	private volatile boolean cancelled = false;

	BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			BigInteger p = BigInteger.ONE;
			while (!cancelled)
				queue.put(p = p.nextProbablePrime());
		} catch (InterruptedException consumed) {
		}
	}

	public void cancel() {
		cancelled = true;
	}

}

class ClientCode1 {
	void consumePrimes() throws InterruptedException {
		BlockingQueue<BigInteger> primes = null; // ...;
		BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
		producer.start();
		try {
			while (needMorePrimes())
				consume(primes.take());
		} finally {
			producer.cancel();
		}
	}
	
	@PhantomCode
	private boolean needMorePrimes(){
		return false;
	}
	@PhantomCode
	private void consume(BigInteger i){
		
	}
}
