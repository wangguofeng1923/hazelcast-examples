package hazelcast_examples.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.MigrationEvent;
import com.hazelcast.core.MigrationListener;

public class MigrationListenerDemo implements MigrationListener{
	private static final Logger logger=LoggerFactory.getLogger(MigrationListenerDemo.class);
	@Override
	public void migrationCompleted(MigrationEvent migrationEvent) {
		logger.info("migrationCompleted: " + migrationEvent);
		
	}

	@Override
	public void migrationFailed(MigrationEvent migrationEvent) {
		logger.info("migrationFailed: " + migrationEvent);
		
	}

	@Override
	public void migrationStarted(MigrationEvent migrationEvent) {
		logger.info("migrationStarted: " + migrationEvent);
		
	}

}
