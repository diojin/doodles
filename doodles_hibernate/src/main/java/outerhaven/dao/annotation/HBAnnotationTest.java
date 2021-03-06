package outerhaven.dao.annotation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import outerhaven.dao.vo.annotation.Entity1;
import outerhaven.dao.vo.annotation.EntityChild1;
import outerhaven.dao.vo.annotation.EntityChild2;

public class HBAnnotationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		testHybridHBPlainMapping();
		testHBAnnotation();
	}
	public static void testHBAnnotation(){
		try{
			SessionFactory sf = new AnnotationConfiguration()
			.configure()
			.addPackage("outerhaven.dao.vo.annotation")
			.addAnnotatedClass(Entity1.class)
			.addAnnotatedClass(EntityChild1.class)
			.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tran=session.beginTransaction();
//			Entity1 val = new Entity1();
//			val.setId(2l);
//			val.setDisplayValue("haha");
			EntityChild1 child = new EntityChild1();
			child.setId(6l);
			child.setDisplayValue("hah child again");
			child.setName("child6");
			session.save(child);
			tran.commit();
			session.close();
		
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void testHybridHBPlainMapping(){
		try{
			SessionFactory sf = new AnnotationConfiguration()
			.configure()
			.addPackage("outerhaven.dao.vo.annotation")
			.addAnnotatedClass(Entity1.class)
			.addAnnotatedClass(EntityChild1.class)
			.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tran=session.beginTransaction();
			EntityChild2 child = new EntityChild2();
			child.setId(10l);
			child.setDisplayValue("hah child again");
			child.setComment("hahah again");
			session.save(child);
			tran.commit();
			session.close();
		
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
