package outerhaven.gemfire.listener;

import java.util.List;
import java.util.Properties;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.util.GatewayEvent;
import com.gemstone.gemfire.cache.util.GatewayEventListener;

public class SimpleGatewayListener implements Declarable, GatewayEventListener {
	private LogWriter log;
	@Override
	public void close() {		
	}

	@Override
	public boolean processEvents(List<GatewayEvent> events) {
		for ( GatewayEvent event : events ){
			log.info(event.getDeserializedValue().toString());
		}
		return true;
	}

	@Override
	public void init(Properties props) {
		if ( null == log ){
			log = CacheFactory.getAnyInstance().getLogger();
		}
		String contextFile=props.getProperty("contextFile");		
		
	}
}
