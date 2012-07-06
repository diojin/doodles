package outerhaven.spring.aop.aspectj.annotation;

public class DefaultUsageTracked implements UsageTracked {
	private static int current=0;
	@Override
	public void increaseUseCount() {
		System.out.println("usage count:"+ (current++) );
	}
}
