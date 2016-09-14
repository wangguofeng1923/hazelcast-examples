package hazelcast_examples.semaphore;

import com.hazelcast.config.Config;
import com.hazelcast.config.SemaphoreConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.ISemaphore;

public class ISemaphoreDemo {
	public static void main(String[] args) throws InterruptedException {
		Config config=new Config();
		config.getGroupConfig().setName("ISemaphoreDemo");
		SemaphoreConfig semaphoreConfig=config.getSemaphoreConfig("semaphore");
		//only three thread can call method:semaphore.acquire(),other threads will be blocked on it,
		//until some thread call method:semaphore.release(); 
		semaphoreConfig.setInitialPermits(3);
		semaphoreConfig.setBackupCount(1);
		
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config); 
	    ISemaphore semaphore = hazelcastInstance.getSemaphore( "semaphore" ); 
	    IAtomicLong resource = hazelcastInstance.getAtomicLong( "resource" ); 
	    for ( int k = 0 ; k < 1000 ; k++ ) {
	        System.out.println( "At iteration: " + k + ", Active Threads: " + resource.get() );
	        semaphore.acquire();
	        try {
	          resource.incrementAndGet();
	          Thread.sleep( 20000);
	          resource.decrementAndGet();
	        } finally { 
	          semaphore.release();
	        }
	      }
	      System.out.println("Finished");
	    
	}
}
