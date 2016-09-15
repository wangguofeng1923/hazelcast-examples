package hazelcast_examples.events;

import java.util.Collection;

import com.hazelcast.config.Config;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.MapPartitionLostEvent;
import com.hazelcast.map.listener.MapPartitionLostListener;
/*
Global Event Configuration
hazelcast.event.queue.capacity: default value is 1000000
hazelcast.event.queue.timeout.millis: default value is 250
hazelcast.event.thread.count: default value is 5

A striped executor in each cluster member controls and dispatches the received events.
 This striped executor also guarantees the event order. 
 For all events in Hazelcast, the order in which events are generated and the order in which they are published are guaranteed for given keys. For map and multimap,
  the order is preserved for the operations on the same key of the entry. For list, set, topic and queue, the order is preserved for events on that instance of the distributed data structure.

To achieve the order guarantee, you make only one thread responsible for a particular set of events (entry events of a key in a map, item events of a collection, etc.) in StripedExecutor (within com.hazelcast.util.executor).

If the event queue reaches its capacity (hazelcast.event.queue.capacity) and the last item cannot be put into the event queue for the period specified in hazelcast.event.queue.timeout.millis, these events will be dropped with a warning message, such as "EventQueue overloaded".

If event listeners perform a computation that takes a long time, the event queue can reach its maximum capacity and lose events. For map and multimap, you can configure hazelcast.event.thread.count to a higher value so that fewer collisions occur for keys, and therefore worker threads will not block each other in StripedExecutor. For list, set, topic and queue, you should offload heavy work to another thread. To preserve order guarantee, you should implement similar logic with StripedExecutor in the offloaded thread pool. 
*/
public class ConfigListeners {
	private static void mapListenerTest(HazelcastInstance hazelcastInstance){
		IMap<String, String> map = hazelcastInstance.getMap( "somemap");
		map.addEntryListener(new EntryListenerDemo(), true);
		 map.addPartitionLostListener(new MapPartitionLostListener() {
		      @Override
		      public void partitionLost(MapPartitionLostEvent event) {
		        System.out.println(event);
		      }
		    });
	    String key = "" + System.nanoTime();
	    String value = "1";
	    map.put( key, value );
	    map.put( key, "2" );
	    map.delete( key );

	}
	
	public static void main(String[] args) {
		Config config=new Config();
		config.getMapConfig("map").setBackupCount(1);
		
		ListenerConfig membershipListenerConfig=new ListenerConfig();
		membershipListenerConfig.setImplementation(new MembershipListenerDemo());
		config.getListenerConfigs().add(membershipListenerConfig);
		
		ListenerConfig distributedObjectListenerConfig=new ListenerConfig();
		distributedObjectListenerConfig.setImplementation(new DistributedObjectListenerDemo());
		config.getListenerConfigs().add(distributedObjectListenerConfig);
		
		
		ListenerConfig migrationListenerConfig=new ListenerConfig();
		migrationListenerConfig.setImplementation(new MigrationListenerDemo());
		config.getListenerConfigs().add(migrationListenerConfig);
		
		
		ListenerConfig lifecycleListenerConfig=new ListenerConfig();
		lifecycleListenerConfig.setImplementation(new LifecycleListenerDemo());
		config.getListenerConfigs().add(lifecycleListenerConfig);
		
//		ListenerConfig entryListenerConfig=new EntryListenerConfig();
//		entryListenerConfig.setImplementation(new EntryListenerDemo());
//		config.getListenerConfigs().add(entryListenerConfig);
		
		
		
		 HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
		 
		 mapListenerTest(hazelcastInstance);
		 Collection<DistributedObject> distributedObjects = hazelcastInstance.getDistributedObjects();
		 for (DistributedObject distributedObject : distributedObjects) {
		      System.out.println(distributedObject.getName() + "," + distributedObject.getPartitionKey());
		 }
//		 hazelcastInstance.shutdown();
	}
}
