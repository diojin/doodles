package outerhaven.cip.listing.five;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import outerhaven.cip.common.PhantomCode;

/**
 * Listing 5.15. Coordinating Computation in a Cellular Automaton with CyclicBarrier.
 * @author threepwood
 *
 */
public class CellularAutomata {
	private final Board mainBoard;
	private final CyclicBarrier barrier;
	private final Worker[] workers;

	public CellularAutomata(Board board) {
		this.mainBoard = board;
		int count = Runtime.getRuntime().availableProcessors();
		this.barrier = new CyclicBarrier(count, new Runnable() {
			public void run() {
				mainBoard.commitNewValues();
			}
		});
		this.workers = new Worker[count];
		for (int i = 0; i < count; i++)
			workers[i] = new Worker(mainBoard.getSubBoard(count, i));
	}

	private class Worker implements Runnable {
		private final Board board;

		public Worker(Board board) {
			this.board = board;
		}

		public void run() {
			while (!board.hasConverged()) {
				for (int x = 0; x < board.getMaxX(); x++)
					for (int y = 0; y < board.getMaxY(); y++)
						board.setNewValue(x, y, computeValue(x, y));
				try {
					barrier.await();
				} catch (InterruptedException ex) {
					return;
				} catch (BrokenBarrierException ex) {
					return;
				}
			}
		}
		
		@PhantomCode
		private int[] computeValue(int x, int y) {
			return null;
		}
	}

	public void start() {
		for (int i = 0; i < workers.length; i++)
			new Thread(workers[i]).start();
		mainBoard.waitForConvergence();
	}
}
@PhantomCode
class Board{
	public void commitNewValues(){
		
	}
	public boolean hasConverged(){
		return false;
	}
	public Board getSubBoard( int count, int i){
		return null;
	}
	public void waitForConvergence(){
		
	}
	public int getMaxX(){
		return 0;
	}
	public int getMaxY(){
		return 0;
	}
	public void setNewValue(int x, int y, int[] newVals){
		
	}
}