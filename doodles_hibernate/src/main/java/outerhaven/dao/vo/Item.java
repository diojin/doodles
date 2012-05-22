package outerhaven.dao.vo;

import java.util.Set;

public class Item {
	private Integer id;
	private String name;
	private String description;
	private Set<PurchaseItems> orders;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<PurchaseItems> getOrders() {
		return orders;
	}
	public void setOrders(Set<PurchaseItems> orders) {
		this.orders = orders;
	}	
}
