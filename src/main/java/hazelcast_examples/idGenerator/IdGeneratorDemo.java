package hazelcast_examples.idGenerator;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IdGenerator;

public class IdGeneratorDemo {
	//run this class several times.
	public static void main( String[] args ) throws Exception {
	    HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
	    IdGenerator idGen = hazelcastInstance.getIdGenerator( "newId" );
	    //the start value can be initialized with follow code.otherwise the id will from 1 after cluster restart.
	    //idGen.init(100L);
	    while (true) {
	      Long id = idGen.newId();
	      System.err.println( "Id: " + id );
	      Thread.sleep( 1000 );
	    }
	  }
}
