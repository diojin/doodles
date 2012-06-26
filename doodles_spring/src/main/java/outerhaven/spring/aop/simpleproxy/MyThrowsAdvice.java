package outerhaven.spring.aop.simpleproxy;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.springframework.aop.ThrowsAdvice;

public class MyThrowsAdvice implements ThrowsAdvice {
	public void afterThrowing(Method method, Object[] args, Object target, Throwable throwable){
		System.out.println("Entering MyThrowAdvice.afterThrowing()");  
        System.out.println("method name: " + method.getName());  
        Type[] type = method.getGenericParameterTypes();  
        for(int i = 0; i < type.length; i++) {  
            System.out.println(type[i].toString() + ": " + args[i]);  
        }  
        System.out.println("target: " + target.getClass());  
        System.out.println("throw: " + throwable.getClass().getName());  
        throw new NumberFormatException();  
	}
}
