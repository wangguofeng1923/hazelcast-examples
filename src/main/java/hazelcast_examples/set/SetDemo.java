package hazelcast_examples.set;

import java.util.Iterator;
import java.util.Set;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ISet;

import hazelcast_examples.domain.Price;
import hazelcast_examples.domain.Sample;

public class SetDemo {
	public static void main(String[] args) {
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

		Set<Price> set = hazelcastInstance.getSet( "IBM-Quote-History" );
		set.add( new Price( 10) );
		set.add( new Price( 11) );
		set.add( new Price( 12) );
		set.add( new Price( 11) );
		//....
		Iterator<Price> iterator = set.iterator();
		while ( iterator.hasNext() ) { 
		  Price price = iterator.next(); 
		  //analyze
		}
	}
}
