package outerhaven.dao.vo;

public class SimpleValueObject {
	private float maxWeight;
	private float minWeight;
	private long count;
	
	public SimpleValueObject() {
		super();
	}
	public SimpleValueObject(float maxWeight, float minWeight, long count) {
		this.maxWeight = maxWeight;
		this.minWeight = minWeight;
		this.count = count;
	}
	public float getMaxWeight() {
		return maxWeight;
	}
	public void setMaxWeight(float maxWeight) {
		this.maxWeight = maxWeight;
	}
	public float getMinWeight() {
		return minWeight;
	}
	public void setMinWeight(float minWeight) {
		this.minWeight = minWeight;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}	
}
