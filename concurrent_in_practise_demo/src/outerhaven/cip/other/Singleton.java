package outerhaven.cip.other;

import outerhaven.cip.common.NotThreadSafe;
import outerhaven.cip.common.ThreadSafe;

public class Singleton {
}


/**
 * 1, 懒汉写法, lazy initialization
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
 * 2, 饿汉写法
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
 * 3, 静态内部类，优点：加载时不会初始化静态变量INSTANCE，因为没有主动使用，达到Lazy loading
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
 * 4, 枚举. 优点：不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象
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
 * 1,懒汉写法, lazy initialization
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
 * 1, 懒汉写法, lazy initialization
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


