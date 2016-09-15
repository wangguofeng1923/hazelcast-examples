package hazelcast_examples.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.MapEvent;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryEvictedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;
import com.hazelcast.map.listener.MapClearedListener;
import com.hazelcast.map.listener.MapEvictedListener;

public class EntryListenerDemo implements   EntryAddedListener<String, String>, 
											EntryRemovedListener<String, String>, 
											EntryUpdatedListener<String, String>, 
											EntryEvictedListener<String, String> , 
											MapEvictedListener, 
											MapClearedListener{
	private static final Logger logger=LoggerFactory.getLogger(EntryListenerDemo.class);
	@Override
    public void entryAdded( EntryEvent<String, String> event ) {
      logger.info( "Entry Added:" + event );
    }

    @Override
    public void entryRemoved( EntryEvent<String, String> event ) {
      logger.info( "Entry Removed:" + event );
    }

    @Override
    public void entryUpdated( EntryEvent<String, String> event ) {
      logger.info( "Entry Updated:" + event );
    }

    @Override
    public void entryEvicted( EntryEvent<String, String> event ) {
      logger.info( "Entry Evicted:" + event );
    }

    @Override
    public void mapEvicted( MapEvent event ) {
      logger.info( "Map Evicted:" + event );
    }

    @Override
    public void mapCleared( MapEvent event ) {
      logger.info( "Map Cleared:" + event );
    }


}
