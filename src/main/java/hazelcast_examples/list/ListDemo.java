package hazelcast_examples.list;

import java.util.Iterator;
import java.util.List;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import hazelcast_examples.domain.Price;

public class ListDemo {
	public static void main(String[]args){

		HazelcastInstance hz = Hazelcast.newHazelcastInstance();
		
		List<Price> list = hz.getList( "IBM-Quote-Frequency" );
		list.add( new Price( 10 ) );
		list.add( new Price( 11 ) );
		list.add( new Price( 12 ) );
		list.add( new Price( 11 ) );
		list.add( new Price( 12 ) );
		
		//....
		Iterator<Price> iterator = list.iterator();
		while ( iterator.hasNext() ) { 
		  Price price = iterator.next(); 
		  //analyze
		}
	}
}
