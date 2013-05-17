package outerhaven.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import outerhaven.dao.vo.Item;
import outerhaven.dao.vo.Order;
import outerhaven.dao.vo.PurchaseItems;
import outerhaven.dao.vo.VEmpDept;

public class TestDao {
	private static Log logger = LogFactory.getLog(TestDao.class);
	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;
	
	
	public static void main(String[] args) {
			
//		Department de = one2ManyHQL1();
//		logger.debug("size:\t"+ de.getEmployees()==null);
//		one2ManyHQL2();
//		one2ManyQueryIterate();
//		one2ManyQueryList();
//		one2ManyCriteriaList();
//		one2ManyHQL3ObjectList();
//		one2ManyHibernateTemplate();
//		m2mSaveTest();		
//		m2mLoadTest();
//		testOne2ManySave();
//		testOne2ManyDelete();		
//		testOne2ManyDelete2();
//		viewSupport();
		testHQL();
//		testHQL1();
	}
	
	public static void testHQL(){
		String queryString = null;
		List result = null;
		queryString = "from Employee";
		/*
		 * straightforward inner join
		 */
		queryString = "from Employee emp inner join emp.department dept";
		/*
		 * "with" part in hibernate will be converted into join on conditions,
		 * but rather where condition
		 */
		queryString = "from Employee as emp inner join emp.department as dept with dept.deptname > 'dept2'";
		/*
		 * cartesian product, for non related tables
		 * return type is List<Object[]>, 2 separate objects in Object[] arrays, 
		 * 1 Employee, 1 EntityChild2
		 */
		queryString = "from Employee emp, EntityChild2 ent";
		/*
		 * following 2 queries use same sql query, department is fetched whatever the lasy setting
		 * return type is diffent, 1st one is List<Object[]>, 2nd is List<Employee>
		 * it is not as what hibernate reference 3.5 told
		 * 
		 */
		queryString = "from Employee emp inner join emp.department dept";
		queryString = "from Employee emp inner join fetch emp.department dept";
				
		result = findByHQL(queryString);
	}
	
	/*
	 * Test HQL 1:
	 * from Employee emp inner join emp.department dept
	 */
	public static void testHQL1() {
		String queryString = "from Employee emp inner join emp.department dept";
		List<Object[]> results = findByHQL(queryString);
		for ( Object[] innerResult: results ){
			logger.info(innerResult[0]);
		}
	}
	
	public static List findByHQL(String queryString ) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao=(TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		List result = null;
		Query query = session.createQuery(queryString);
		result = query.list();
		return result;		
	}
	 
	public static void m2mLoadTest(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao=(TestDao)ctx.getBean("testDao");
		Session session= dao.getSessionFactory().openSession();
		Order order = (Order)session.load(Order.class, 27);
		logger.info(((PurchaseItems)order.getItems().iterator().next()).getItem().getDescription());
	}
	
	public static void m2mSaveTest(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao=(TestDao)ctx.getBean("testDao");
		Session session= dao.getSessionFactory().openSession();
		Order order = new Order();
		order.setCustomerName("dio");
		order.setAddress("nowhere");
		Item item = new Item();
		item.setName("gamepad");
		item.setDescription("for gaming");
		PurchaseItems pur = new PurchaseItems();
		pur.setPrice(new Double(22.2));
		pur.setPurchaseDate(new Date());
		pur.setQuantity(11);
		
		pur.setItem(item);
		pur.setOrder(order);
		
		Set<PurchaseItems> set = new HashSet<PurchaseItems>();
		set.add(pur);
		order.setItems(set);
		
		set = new HashSet<PurchaseItems>();
		set.add(pur);		
		item.setOrders(set);
		
		session.save(item);
		session.save(order);
		session.flush();
	}
	
	public static Department one2ManyHQL1(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query query = session.createQuery("from Department");	
		Department dep = null;
		for( Iterator<Department> it = query.iterate(); it.hasNext();  ){
			dep=it.next();
			logger.debug(dep.getDeptname());
		}		
		session.close();
		return dep;
	}
	public static Department one2ManyHQL2(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query query = session.createQuery("select de from Department de inner join de.employees em where em.empname = 'haijian1'");
		Department dep = null;
		for( Iterator<Department> it = query.iterate(); it.hasNext();  ){
			dep=it.next();
			logger.debug(dep.getDeptname());
		}
		session.close();
		return dep;
	}
	public static void viewSupport(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query query =session.createQuery("from VEmpDept");
		List<VEmpDept> rs = query.list();
		for (Iterator<VEmpDept> it = rs.iterator(); it.hasNext();){
			logger.debug(it.next());
		}
	}
	/*
	 * compare list() and iterator()
	 * iterator() issues N+1 calls, one for ids, the N for each row corresponding 
	 * to the id
	 */
	public static void one2ManyQueryIterate(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query query =session.createQuery("from Employee");
		for (Iterator<Employee> it = query.iterate(); it.hasNext();){
			logger.debug(it.next().getEmpname());
		}		
	}
	/*
	 * compare list() and iterator()
	 * list() doesn't have the N+1 problem
	 */
	public static String one2ManyQueryList(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query query =session.createQuery("from Employee");
		List<Employee> rs = query.list();
		StringBuffer buf = new StringBuffer();
		for (Iterator<Employee> it = rs.iterator(); it.hasNext();){
			buf.append(it.next().getEmpname());
		}		
		return buf.toString();
	}
	public static void one2ManyCriteriaList(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Criteria cri = session.createCriteria(Employee.class);
		List<Employee> rs = cri.list();
		for ( Iterator<Employee> it = rs.iterator();it.hasNext(); ){
			logger.debug(it.next().getEmpname());			
		}				
	}
	/*
	 * iterate doesn't have N+1 queries problem 
	 * if a lists of column are specified as select part 
	 */
	public static void one2ManyHQL3ObjectList(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query query = session.createQuery("select em.id, em.empname, em.context, em.department.deptname from Employee em inner join em.department order by em.id desc");
		for( Iterator it = query.iterate(); it.hasNext();  ){
			Object[] rs = (Object[])it.next();
			logger.debug(rs[1]);
		}
	}
	/*
	 * only work with lazy="false"
	 * session is closed after getHibernateTemplate().find() call
	 */
	public static void one2ManyHibernateTemplate(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		List<Employee> rs = dao.getHibernateTemplate().find("from Employee");
		for ( Iterator<Employee> it = rs.iterator();it.hasNext(); ){
			logger.debug(it.next());			
		}				
	}
	
	public static void testOne2ManySave(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Department dept = new Department();
		dept.setDeptname("joy");
		Employee emp = new Employee();
		emp.setDepartment(dept);
		emp.setEmpname("diojin");
		emp.setEmpage(0);
		emp.setContext("haha");
		dept.getEmployees().add(emp);
		session.save(dept);		
	}
	/*
	 * 5 sqls are generated in this case
	 */
	public static void testOne2ManyDelete(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Department dept = new Department();
		Department value =(Department)session.load(Department.class, 18);
		session.delete(value);
		session.flush();
		session.close();		
	}
	
	/*
	 * 5 sqls are generated in this case
	 */	
	public static void testOne2ManyDelete2(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		TestDao dao = (TestDao)ctx.getBean("testDao");
		Session session = dao.getSessionFactory().openSession();
		Query q = session.createQuery("from Department where id = :id ");
		q.setParameter("id", 17);
		Department dept = (Department)q.list().get(0);
		session.delete(dept);
		session.flush();
		session.close();
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
