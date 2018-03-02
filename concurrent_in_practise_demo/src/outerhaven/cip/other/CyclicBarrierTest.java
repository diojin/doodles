package outerhaven.cip.other;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
	
	private static final int THREAD_NUM=3;

	public static void main(String[] args) {
		CyclicBarrier cb = new CyclicBarrier(THREAD_NUM, new Runnable(){

			@Override
			public void run() {
				System.out.println("all threads arrived, clean up now");
			}
			
		});
		
		for (int i=0; i< THREAD_NUM * 2 ; ++i){
			new Thread(new Runnable(){
				
				@Override
				public void run() {
					try{
						System.out.println(String.format("[%s]-[%s]:\t%s", Thread.currentThread().getId(),
								Thread.currentThread().getName(), "doing stuff"));
						cb.await();
						System.out.println(String.format("[%s]-[%s]:\t%s", Thread.currentThread().getId(),
								Thread.currentThread().getName(), "reluctant to say byebye"));
					}catch ( InterruptedException e){
						
					}catch ( BrokenBarrierException e){
						
					}
				}
				
			}).start();
		}
	}


}
