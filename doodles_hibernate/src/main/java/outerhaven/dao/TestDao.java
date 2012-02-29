package outerhaven.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import outerhaven.dao.vo.Department;
import outerhaven.dao.vo.Employee;
import outerhaven.dao.vo.VEmpDept;

public class TestDao {
	private static Log logger = LogFactory.getLog(TestDao.class);
	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;
	
	public static void main(String[] args) {
		logger.info(test4());
		
//		Department de = test8();
//		logger.debug("size:\t"+ de.getEmployees()==null);
	}
	public static Department test8(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query query = session.createQuery("from Department");
		Department dep = null;
		for( Iterator<Department> it = query.iterate(); it.hasNext();  ){
			dep=it.next();
			logger.debug(dep);
		}		
		session.close();
		return dep;
	}
	public static Department test7(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query query = session.createQuery("select de from Department de inner join de.employees em where em.empname = 'dio'");
		Department dep = null;
		for( Iterator<Department> it = query.iterate(); it.hasNext();  ){
			dep=it.next();
			logger.debug(dep);
		}
		session.close();
		return dep;
	}
	public static void test6(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query query =session.createQuery("from VEmpDept");
		List<VEmpDept> rs = query.list();
		for (Iterator<VEmpDept> it = rs.iterator(); it.hasNext();){
			logger.debug(it.next());
		}
	}
	// compare list() and iterator()
	public static void test5(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query query =session.createQuery("from Employee");
		for (Iterator<Employee> it = query.iterate(); it.hasNext();){
			logger.debug(it.next());
		}		
	}
	// compare list() and iterator()
	public static String test4(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query query =session.createQuery("from Employee");
		List<Employee> rs = query.list();
		StringBuffer buf = new StringBuffer();
		for (Iterator<Employee> it = rs.iterator(); it.hasNext();){
			buf.append(it.next());			
		}		
		return buf.toString();
	}
	public static void test3(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Criteria cri = session.createCriteria(Employee.class);
		List<Employee> rs = cri.list();
		for ( Iterator<Employee> it = rs.iterator();it.hasNext(); ){
			logger.debug(it.next());			
		}				
	}
	public static void test2(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query query = session.createQuery("select em.id, em.empname, em.context, em.department.deptname from Employee em inner join em.department order by em.id desc");
		for( Iterator it = query.iterate(); it.hasNext();  ){
			Object[] rs = (Object[])it.next();
			logger.debug(rs[1]);
		}
	}
	// only work with lazy="false"
	public static void test1(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		List<Employee> rs = dao.getHibernateTemplate().find("from Employee");
		for ( Iterator<Employee> it = rs.iterator();it.hasNext(); ){
			logger.debug(it.next());			
		}				
//		List<Department> rs1=dao.getHibernateTemplate().find("from Department");
//		for ( Iterator<Department> it =rs1.iterator(); it.hasNext();){
//			logger.debug(it.next());
//		}	
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
