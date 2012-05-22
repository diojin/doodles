package com.citi.cache.server;

import gfinet.gemfire.agent.bean.AdminAgent;
import gfinet.gemfire.agent.bean.RegionAgent;
import gfinet.mgmt.jmx.server.MBeanHandler;

import java.io.FileInputStream;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnectorServer;
import javax.naming.Context;

import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;



public class CacheServer {

	
	private String xmlFile = "gemfire/cache.xml";
	
	public static void main(String[] args) throws Exception {
		CacheServer cache=new CacheServer();
//		System.setProperty("gemfire.status-monitoring-enabled", "true");
//		System.setProperty("gemfire.status-monitoring-port", "37001");
//		System.setProperty("log4j.configuration", "log4j.properties");
//		System.setProperty("gemfire.Query.VERBOSE", "true");
//		System.setProperty("gemfire.Index.VERBOSE", "true");
//		System.setProperty("gemfire.Query.INDEX_THRESHOLD_SIZE", "1000");
		cache.initCache();
		cache.initJMX();
		
		
	}

	void initCache() throws Exception {
		FileInputStream input = new FileInputStream(
				"target/classes/gemfire/cache.properties");
		Properties props = new Properties();
		props.load(input);
		props.setProperty("name", "CacheRunner");
		if (this.xmlFile != null) {
			props.setProperty("cache-xml-file", this.xmlFile.toString());
		}
			new CacheFactory(props).create();
		
		
			
	}	

	private  void initJMX() throws Exception{
//		JMXServerFactory.setPlatformServerDomain("TEST");
//		JMXServerFactory.setPlatformServerPort(1099);
//		JMXServer jmxserver=JMXServerFactory.getPlatformJMXServer();
		
//		LocateRegistry.createRegistry(1099);
		Map env = new HashMap<String, Object>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.rmi.registry.RegistryContextFactory");
		env.put(RMIConnectorServer.JNDI_REBIND_ATTRIBUTE, "true");
		JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:1099/server");
		ObjectName objectName=ObjectName.getInstance("test:name=test");
		ManagementFactory.getPlatformMBeanServer().registerMBean(MBeanHandler.createJMXMBean(new AdminAgent(),objectName), objectName);
		
		Region  region=CacheFactory.getAnyInstance().getRegion("/CHECK");	
		ObjectName regionObjectName=ObjectName.getInstance("region:name=region");
		ManagementFactory.getPlatformMBeanServer().registerMBean(MBeanHandler.createJMXMBean(new RegionAgent(region),regionObjectName), regionObjectName);
		JMXConnectorServer connectorServer = JMXConnectorServerFactory
		.newJMXConnectorServer(url, env, ManagementFactory.getPlatformMBeanServer());
        connectorServer.start();
		
//		MBeanHandler.createJMXMBean(new AdminAgent(), ObjectNameFactory.resolveFromClass(AdminAgent.class));
//		jmxserver.registerMBean(new AdminAgent(), ObjectNameFactory.resolveFromClass(AdminAgent.class));
		System.out.println("JMX connection is established on: service:jmx:rmi:///jndi/rmi://127.0.0.1:1099/server");
		
	}
	
}
