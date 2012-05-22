package outerhaven.dao.vo;

import java.util.Date;

public class PurchaseItems {
	private Integer id;
	private Integer orderId;
	private Integer itemId;
	private Double price;
	private Integer quantity;
	private Date purchaseDate;
	private Order order;
	private Item item;
	@Override
	public boolean equals(Object other){
		if ( this == other ){
			return true;		
		}
		if (! (other instanceof PurchaseItems) ){
			return false;
		}
		if ( this.id.equals(((PurchaseItems)other).getId()) ){
			return false;
		}
		return true;
	}
	@Override
	public int hashCode(){
		return  null == this.id? 0 : this.id.hashCode();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
}
