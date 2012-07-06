package outerhaven.spring.aop.aspectj.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

        /**
         * @param args
         */
        public static void main(String[] args) {
                ApplicationContext context = new ClassPathXmlApplicationContext(
                                "aop/aspectj/aspectj_anno_config.xml");
                IBusiness bc = (IBusiness) context.getBean("myBusinessClass");
                bc.doSomeOperation();
                UsageTracked usage = (UsageTracked)context.getBean("myBusinessClass");
                usage.increaseUseCount();
                usage.increaseUseCount();
        }

}