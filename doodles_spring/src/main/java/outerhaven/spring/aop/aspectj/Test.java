package outerhaven.spring.aop.aspectj;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"aop/aspectj/aspectj_config1.xml");
		SimpleBean simpleBean = (SimpleBean) applicationContext
				.getBean("simpleBean");
		ContentClass contentClass = new ContentClass();
		contentClass.loggerDetail(simpleBean);
		applicationContext.close();
	}
}