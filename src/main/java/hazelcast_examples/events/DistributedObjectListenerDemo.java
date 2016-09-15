package hazelcast_examples.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.DistributedObjectEvent;
import com.hazelcast.core.DistributedObjectListener;

public class DistributedObjectListenerDemo implements DistributedObjectListener {
	private static final Logger logger=LoggerFactory.getLogger(DistributedObjectListenerDemo.class);
	@Override
	public void distributedObjectCreated(DistributedObjectEvent event) {
		 DistributedObject instance = event.getDistributedObject();
		 logger.info("distributedObjectCreated " + instance.getName() + "," + instance.getPartitionKey());
		
	}

	@Override
	public void distributedObjectDestroyed(DistributedObjectEvent event) {
		 DistributedObject instance = event.getDistributedObject();
		 logger.info("distributedObjectDestroyed: " + instance.getName() + "," + instance.getPartitionKey());
		
	}

}
