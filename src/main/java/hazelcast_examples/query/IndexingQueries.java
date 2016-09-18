package hazelcast_examples.query;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import hazelcast_examples.domain.Employee;

public class IndexingQueries {
public static void main(String[] args) {
	HazelcastInstance instance=HazelcastClient.newHazelcastClient();
	IMap<String, Employee> map = instance.getMap( "my-distributed-map" );
	// ordered, since we have ranged queries for this field
	map.addIndex( "age", true );
	// not ordered, because boolean field cannot have range
	map.addIndex( "active", false );
}
}
