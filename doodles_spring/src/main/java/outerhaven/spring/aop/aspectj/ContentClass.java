package outerhaven.spring.aop.aspectj;

public class ContentClass {
	public static void loggerDetail(SimpleBean simpleBean) {
		System.out.println("Student Logger Detail");
		System.out.println("**************************************************************");
		System.out.println("Name: " + simpleBean.getName());
		System.out.println("Age: " + simpleBean.getAge());
		System.out.println("Name (Age): " + simpleBean.setNameAndAge());
		System.out.println("**************************************************************");
	}
}