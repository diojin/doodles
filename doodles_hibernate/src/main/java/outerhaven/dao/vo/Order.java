package outerhaven.dao.vo;

import java.util.Set;

public class Order {
	private Integer id;
	private String customerName;
	private String address;
	private Set<PurchaseItems> items;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Set<PurchaseItems> getItems() {
		return items;
	}
	public void setItems(Set<PurchaseItems> items) {
		this.items = items;
	}
	
}
