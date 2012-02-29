package outerhaven.dao.vo;

public class VEmpDept {
	public String toString(){
		return String.format("[%s\t%s\t%s\t%s\t%s\t%s]", 
				this.id,
				this.empname,
				this.empage,
				this.context,
				this.deptid,
				this.deptname);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public Integer getEmpage() {
		return empage;
	}
	public void setEmpage(Integer empage) {
		this.empage = empage;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Integer getDeptid() {
		return deptid;
	}
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	private Integer id; 
	private String empname;
	private Integer empage;
	private String context;
	private Integer deptid;
	private String deptname;	
}
