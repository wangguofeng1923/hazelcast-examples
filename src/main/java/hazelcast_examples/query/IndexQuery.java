package hazelcast_examples.query;

import java.util.Collection;

import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;

import hazelcast_examples.domain.Employee;

public class IndexQuery {
	public static void main(String[] args) {
		HazelcastInstance hazelcastInstance=Hazelcast.newHazelcastInstance();
		IMap<String,Employee> map = hazelcastInstance.getMap( "employees" );
		map.addIndex("age", true);//create index on field:age, and ordered it.(queyr age like ag>30)
		map.addIndex("name", false);//create index on field:name without ordered.
		Predicate predicate=Predicates.and(Predicates.greaterEqual("age", 30),Predicates.like("name", "guofeng"));
		Collection<Employee>coll=map.values(predicate);
		System.out.println(coll.size());
		MapConfig mapConfig=new MapConfig();
		mapConfig.addMapIndexConfig(new MapIndexConfig("name", false));
		mapConfig.addMapIndexConfig(new MapIndexConfig("age", true));
	}
}
