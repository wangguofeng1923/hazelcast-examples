package hazelcast_examples.replicatedMap;

import java.util.Collection;
import java.util.Map;

import com.hazelcast.config.Config;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.ReplicatedMapConfig;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapEvent;
import com.hazelcast.core.ReplicatedMap;

import hazelcast_examples.domain.Customer;
/**
A Replicated Map does not partition data (it does not spread data to different cluster members); 
instead, it replicates the data to all members. All other data structures are partitioned in design.

Replication leads to higher memory consumption. However, a Replicated Map has faster read and write access since the data is available on all members.

Writes could take place on local/remote members in order to provide write-order, eventually being replicated to all other members.

Replicated Map is suitable for objects, catalog data, or idempotent calculable data (such as HTML pages). 
It fully implements the java.util.Map interface, 
but it lacks the methods from java.util.concurrent.ConcurrentMap since there are no atomic guarantees to writes or reads.
 * @author Administrator
 *
 */
public class ReplicatedMapDemo {
public static void main(String[] args) {
	EntryListener<String, Customer>listener=new EntryListener<String, Customer>() {
		  @Override
		  public void entryAdded( EntryEvent<String, Customer> event ) {
		    System.out.println( "Entry added: " + event );
		  }

		  @Override
		  public void entryUpdated( EntryEvent<String, Customer> event ) {
			  System.out.println( "Entry updated: " + event );
		  }

		  @Override
		  public void entryRemoved( EntryEvent<String, Customer> event ) {
			  System.out.println( "Entry removed: " + event );
		  }

		  @Override
		  public void entryEvicted( EntryEvent<String, Customer> event ) {
			  System.out.println( "entryEvicted: " + event );
		  }

		@Override
		public void mapCleared(MapEvent paramMapEvent) {
			 System.out.println( "mapCleared: " + paramMapEvent );
			
		}

		@Override
		public void mapEvicted(MapEvent paramMapEvent) {
			 System.out.println( "mapEvicted: " + paramMapEvent );
			
		}
		};
	
	Config config=new Config();
	ReplicatedMapConfig replicatedMapConfig=new ReplicatedMapConfig();
	replicatedMapConfig.setInMemoryFormat(InMemoryFormat.OBJECT);
	replicatedMapConfig.setName("customers");
	replicatedMapConfig.setStatisticsEnabled(true);
	replicatedMapConfig.setAsyncFillup(true);
	config.getReplicatedMapConfigs().put("customers", replicatedMapConfig);
	
	HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
	ReplicatedMap<String, Customer> customers = hazelcastInstance.getReplicatedMap("customers");
	customers.addEntryListener(listener);
	customers.put( "1", new Customer( "Joe", "Smith" ) );
	customers.put( "2", new Customer( "Ali", "Selam" ) );
	customers.put( "3", new Customer( "Avi", "Noyan" ) );

	Collection<Customer> colCustomers = customers.values();
	for ( Customer customer : colCustomers ) {
	  // process customer
		System.out.println(customer.getFirstName());
	}
	hazelcastInstance.shutdown();
}
}
