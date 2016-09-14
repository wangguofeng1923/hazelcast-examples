package hazelcast_examples.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class QueueDemo {
	public static void main(String[] args) throws InterruptedException {
		Config config=new Config("demo-instance");
    	config.getProperties().put("hazelcast.logging.type", "slf4j");
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);

		BlockingQueue<MyTask> queue = hazelcastInstance.getQueue( "tasks" );
		queue.put( new MyTask() );
		MyTask task = queue.take();
	
		boolean offered = queue.offer( new MyTask(), 10, TimeUnit.SECONDS );
		task = queue.poll( 5, TimeUnit.SECONDS );
		if ( task != null ) {
		  //process task
		}
	}
}
