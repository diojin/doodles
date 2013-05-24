package outerhaven.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import outerhaven.dao.vo.Cat;
import outerhaven.dao.vo.Department;
import outerhaven.dao.vo.DomesticCat;
import outerhaven.dao.vo.Employee;
import outerhaven.dao.vo.Item;
import outerhaven.dao.vo.Order;
import outerhaven.dao.vo.PurchaseItems;
import outerhaven.dao.vo.UnmappedCat;
import outerhaven.dao.vo.VEmpDept;

public class TestDao {
	private static Log logger = LogFactory.getLog(TestDao.class);
	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;
	private static ApplicationContext ctx = null;
	public static final String CAT_NAME="dio";
	
	public static void main(String[] args) throws Exception {
			
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
//		testHQL();
//		testHQL1();
//		createFilterTest();
//		testCriteria();
		testSqlQuery();
	}
	public static void testSqlQuery(){
		Session session = getTestDaoInstance().getSessionFactory().openSession();
		List result = null;
//		result = session.createSQLQuery("select * from cats").list();
//		result = session.createSQLQuery("select * from cats")
//					.addScalar("id", Hibernate.LONG)
//					.addScalar("sex", Hibernate.CHARACTER)
//					.addScalar("name", Hibernate.STRING)
//					.list();
//		/*
//		 * two strage points:
//		 * 1. the return entity class is a persistent class, accessing it collection kittens triggers 
//		 * one select query to get all the kittens 
//		 * 2. property name has to same to column name so as to be populated.
//		 * besides, property mother is not proxied or populated, which is the general case for
//		 * other fetching techniques 
//		 */
//		result = session.createSQLQuery("select * from cats").addEntity(Cat.class).list();
//		/*
//		 * 1. return type, List<Object[]>, 2 element for Object[], 2 Cat are the same one
//		 * 2. for the each 2 returned Cat, mother is proxied.
//		 * 3. supposed situation
//		 * supposed to work if different tables join each other
//		 * many-to-one relation "mother" should be populated, which in this special case not.
//		 */
//		result = session.createSQLQuery("select * from cats kid, cats mother where kid.mother_id = mother.id")
//					.addEntity("cat", Cat.class)
//					.addJoin("mother","cat.mother")
//					.list();
//		/*
//		 * this is a working example of query above
//		 * 1. return type, List<Object[]>, 2 element for Object[], 1 cat, 1 mother.
//		 * 2. for each element in Object[], property mother is populated.
//		 * 3. aliases in the sql and addEntity, addJoin method has to be matching
//		 */
//		result = session.createSQLQuery("select {kid.*}, {mother.*} from cats kid, cats mother where kid.mother_id = mother.id")
//					.addEntity("kid", Cat.class)
//					.addJoin("mother","kid.mother")
//					.list();
//		/*
//		 * failed with org.hibernate.MappingException: Unknown entity: outerhaven.dao.vo.UnmappedCat
//		 */
//		result = session.createSQLQuery("select {kid.*}, {mother.*} from cats kid, cats mother where kid.mother_id = mother.id")
//					.addEntity("kid", UnmappedCat.class)
//					.addJoin("mother","kid.mother")
//					.list();		
//		/*
//		 * 1. return type, List<Object[]>, 2 element as expected, one kid, one mother
//		 * 2. mother property is not proxied for either mother or kid
//		 */
//		result = session.createSQLQuery("select {c.*}, {m.*} from cats c, cats m where c.mother_id=m.id")
//					.addEntity("c",Cat.class)
//					.addEntity("m", Cat.class)
//					.list();
//		/*
//		 * 1. failed with unexpected exception, seems all the properties of entity class has to be selected
//		 * 
//		 * Reason: Native SQL queries which query for entities that are mapped as part of an inheritance must include
//			all properties for the baseclass and all its subclasses.
//		 */
//		result = session.createSQLQuery("select c.id as {c.id}, c.name as {c.name}, c.birthdate as {c.birthdate}, " +
//											"c.mother_id as {c.mother}, c.subclass as {c.class}, {m.*} " +
//											"from cats c, cats m where c.id=m.id")
//						.addEntity("c", Cat.class)
//						.addEntity("m", Cat.class)
//						.list();
//		result = session.getNamedQuery("sqlQuery1").setParameter("namePattern", "gen%").list();
//		result = session.getNamedQuery("sqlQuery1").setString("namePattern", "gen%").list();
//		result = session.getNamedQuery("sqlQuerySimpleJoin").list();
//		/*
//		 * try unmapped entity
//		 * failed with Unknown entity: outerhaven.dao.vo.UnmappedCat
//		 */
//		result = session.getNamedQuery("sqlQuerySimpleUnmapped").setString("namePattern", "%dio").list();		
//		/*
//		 *	sql query insert, working
//		 */
//		SQLQuery queryInsert = session.createSQLQuery("insert into cats values ( 11, 'D', 12.44, sysdate(), 'test', 'F', null, 'test', 0 )");
//		queryInsert.executeUpdate();
//		/*
//		 * sql query update, update primary key, working
//		 */
//		SQLQuery queryUpdate = session.createSQLQuery("update cats set id=99 where id = 11");
//		queryUpdate.executeUpdate();		
//		/*
//		 * named query DML, works
//		 */
//		session.getNamedQuery("sqlQueryInsert").executeUpdate();
		/*
		 * named query, return scalar 
		 */
		result = session.getNamedQuery("sqlQueryScalar").list();
	}
	
