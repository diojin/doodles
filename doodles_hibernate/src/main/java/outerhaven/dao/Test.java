package outerhaven.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	private static Log log = LogFactory.getLog(Test.class);
	private static DateFormat df_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	private static DateFormat ts_yyyyMMdd_hhmmss = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("common-context.xml");
//		TestDao dao = (TestDao)ctx.getBean("testDao");
//		System.out.println(dao.test4());
		
		log.info(new SimpleDateFormat("yyyyMMdd").format(test(df_yyyyMMdd)));
	}
	public static Date test(DateFormat formater){
		try{
			Pattern pat = Pattern.compile("\\d{8} (\\d{2}:){2}\\d{2}.*");
			Matcher matcher=pat.matcher("20120323 12:12:12");
			log.info(matcher.matches());
			
			return formater.parse("20120323 12:12:12");
		}catch (Exception e){
			log.error(e);
			return null;
		}
		
	}

}
