package hazelcast_examples.atomicLong;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IFunction;

public class AtomicLongDemo {
	private static void incrementAndGet(HazelcastInstance hazelcastInstance ){
		IAtomicLong counter = hazelcastInstance.getAtomicLong( "counter" );
	    for ( int k = 0; k < 1000 * 10; k++ ) {
	      if ( k % 5000 == 0 ) {
	        System.out.println( "At: " + k );
	      }
	      counter.incrementAndGet();
	    }
	    System.out.printf( "Count is %s\n", counter.get() );
	}
	private static void applyFun(HazelcastInstance hazelcastInstance){
		IAtomicLong atomicLong = hazelcastInstance.getAtomicLong( "atomicLong" );
		 atomicLong.set( 1 );
		 IFunction<Long,Long>fun=new IFunction<Long, Long>() {


				private static final long serialVersionUID = 1L;

				@Override
				public Long apply(Long v) {
					return v+3;
				}
			};
			long result= atomicLong.apply(fun);
			System.out.println( "apply.result: " + result);         
		    System.out.println( "apply.value: " + atomicLong.get() );

		    atomicLong.set( 1 );
		    atomicLong.alter(fun);             
		    System.out.println( "alter.value: " + atomicLong.get() );

		    atomicLong.set( 1 );
		    result = atomicLong.alterAndGet(fun);         
		    System.out.println( "alterAndGet.result: " + result );         
		    System.out.println( "alterAndGet.value: " + atomicLong.get() );

		    atomicLong.set( 1 );
		    result = atomicLong.getAndAlter( fun);         
		    System.out.println( "getAndAlter.result: " + result );         
		    System.out.println( "getAndAlter.value: " + atomicLong.get() );
		
		
	}
	public static void main(String[] args) {
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();     
		incrementAndGet(hazelcastInstance);
		applyFun(hazelcastInstance);
	}
}
