package hazelcast_examples.domain;

import java.io.Serializable;

public class Price  implements Serializable{
	private static final long serialVersionUID = 1L;
	private int count=0;
	public Price(int count) {
		super();
		this.count = count;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
