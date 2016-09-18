package hazelcast_examples.query;

import java.util.Collection;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import com.hazelcast.query.SqlPredicate;

import hazelcast_examples.domain.Office;

public class CollectionArrayQuery {
	public static void main(String[] args) {
		HazelcastInstance hazelcastInstance=Hazelcast.newHazelcastInstance();
		IMap<String,Office> map = hazelcastInstance.getMap( "Offices" );
		map.addIndex("employees[any].age", true);
		Predicate p=Predicates.and(Predicates.greaterThan("id", 2),Predicates.greaterEqual("employees[any].age", 20));
		Collection<Office> result = map.values(p);
		
//		Predicate p = new SqlPredicate("employees[0].name", "guofeng");
//		Predicate predicate = new SqlPredicate("employees[any].name", "guofeng");
//		Collection<Office> coll = map.values(predicate);
	}
}
