package hazelcast_examples.lock;

import java.util.ArrayList;
import java.util.List;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICondition;
import com.hazelcast.core.ILock;

public class Consumer {
	private HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
	private ILock lock = hazelcastInstance.getLock( "myLockId" );
	private ICondition condition = lock.newCondition( "myConditionId" );
	private List<String>list=new ArrayList<>();
	public Consumer(){
		this.list=hazelcastInstance.getList("data-store");
	}
	private  boolean canConsume(){
		if(list.isEmpty()){
			return false;
		}
		return true;
	}
	private void consume(){
		
		String obj=list.get(0);
		list.remove(obj);
		System.out.println("consume object: "+obj);
	}
	
	public  void doConsume() throws InterruptedException{
		new Thread(()->{
			
			lock.lock();
			try {
				try {
				 while ( !canConsume() ) {
					    condition.await(); // frees the lock and waits for signal
					                       // when it wakes up it re-acquires the lock if 
					                       // available or waits for it to become
					                       // available
					  }
					  consume();
					  condition.signalAll();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			 
			} finally {
			  lock.unlock();
			}
			System.out.println("finish consume");
		}).start();
		
	}
}
