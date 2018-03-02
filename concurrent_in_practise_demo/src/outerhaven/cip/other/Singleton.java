package outerhaven.cip.other;

import outerhaven.cip.common.NotThreadSafe;
import outerhaven.cip.common.ThreadSafe;

public class Singleton {
}


/**
 * 1, ����д��, lazy initialization
 * @author threepwood
 *
 */
@NotThreadSafe
class Singleton1d0 {
	private static Singleton1d0 instance = null;
	private Singleton1d0(){
		
	}
	public static Singleton1d0 getInstance(){
		if ( null == instance){
			instance = new Singleton1d0();
		}
		return instance;
	}

}
/**
 * 2, ����д��
 * @author threepwood
 *
 */
@ThreadSafe
class Singleton2 {
	private static Singleton2 instance = new Singleton2();
	private Singleton2(){
		
	}
	public static Singleton2 getInstance(){
		return instance;
	}

}
/**
 * 3, ��̬�ڲ��࣬�ŵ㣺����ʱ�����ʼ����̬����INSTANCE����Ϊû������ʹ�ã��ﵽLazy loading
 * @author threepwood
 *
 */
@ThreadSafe
class Singleton3 {
	private static class SingletonHolder{
		// PS: final should not be necessary, but looks better
		public static final Singleton3 INSTANCE = new Singleton3();
	}
	private Singleton3(){
		
	}
	public static Singleton3 getInstance(){
		return SingletonHolder.INSTANCE;
	}
}
/**
 * 4, ö��. �ŵ㣺�����ܱ�����߳�ͬ�����⣬���һ��ܷ�ֹ�����л����´����µĶ���
 * @author threepwood
 *
 */
@ThreadSafe
enum Singleton4{
	INSTANCE;
	public void doSometing(){
	}
}

/**
 * 1,����д��, lazy initialization
 * thread safe but performance is not optimized
 * @author threepwood
 *
 */
@ThreadSafe
class Singleton1d1 {
	private static Singleton1d1 instance = null;
	private Singleton1d1(){
		
	}
	public static synchronized Singleton1d1 getInstance(){
		if ( null == instance){
			instance = new Singleton1d1();
		}
		return instance;
	}

}

/**
 * 1, ����д��, lazy initialization
 * Double-Checked Locking
 * thread safe, performance good
 */
@NotThreadSafe
class Singleton1d2 {
	// volatile is not mandatory, it is thread safe even without it, 
	// however, the times to enter CS is reduced when using it
	private static volatile Singleton1d2 instance = null;
	private static final Object lock = new Object();
	private Singleton1d2(){
		
	}
	public static Singleton1d2 getInstance(){
		if ( null == instance ){
			synchronized (lock ){
				if ( null == instance ){
					instance = new Singleton1d2();
				}
			}
		}
		return instance;
	}

}


