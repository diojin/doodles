package outerhaven.spring.aop.aspectj.annotation;

public class BusinessImpl implements IBusiness {
	@Override
	@Auditable(value = "hahaha")
	public void doSomeOperation() {
        System.out.println("I do what I do best, i.e sleep.");
        try {
                Thread.sleep(2000);
        } catch (InterruptedException e) {
                System.out.println("How dare you to wake me up?");
        }
        System.out.println("Done with sleeping.");
	}
}
