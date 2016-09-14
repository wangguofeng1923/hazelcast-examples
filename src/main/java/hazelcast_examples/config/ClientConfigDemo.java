package hazelcast_examples.config;

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

public class ClientConfigDemo {
	public static void main(String[] args) throws FileNotFoundException {
		startInstance();
		ClientConfig config=new ClientConfig();
		config.setLicenseKey( "Your Enterprise License Key" );
		HazelcastInstance instance=HazelcastClient.newHazelcastClient(config);
	
	}
	private static void startInstance(){
		Config config = new Config();
        config.setInstanceName("DistributedMap");

        
        HazelcastInstance h = Hazelcast.newHazelcastInstance(config);
       
	}
}
