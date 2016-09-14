package hazelcast_examples.multiMap;

import java.util.Collection;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

public class MultiMapDemo {
	private static void printData(){
		 HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
		 MultiMap<Object, Object> map = hazelcastInstance.getMultiMap( "map" );
		 for ( Object key : map.keySet() ){
		      Collection <Object > values = map.get( key );
		      System.out.printf( "%s -> %s\n",String.valueOf(key), values.toString() );
		    }

		    System.out.println( "PutMember:Done" );
	}
	private static void putData(){
		 HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
		 MultiMap<Object, Object> map = hazelcastInstance.getMultiMap( "map" );
		    map.put( "a", "1" );
		    map.put( "a", "2" );
		    map.put( "b", "3" ); 
		    System.out.println( "PutMember:Done" );
	}
	public static void main(String[] args) {
			putData();
			printData();
	}
}
