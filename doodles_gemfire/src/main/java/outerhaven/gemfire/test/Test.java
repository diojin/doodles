package outerhaven.gemfire.test;

import outerhaven.gemfire.GatewayHubControl;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.PoolManager;

public class Test {
	public static void main(String[] args){
		ClientCache cache = new ClientCacheFactory()
			.set("name", "clientworker")
			.set("cache-xml-file", "gemfire/client_base.xml")
			.create();
		
		Region<String, String> exampleRegion = cache.getRegion("exampleRegion");
		exampleRegion.put("key0", "test0");
		exampleRegion.put("key1", "test1");
		GatewayHubControl ctl = new GatewayHubControl();
		ctl.setPool(PoolManager.find("base_pool"));
		ctl.showGatewayQueueSize();		
	}
}
