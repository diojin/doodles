package outerhaven.spring.aop.simpleproxy;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test {
	public static void main(String[] args) throws MyException{
		testAOPAspect();
	}
	
	public static void testSimpleProxy() throws MyException{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop/aopConfig.xml");
		IMyAdvice target = (IMyAdvice)context.getBean("aop1");
		target.test(10, "hah", 10.3f);
		/**
		 *  codes below doesn't work.
		 *  proxied object has to be used.
		 */
//		MyAdviceTarget targetReal = (MyAdviceTarget)context.getBean("myAdviceTarget");
//		targetReal.test(10, "haha", 10.5f);
	}
	public static void testAOPAspect()throws MyException{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop/aopConfig.xml");
		IMyAdvice target = (IMyAdvice)context.getBean("myAdviceTarget");
		target.test(10, "hah", 10.1f);
	}
}
