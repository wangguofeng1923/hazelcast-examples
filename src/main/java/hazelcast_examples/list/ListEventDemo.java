package hazelcast_examples.list;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

import hazelcast_examples.domain.Price;
import hazelcast_examples.domain.Sample;

public class ListEventDemo {
	  public static void main( String[] args ) { 
		    Sample sample = new Sample();
		    HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
		    IList<Price> list = hazelcastInstance.getList( "default" );
		    list.addItemListener( sample, true ); 

		    Price price = new Price( 10 );
		    list.add( price );
		    list.remove( price );
		  } 
}
