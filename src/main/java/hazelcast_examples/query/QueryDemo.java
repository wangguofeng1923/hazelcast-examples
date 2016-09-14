package hazelcast_examples.query;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;

public class QueryDemo {
	public static void main(String[] args) throws FileNotFoundException {
		startInstance();
		ClientConfig config=new ClientConfig();
		config.setLicenseKey( "Your Enterprise License Key" );
		HazelcastInstance instance=HazelcastClient.newHazelcastClient(config);
		IMap<String, Employee> map = instance.getMap( "my-distributed-map" );

		EntryObject e = new PredicateBuilder().getEntryObject();
		Predicate predicate = e.is( "active" ).and( e.get( "age" ).lessThan( 30 ) );
		Collection<Employee>coll=map.values( predicate );
//		Set<Employee> employees = map.values( predicate );
		System.out.println(coll.size());
	}
	private static void startInstance(){
		Config config = new Config();
        config.setInstanceName("DistributedMap");

        
        HazelcastInstance h = Hazelcast.newHazelcastInstance(config);
        ConcurrentMap<String, Employee> map = h.getMap("my-distributed-map");
        map.put("demo1", new Employee("guofeng1",28,true,100D));
        map.put("demo2", new Employee("guofeng2",29,true,100D));
        map.put("demo3", new Employee("guofeng3",30,true,100D));
        map.put("demo4", new Employee("guofeng4",31,true,100D));
	}
}
