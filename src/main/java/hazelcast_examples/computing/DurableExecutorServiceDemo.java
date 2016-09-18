package hazelcast_examples.computing;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.durableexecutor.DurableExecutorService;

public class DurableExecutorServiceDemo {
public static void main(String[] args) {
	Config config = new Config();
	config.getDurableExecutorConfig( "myDurableExecSvc" )
	      .setPoolSize (8 )
	      .setDurability(1 )
	      .setCapacity(1);
	HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance(config);
	DurableExecutorService durableExecSvc = hazelcast.getDurableExecutorService("myDurableExecSvc");

	
}
}
