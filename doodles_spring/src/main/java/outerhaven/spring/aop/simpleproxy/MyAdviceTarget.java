package outerhaven.spring.aop.simpleproxy;


public class MyAdviceTarget implements IMyAdvice {

	@Override
	public String test(int i, String s, float f) throws MyException {
        System.out.println("Enter MyAdviceTarget.test()");  
        System.out.println("target: " + this.getClass());  
        StringBuffer buf = new StringBuffer();  
        buf.append( "i = " + i);  
        buf.append( ", s = \"" + s + "\"");  
        buf.append( ", f = " + f);  
        throw new NullPointerException("wawaaw");  
	}
}
