package outerhaven.spring.aop.simpleproxy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations={
	"/aop/aopConfig.xml"		
})
public class TestAspectAOP {
	@Autowired
	ApplicationContext context;
	@Autowired
	private IMyAdvice myAdviceTarget;
	@Before
	public void before(){
		// cast failed because all implementation classes are proxied
//		myAdviceTarget=(MyAdviceTarget)context.getBean("myAdviceTarget");
	}
	@Test(expected=MyException.class)
	public void test() throws MyException{
		myAdviceTarget.test(10, "hah", 10.4f);
		/**
		 * following code has class cast exception
		 */
//		((MyAdviceTarget)myAdviceTarget).test(10, "haha", 10.4f);
	}
}
