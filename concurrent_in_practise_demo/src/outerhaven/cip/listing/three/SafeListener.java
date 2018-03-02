package outerhaven.cip.listing.three;

import java.awt.Event;
import java.util.EventListener;

import outerhaven.cip.common.PhantomCode;

/**
 * Listing 3.8. Using a Factory Method to Prevent the this Reference from Escaping During Construction.
 * @author threepwood
 *
 */
class SafeListener {
	private final EventListener listener;

	private SafeListener() {
		listener = new EventListener() {
			public void onEvent(Event e) {
				doSomething(e);
			}
		};
	}

	public static SafeListener newInstance(EventSource source) {
		SafeListener safe = new SafeListener();
		source.registerListener(safe.listener);
		return safe;
	}
	
	@PhantomCode
	private void doSomething(Event e){
		
	}
	@PhantomCode
	private class EventSource{
		void registerListener(EventListener el){}
	}
}