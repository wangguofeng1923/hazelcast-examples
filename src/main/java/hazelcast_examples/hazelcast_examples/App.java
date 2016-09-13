package hazelcast_examples.hazelcast_examples;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//    	System.setProperty("hazelcast.logging.class", "com.hazelcast.logging.MySlf4jFactory");
    	Config config=new Config("demo-instance");
    	config.getProperties().put("hazelcast.logging.type", "slf4j");
    	
        HazelcastInstance instance=Hazelcast.newHazelcastInstance(config);
    }
}
