package hazelcast_examples.lock;

import java.util.ArrayList;
import java.util.List;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICondition;
import com.hazelcast.core.ILock;

public class Producer {
	private HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
	private ILock lock = hazelcastInstance.getLock( "myLockId" );
	private ICondition condition = lock.newCondition( "myConditionId" );
	private List<String>list=new ArrayList<>();
	public Producer(){
		this.list=hazelcastInstance.getList("data-store");
	}
	private  boolean shouldProduce() {
		return this.list.size()==0;
	}
	private  void produce() {
		
		String obj=String.valueOf(System.currentTimeMillis());
		this.list.add(obj);
		System.out.println("produce object: "+obj);
	}
	
	public  void doProduce() throws InterruptedException {
		new Thread(()->{
			lock.lock();
			
			try{
				try{
					while ( !shouldProduce() ) {
					    condition.await(); // frees the lock and waits for signal
					                       // when it wakes up it re-acquires the lock
					                       // if available or waits for it to become
					                       // available
					  }
					produce();
					condition.signalAll();
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}finally{
				lock.unlock();
			}
			System.out.println("finish product");
			
		}).start();
		
	}
}
