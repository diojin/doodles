package outerhaven.gemfire;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.util.Gateway;
import com.gemstone.gemfire.cache.util.GatewayHub;

public class GatewayHubFunction extends FunctionAdapter {
	private static final int START=0;
	private static final int STOP=1;
	@Override
	public void execute(FunctionContext ctx) {
		unsafeExec(ctx);
	}	
	
	@Override
	public String getId() {
		return String.valueOf(CacheFactory.getAnyInstance().getDistributedSystem().getId());
	}
	
	private void unsafeExec( FunctionContext ctx ){
		Cache cache = CacheFactory.getAnyInstance();
		GatewayHub hub = cache.getGatewayHubs().get(0);
		Gateway gateway = hub.getGateways().get(0);
		LogWriter log = cache.getLogger();
		log.info(String.format("Current size of gateway hub is %d", gateway.getQueueSize()));
		ctx.getResultSender().sendResult(Integer.valueOf(gateway.getQueueSize()));
		ctx.getResultSender().lastResult(Boolean.TRUE);
	}
}
