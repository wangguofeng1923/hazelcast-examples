package hazelcast_examples.query;

import java.util.Collection;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.PagingPredicate;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;

import hazelcast_examples.domain.Employee;

public class PagingPredicates {
public static void main(String[] args) {
	HazelcastInstance instance=HazelcastClient.newHazelcastClient();
	IMap<String, Employee> map = instance.getMap( "my-distributed-map" );
	Predicate greaterEqual = Predicates.greaterEqual( "age", 18 );
	PagingPredicate pagingPredicate = new PagingPredicate( greaterEqual, 5 );
	// Retrieve the first page
	Collection<Employee> values = map.values( pagingPredicate );

	// Set up next page
	pagingPredicate.nextPage();
	// Retrieve next page
	values = map.values( pagingPredicate );
}
}
