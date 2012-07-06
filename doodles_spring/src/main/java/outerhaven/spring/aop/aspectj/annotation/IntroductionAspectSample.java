package outerhaven.spring.aop.aspectj.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

@Aspect
public class IntroductionAspectSample {
	@DeclareParents(value="outerhaven.spring.aop.aspectj.annotation.*+", defaultImpl=DefaultUsageTracked.class)
	private static UsageTracked mixin;	
}
