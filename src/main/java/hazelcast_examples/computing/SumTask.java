package hazelcast_examples.computing;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IMap;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class SumTask
    implements Callable<Integer>, Serializable, HazelcastInstanceAware {

	private static final long serialVersionUID = 1L;
	private transient HazelcastInstance hazelcastInstance;

  public void setHazelcastInstance( HazelcastInstance hazelcastInstance ) {
    this.hazelcastInstance = hazelcastInstance;
  }

  public Integer call() throws Exception {
    IMap<String, Integer> map = hazelcastInstance.getMap( "map" );
    int result = 0;
    for ( String key : map.localKeySet() ) {
      System.out.println( "Calculating for key: " + key );
      result += map.get( key );
    }
    System.out.println( "Local Result: " + result );
    return result;
  }
}