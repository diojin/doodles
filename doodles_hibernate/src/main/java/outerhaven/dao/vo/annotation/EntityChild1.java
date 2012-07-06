package outerhaven.dao.vo.annotation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
@Entity
@Table(name="entity_child1")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class EntityChild1 extends Entity1 {
	@Column(name="name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
