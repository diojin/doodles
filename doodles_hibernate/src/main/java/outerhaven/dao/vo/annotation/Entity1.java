package outerhaven.dao.vo.annotation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  
public class Entity1 implements Serializable {
	protected long id;	
	/**
	 * default display value for this domain object
	 */
	protected String displayValue;
	
	public Entity1()
	{
		super();
	}
	
	public Entity1(long Id)
	{
		super();
		this.id = Id;
	}	
	
	public Entity1(long Id, String displayValue)
	{
		super();
		this.id = Id;
		this.displayValue = displayValue;
	}
	
	public Entity1(String displayValue)
	{
		super();
		this.displayValue = displayValue;
	}		
	@Id
	@Column(name="id", nullable=false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@Column(name="dispvalue")
	public String getDisplayValue() {
		return displayValue;
	}


	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (getClass().isInstance(obj)) {
			Entity1 other = (Entity1)obj;
			return getId() == other.getId();
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return (int)(id ^ (id >>> 32));
	}
}