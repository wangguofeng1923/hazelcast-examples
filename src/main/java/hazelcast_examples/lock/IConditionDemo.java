package hazelcast_examples.lock;


public class IConditionDemo {
	
	
	public static void main(String[] args) throws InterruptedException {
		new Consumer().doConsume();
		new Producer().doProduce();
		
	}
}
