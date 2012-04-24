package outerhaven.gemfire.domain;

import java.io.Serializable;

public class ObjectA implements Serializable  {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString(){
		return name;
	}
}