	public static void testCriteria(){
		Session session = getTestDaoInstance().getSessionFactory().openSession();
		Criteria cri = null;
		List result = null;
		cri = session.createCriteria(Cat.class);
		cri.setMaxResults(2);
		result = cri.list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();
		
		cri = session.createCriteria(Cat.class)
				.add(Restrictions.ilike("name", "gen1%"));
		result = cri.list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();
		
		cri = session.createCriteria(Cat.class)
				.add(Restrictions.or(
						Restrictions.or(Restrictions.eq("name", "gen11-dio"),
										Restrictions.eq("name", "gen12")), 
						Restrictions.eq("name", "adam")));
		result = cri.list();
		
		/*
		 * 	same function as above by using disjunction
		 */		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();		
		cri = session.createCriteria(Cat.class)
				.add(Restrictions.disjunction()
						.add(Restrictions.eq("name", "gen11-dio"))
						.add(Restrictions.eq("name", "gen12"))
						.add(Restrictions.eq("name", "adam")));
		result = cri.list();		
		/*
		 * same function as above by using Property 
		 */	
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();

		Property name = Property.forName("name");
		cri = session.createCriteria(Cat.class)
				.add(Restrictions.disjunction()
						.add(name.eq("gen11-dio"))
						.add(name.eq("gen12"))
						.add(name.eq("adam")));
		result = cri.list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();		
		/*
		 * {alias} will be replaced by actual table alias of generated sql
		 * in this special case, it is not mandatory since only one table is selected, 
		 * no identical column name. 
		 */
		cri = session.createCriteria(Cat.class)
				.add(Restrictions.sqlRestriction("lower({alias}.name) = ?", "eva", Hibernate.STRING));
		result = cri.list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();
		/*
		 *  navigate association
		 *  Return type: List<Cat>
		 *  1. collection kittens is not populated, maybe because partial children are loaded by 
		 *  generated sql, which can't populate collection consistent with database   
		 *  that also indiates the sql fetches unused column, which lead to low performance
		 *  2. The second createCriteria() returns a new instance of Criteria that refers to the elements
				of the kittens collection, however, data of first criteria is actually returned
		 *  
		 *  
		 *      select
			        this_.id as id7_1_,
			        this_.weight as weight7_1_,
			        this_.birthdate as birthdate7_1_,
			        this_.color as color7_1_,
			        this_.sex as sex7_1_,
			        this_.litter_id as litter7_7_1_,
			        this_.mother_id as mother8_7_1_,
			        this_.name as name7_1_,
			        this_.subclass as subclass7_1_,
			        cat1_.id as id7_0_,
			        cat1_.weight as weight7_0_,
			        cat1_.birthdate as birthdate7_0_,
			        cat1_.color as color7_0_,
			        cat1_.sex as sex7_0_,
			        cat1_.litter_id as litter7_7_0_,
			        cat1_.mother_id as mother8_7_0_,
			        cat1_.name as name7_0_,
			        cat1_.subclass as subclass7_0_ 
			    from
			        cats this_ 
			    inner join
			        cats cat1_ 
			            on this_.id=cat1_.mother_id 
			    where
			        this_.name=? 
			        and cat1_.name like ?
		 *  
		 */
		cri = session.createCriteria(Cat.class)
				.add(Restrictions.eq("name", "eva"))
				.createCriteria("kittens")
					.add(Restrictions.like("name", "%dio"));
		result = cri.list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();

		/*
		 *  return type: List<Cat>
		 *  duplicated records returned due to the sql itself, property kittens is not populated
		 */		
		cri = session.createCriteria(Cat.class)
				.createAlias("kittens", "kid")
				.createAlias("mother", "mum")
				.add(Restrictions.eq("mum.name", "eva"));
		result = cri.list();
		
		/*
		 * return type: ArrayList<HashMap>
		 */
		cri = session.createCriteria(Cat.class)
				.createCriteria("kittens", "kid")
					.add(Restrictions.like("name", "gen%" ))
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		result = cri.list();
		Iterator iter = result.iterator();
		while ( iter.hasNext() ){
			Map map =(Map) iter.next();
			Cat cat = (Cat)map.get(Criteria.ROOT_ALIAS);
			Cat kid = (Cat)map.get("kid");
		}
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();
		
		cri = session.createCriteria( Cat.class )
				.createAlias("mother", "mt", Criteria.LEFT_JOIN, Restrictions.eq("mt.name",
				"eva") )
				.addOrder(org.hibernate.criterion.Order.asc("mt.weight"));
		result = cri.list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();
		/*
		 * 1. left outer join is used to eager fetch collections
		 * 2. 2 Cat returned from call below(because cat eva has two children), however, 2 Cat are the same, 
		 * 	  they are in the same memory address.
		 */
		cri = session.createCriteria(Cat.class)
				.add(Restrictions.eq("name", "eva"))
				.setFetchMode("mother", FetchMode.EAGER)
				.setFetchMode("kittens", FetchMode.EAGER);
		result = cri.list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();
		/*
		 * Example query
		 * Version properties, identifiers and associations are ignored. By default, null valued properties are
			excluded.
		 * 1. Wrapper types of primary types should be used, since primary types have default values,which can't 
		 * 		by default be exclued by Example
		 * 2. char type with default value can't be excluded, unless the property is explicitly excluded  			
		 */
		Cat catExample = new Cat();
		catExample.setColor("blue");
		catExample.setWeight(12.12f);
		Example example = Example.create(catExample)
							.excludeProperty("weight")
							.excludeZeroes()
							.enableLike();
		cri = session.createCriteria(Cat.class)
				.add(example);
		result = cri.list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();		
		Cat catExample1 = new Cat();
		catExample1.setColor("blue");
		catExample1.setWeight(12.12f);
		Cat mother = new Cat();
		mother.setColor("bluewhite");
		catExample.setMother(mother);
		cri = session.createCriteria(Cat.class)
				.add(Example.create(catExample))
				.createCriteria("mother")
				.add(Example.create(catExample.getMother()));
		result = cri.list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();	
		/*
		 * Projection
		 * it seems alias doesn't work
		 */		
		cri = session.createCriteria(Cat.class)
				.add(Restrictions.like("name", "gen11%"))
				.setProjection(Projections.projectionList()
						.add(Projections.rowCount())
						.add(Projections.avg("weight"), "avgWeight")
						.add(Projections.alias(Projections.sum("weight"), "sumWeight"))
						.add(Projections.max("weight").as("maxWeight"))
						.add(Projections.groupProperty("name")));
		result = cri.list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();	
		/*
		 * 	DetachedCriteria, criteria outside session
		 */
		DetachedCriteria dcri = DetachedCriteria.forClass(Cat.class)
									.add(Property.forName("name").like("gen%"));
		result = dcri.getExecutableCriteria(session).list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();
		/*
		 * DetachedCriteria as subselect
		 */
		DetachedCriteria dcri1 = DetachedCriteria.forClass(Cat.class)
									.setProjection(Property.forName("weight").avg());
		cri = session.createCriteria(Cat.class)
				.add(Property.forName("weight").gt(dcri1));
		result = cri.list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();
		/*
		 * correlated subselect
		 */
		DetachedCriteria dcri2 = DetachedCriteria.forClass(Cat.class, "cat1")
									.add(Property.forName("cat1.sex").eqProperty("cat.sex"))
									.setProjection(Property.forName("weight").avg());
		cri = session.createCriteria(Cat.class, "cat")
				.add(Property.forName("cat.weight").gt(dcri2));
		result = cri.list();	
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();		
		DetachedCriteria dcri3 = DetachedCriteria.forClass(Cat.class)
									.setProjection(Property.forName("weight"));
		cri = session.createCriteria(Cat.class)
				.add(Subqueries.propertyGeAll("weight", dcri3));
		result = cri.list();
		
		session.close();
		session = getTestDaoInstance().getSessionFactory().openSession();	
	}
	
