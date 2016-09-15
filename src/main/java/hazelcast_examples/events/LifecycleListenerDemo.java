package hazelcast_examples.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.LifecycleEvent;
import com.hazelcast.core.LifecycleListener;
/*
The Lifecycle Listener notifies for the following events:

STARTING: A member is starting.
STARTED: A member started.
SHUTTING_DOWN: A member is shutting down.
SHUTDOWN: A member's shutdown has completed.
MERGING: A member is merging with the cluster.
MERGED: A member's merge operation has completed.
CLIENT_CONNECTED: A Hazelcast Client connected to the cluster.
CLINET_DISCONNECTED: A Hazelcast Client disconnected from the cluster.
 */
public class LifecycleListenerDemo implements LifecycleListener{
	private static final Logger logger=LoggerFactory.getLogger(LifecycleListenerDemo.class);
	@Override
	public void stateChanged(LifecycleEvent paramLifecycleEvent) {
		logger.info("stateChanged: "+paramLifecycleEvent);
		
	}

}
