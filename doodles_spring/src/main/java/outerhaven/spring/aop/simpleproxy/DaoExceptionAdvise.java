package outerhaven.spring.aop.simpleproxy;

import org.aspectj.lang.JoinPoint;

public class DaoExceptionAdvise {
	
	public void onExecute(JoinPoint jp, Throwable t1) throws MyException{
		System.out.println("entering DaoExceptionAdvise");
		System.out.println(String.format("method {%s}, original exception{%s}\nmessage{%s}", 
				jp.getSignature().getName(),
				t1.getClass(),
				t1.getMessage()
				));
		throw new MyException("haha");
	}
}
