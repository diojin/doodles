package outerhaven.gemfire;

import java.io.Serializable;

import com.gemstone.gemfire.cache.client.Pool;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;

public class GatewayHubControl {
	private Pool pool;
	private GatewayHubFunction function = new GatewayHubFunction(); 
	
	public void setPool(Pool pool) {
		this.pool = pool;
	}	
	
	public int showGatewayQueueSize(){
		FunctionService.registerFunction(function);
		Execution execute = FunctionService.onServer(pool);
		ResultCollector rc = execute.execute(function);
		Serializable result = rc.getResult();
		System.out.println(result.getClass());
		return 0;
	}	
}
