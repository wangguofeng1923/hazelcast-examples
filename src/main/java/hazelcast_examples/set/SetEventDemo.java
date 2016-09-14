package hazelcast_examples.set;

import java.util.Iterator;
import java.util.Set;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ISet;

import hazelcast_examples.domain.Price;
import hazelcast_examples.domain.Sample;

public class SetEventDemo {

public static void main(String[] args) {
	 	Sample sample = new Sample();
	    HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
	    ISet<Price> set = hazelcastInstance.getSet( "default" );
	    set.addItemListener( sample, true ); 

	    Price price = new Price( 10 );
	    set.add( price );
	    set.remove( price );
	    hazelcastInstance.shutdown();
}
}
