package outerhaven.spring.aop.aspectj.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class BusinessProfiler {
		private final String pointcut = "execution(* outerhaven.spring.aop.aspectj.annotation.*.*(..))";
        @Around(pointcut)
        public Object profile(ProceedingJoinPoint pjp) throws Throwable {
                long start = System.currentTimeMillis();
                System.out.println("Going to call the method in around advise.");
                Object output = pjp.proceed();
                System.out.println("Method execution completed.");
                long elapsedTime = System.currentTimeMillis() - start;
                System.out.println("Method execution time: " + elapsedTime + " milliseconds.");
                return output;
        }
        @AfterReturning(pointcut=pointcut, returning="result")
        public void logAfterReturning( JoinPoint jp, Object result ){
        	System.out.println("logAfterReturning() is running!");
        	System.out.println("hijacked : " + jp.getSignature().getName());
        	System.out.println("Method returned value is : " + result==null?"null":result);
        	System.out.println("******");
        }
        @Before(pointcut + " && @annotation(auditable)")
        public void audit(Auditable auditable){
        	System.out.println("i am audited with "+ auditable.value());
        }
        
/**
 *  following code provides an alternative way, not very elegant        
 */
//        @Pointcut("execution(* outerhaven.spring.aop.aspectj.annotation.*.*(..))")
//        public void businessMethods() {
//        	System.out.println("what is the purpose of it");
//        }
//        @Around("businessMethods()")
//        public Object profile(ProceedingJoinPoint pjp) throws Throwable {
//                long start = System.currentTimeMillis();
//                System.out.println("Going to call the method in around advise.");
//                Object output = pjp.proceed();
//                System.out.println("Method execution completed.");
//                long elapsedTime = System.currentTimeMillis() - start;
//                System.out.println("Method execution time: " + elapsedTime + " milliseconds.");
//                return output;
//        }
/**
 * following are some sample codes        
 */
        @Pointcut("execution(public * *(..))")
        private void anyPublicOperation() {}
        
        @Pointcut("within(outerhaven.spring.aop.aspectj..*)")
        private void inTrading() {}
        
        @Pointcut("anyPublicOperation() && inTrading()")
        private void tradingOperation() {}       
        
}