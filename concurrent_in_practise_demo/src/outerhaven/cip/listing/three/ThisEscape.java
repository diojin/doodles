package outerhaven.cip.listing.three;

import java.awt.Event;
import java.util.EventListener;

import outerhaven.cip.common.NotThreadSafe;
import outerhaven.cip.common.PhantomCode;
/**
 * Listing 3.7. Implicitly Allowing the this Reference to Escape. Don't Do this.
 * @author threepwood
 *
 */
@NotThreadSafe
public class ThisEscape {
	public ThisEscape(EventSource source) {
		source.registerListener(new EventListener() {
			public void onEvent(Event e) {
				doSomething(e);
			}
		});
	}
	
	@PhantomCode
	private void doSomething(Event e){
		
	}
	@PhantomCode
	private class EventSource{
		void registerListener(EventListener el){}
	}
}
