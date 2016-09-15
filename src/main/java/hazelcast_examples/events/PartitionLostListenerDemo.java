package hazelcast_examples.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.partition.PartitionLostEvent;
import com.hazelcast.partition.PartitionLostListener;

public class PartitionLostListenerDemo implements PartitionLostListener{
	private static final Logger logger=LoggerFactory.getLogger(PartitionLostListenerDemo.class);
	@Override
	public void partitionLost(PartitionLostEvent paramPartitionLostEvent) {
		logger.info("partitionLost: "+paramPartitionLostEvent);
		
	}

}