	public static void createFilterTest(){
		Session session = getTestDaoInstance().sessionFactory.openSession();
		Cat cat = (Cat)session.load(Cat.class, 1l);
		String filterString = null;
		List filteredList = null;		
		/*
		 *  simple paging filter, indeed paging function is implemented by Query
		 */
		filterString = "";
		filteredList = session.createFilter(cat.getKittens(), filterString)
						.setFirstResult(0)
						.setMaxResults(1)
						.list();		
		/*
		 * properties selection filter
		 */
		filterString = "select this.weight";
		filteredList = session.createFilter(cat.getKittens(), filterString).list();
		
		/*
		 * where filter
		 */
		
		filterString = "where weight>10 order by weight desc";
		filteredList = session.createFilter(cat.getKittens(), filterString).list();
		
		/*
		 * full filter
		 */
		
		filterString = "select this.weight where this.weight>10 order by this.weight desc";
		filteredList = session.createFilter(cat.getKittens(), filterString).list();		
		
		session.close();
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
		
		/*
		 * 1, polymorphic loading Cat and DomesticCat
		 * 2, return type: List<Object[]>, 3 elements in Object[] for each list element, contents
		 * 		of which are cat, mother, kitten in sequence.
		 * 3, For the 1st element in Object[], cat, property mother is populated, however, property 
		 * 		kitten is not, although lazy attribute for both of them are true.  
		 */
		queryString = "from Cat cat inner join cat.mother mum left outer join cat.kittens kittens";
		
		/*
		 * minor differences with the sql above, 
		 * 
		 * 1, 2 joins involved, both are left outer join, 
		 * 		while as for the sql above, 2 joins involved, one is inner join, the other is 
		 * 		left outer join.
		 * 2, return type is List<Object[]>, 2 elements in Object[]
		 * 3, regards on lazy fetch, they are the same 
		 */
		queryString = "from Cat as cat left join cat.mother.kittens as kittens";
		
		/*
		 * Difference with sql A "from Cat cat inner join cat.mother mum left outer join cat.kittens kittens"
		 * 1, return type: List<Object>(List<Cat> or List<DomesticCat>)
		 * 2, the exact same underlying sql is used
		 * 3, with "fetch", property kittens of Cat is populated, although the lazy attribute is false, whereas 
		 * 		in sql A, it is not populated, even the exact same underlying sql is used.   
		 */		
		queryString = "from Cat as cat inner join fetch cat.mother left outer join fetch cat.kittens";
		
		/*    
		 * "from Cat cat where cat.mother.name = 'eva'"
		 * 
		 * select
			        cat0_.id as id7_,
			        cat0_.weight as weight7_,
			        cat0_.birthdate as birthdate7_,
			        cat0_.color as color7_,
			        cat0_.sex as sex7_,
			        cat0_.litter_id as litter7_7_,
			        cat0_.mother_id as mother8_7_,
			        cat0_.name as name7_,
			        cat0_.subclass as subclass7_ 
			    from
			        cats cat0_ cross 
			    join
			        cats cat1_ 
			    where
			        cat0_.mother_id=cat1_.id 
			        and cat1_.name='eva'
			
			1, return type, List<Cat>
			2, both properties mother and kittens are not populated, property mother is proxied 
		 * 
		 * "from Cat cat inner join cat.mother mother where mother.name='eva'"
		 *     select
			        cat0_.id as id7_0_,
			        cat1_.id as id7_1_,
			        cat0_.weight as weight7_0_,
			        cat0_.birthdate as birthdate7_0_,
			        cat0_.color as color7_0_,
			        cat0_.sex as sex7_0_,
			        cat0_.litter_id as litter7_7_0_,
			        cat0_.mother_id as mother8_7_0_,
			        cat0_.name as name7_0_,
			        cat0_.subclass as subclass7_0_,
			        cat1_.weight as weight7_1_,
			        cat1_.birthdate as birthdate7_1_,
			        cat1_.color as color7_1_,
			        cat1_.sex as sex7_1_,
			        cat1_.litter_id as litter7_7_1_,
			        cat1_.mother_id as mother8_7_1_,
			        cat1_.name as name7_1_,
			        cat1_.subclass as subclass7_1_ 
			    from
			        cats cat0_ 
			    inner join
			        cats cat1_ 
			            on cat0_.mother_id=cat1_.id 
			    where
			        cat1_.name='eva'
		 * 1, return type, List<Object[]>
		 * 2, property mother is populated, though lazy is set to true. property kittens is not populated. 
		 */
		
		queryString = "from Cat cat where cat.mother.name = 'eva'";	
		queryString = "from Cat cat inner join cat.mother mother where mother.name='eva'";
		
		/*
		 * return type: List<Cat>, indeed, java.util.ArrayList is used, not hibernate defined List type
		 */
		queryString = "select mother from Cat cat join cat.mother mother";
		
		/*
		 * exactly same as query above
		 */
		queryString = "select cat.mother from Cat cat"; 
		
		/*
		 * return type: List<String>
		 */
		queryString = "select cat.name from DomesticCat cat";
		
		/*
		 * Difference with "from Cat cat join cat.mother mother left join cat.kittens kittens"
		 * 1, return type: List<List>  java.util.ArrayList<java.util.Arrays$ArrayList>
		 * 2, underlying sql is different, one join sql only get all the ids, and separate simple sql to get Cat by using each id
		 * 3, it is strange two and only two "mother" object are proxies, which are both adam Cat 
		 */
		queryString = "select new list(cat, mother, kittens) from Cat cat join cat.mother mother left join cat.kittens kittens";
		
		/*
		 * List<Object[]>
		 */
		queryString = "select max(weight) as max, min(weight) as min, count(*) as n from Cat cat";
		
		/*
		 * List<SimpleValueObject>
		 * full qualified name has to be designated
		 */
		queryString = "select new outerhaven.dao.vo.SimpleValueObject(max(weight) as max, min(weight) as min, count(*) as n) from Cat";
		
		/*
		 * List<Map>, ArrayList<HashMap>
		 */
		queryString = "select new map(max(weight) as max, min(weight) as min, count(*) as n) from Cat cat";		
		queryString = "select new map(max(weight) as max, min(weight) as min, count(distinct sex) as n) from Cat cat";
		queryString = "select new map(max(weight) as max, min(weight) as min, count(all sex) as n) from Cat cat";
		
		/*
		 *	comparation of instance is converted to comparation of foreign key . 
		 */
		queryString = "from Cat sib1, Cat sib2 where sib1.mother = sib2.mother";
		
		/*
		 * 1, id is pre-defined proper for HQL to reference virtual id column, supposed that
		 * 	  no property with name "id" is defined in entity class
		 * 2, no table join in this case  
		 * 3, The special property class accesses the discriminator value of an instance in the case of
				polymorphic persistence. A Java class name embedded in the where clause will be translated to
				its discriminator value
		 */		
		queryString = "from Cat cat where cat.mother.id=1";
		
		queryString = "from Cat cat where cat.kittens.size > 0 ";
		queryString = "from Cat cat where size(cat.kittens) > 0 ";
		
		queryString = "from Cat mother, Cat kid where kid in elements( mother.kittens ) ";
		
		queryString = "from Cat cat where exists elements(cat.kittens)";
		
		/*
		 * usage of java public static final constant
		 */
		queryString = "from DomesticCat cat where cat.name like '%'||outerhaven.dao.TestDao.CAT_NAME||'%'";
		
		queryString = "from Cat cat where cat.weight > ( select avg(allCat.weight) from DomesticCat allCat )";
		
		queryString = "from Cat cat where not exists ( from Cat mother where mother = cat.mother )";				
			
		queryString = "select cat.id, ( select avg( kitten.weight ) from cat.kittens kitten ) from Cat cat";
		
		queryString = "select cat.id, cat.name from DomesticCat cat join cat.kittens kitten group by cat.id, cat.name order by count(kitten)";
		
		queryString = "from Cat cat where size( cat.kittens ) > 0";
		
		/*
		 * supposed underlying database doesn't support subselect, 
		 * sql "from Cat cat where size( cat.kittens ) > 0" can be partially rewrited to sql below.
		 */
		queryString = "select cat.id from Cat cat join cat.kittens kitten group by cat.id having count(kitten)> 1 ";
		
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
	
	private static ApplicationContext getApplicationContextInstance() {
		if ( null == ctx ){
			ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
		return ctx;
	}
	
	private static TestDao getTestDaoInstance(){
		return (TestDao)getApplicationContextInstance().getBean("testDao");
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
