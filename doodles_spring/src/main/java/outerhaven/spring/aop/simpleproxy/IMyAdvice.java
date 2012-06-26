package outerhaven.spring.aop.simpleproxy;

public interface IMyAdvice {
	String test(int i, String s, float f) throws MyException;
}
