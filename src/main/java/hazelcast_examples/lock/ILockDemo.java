package hazelcast_examples.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;

public class ILockDemo {
	private static void lockWithTimeout(ILock lock){
		lock.lock( 5, TimeUnit.SECONDS );
		try {
		  // do some stuff here..
		  System.out.println("process business logic");
		} finally {
		  try {
		    lock.unlock();
		  } catch ( IllegalMonitorStateException ex ){
		    // WARNING Critical section guarantee can be broken
		  }
		}
	}
	private static void trylockWithTimeout(ILock lock) throws InterruptedException{
		//tryLock with timeout
		if ( lock.tryLock ( 10, TimeUnit.SECONDS ) ) {
			  try {  
			    // do some stuff here..  
				  System.out.println("process business logic");
			  } finally {  
			    lock.unlock();  
			  }   
		} else {
		  // warning
			System.err.println("couldn't get lock");
		}
	}
	private static void lock(ILock lock) throws InterruptedException{
		//get lock directly
				lock.lock();
				//get lock with timeout
				//lock.lock( 5, TimeUnit.SECONDS )
				try {
					// do some stuff here..  
				  System.out.println("process business logic");
				} finally {
				  lock.unlock();
				}
	}
	public static void main(String[] args) throws InterruptedException {
	
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
		ILock lock = hazelcastInstance.getLock( "myLock" );
		//common usage 
		lock(lock);
		//using lock with lease time
		lockWithTimeout(lock);
		//try get lock with lease time
		trylockWithTimeout(lock);
		
		
		
	}
}
