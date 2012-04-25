package outerhaven.gemfire.test;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import outerhaven.gemfire.GatewayHubControl;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.PoolManager;

public class Test {
	public static void main(String[] args){
		registerInterestTest();
	}
	public static void registerInterestTest(){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("gemfire/sgf-client-base.xml");
		ctx.registerShutdownHook();
		try {
			new CountDownLatch(1).await();
		}
		catch (InterruptedException ex) {
			ex.printStackTrace();
		}		
	}
	public static void gatewayTest() {
		ClientCache cache = new ClientCacheFactory()
				.set("name", "clientworker")
				.set("cache-xml-file", "gemfire/client_base.xml").create();

		Region<String, String> exampleRegion = cache.getRegion("exampleRegion");
		exampleRegion.put("key0", "test0");
		exampleRegion.put("key1", "test1");
		GatewayHubControl ctl = new GatewayHubControl();
		ctl.setPool(PoolManager.find("base_pool"));
		ctl.showGatewayQueueSize();
	}
}
