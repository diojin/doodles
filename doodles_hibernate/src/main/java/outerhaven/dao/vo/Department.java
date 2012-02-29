package outerhaven.dao.vo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Department {
	public String toString(){
		StringBuffer buff = new StringBuffer( String.format("[%s\t%s\nChildrens:\n",this.id,this.deptname));
//		if ( this.employees !=null ){
//			for (Iterator<Employee> it = this.employees.iterator();it.hasNext(); ){
//				Employee el = it.next();
//				buff.append(String.format("[%s\t%s]\n", 
//						el.getDeptid(),
//						el.getEmpname()));
//			}			
//		}
		return buff.toString();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public Set<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	private Integer id;
	private String deptname;
	private Set<Employee> employees=new HashSet<Employee>();
}
