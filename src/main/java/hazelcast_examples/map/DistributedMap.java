package hazelcast_examples.map;

	import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.UrlXmlConfig;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
	import com.hazelcast.core.HazelcastInstance;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
	 
	public class DistributedMap {
	    public static void main(String[] args) {
	        Config config = new Config();
	        config.setInstanceName("DistributedMap");
	        config.getNetworkConfig().setPort(5900 );
	        config.getNetworkConfig().setPortAutoIncrement(true);

	        
	        HazelcastInstance h = Hazelcast.newHazelcastInstance(config);
	        
	     //用它的名字来取得一个已经存在的HazelcastInstance;

	        Hazelcast.getHazelcastInstanceByName("my-instance" );

	        //用它的名字来取得所有已经存在的HazelcastInstance;

	        Set<HazelcastInstance> set= Hazelcast.getAllHazelcastInstances();
	        ConcurrentMap<String, String> map = h.getMap("my-distributed-map");
	        map.put("key", "value");
	        map.get("key");
	         
	        //Concurrent Map methods
	        map.putIfAbsent("somekey", "somevalue");
	        map.replace("key", "value", "newvalue");
	       
	    }
	}    

