package hazelcast_examples.computing;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.hazelcast.config.Config;
import com.hazelcast.config.ExecutorConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.IMap;

public class Main {
public static void main(String[] args) throws InterruptedException, ExecutionException {
	
	Config config = new Config();
	ExecutorConfig executorConfig = config.getExecutorConfig("exec");
	executorConfig.setPoolSize( 1 ).setQueueCapacity( 10 )
	          .setStatisticsEnabled( true );
	          
	  HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
	  IExecutorService executorService = 
	      hazelcastInstance.getExecutorService( "default" );
	  
	  for ( int k = 1; k <= 10; k++ ) {
	      Thread.sleep( 1000 );
	      System.out.println( "Producing echo task: " + k );
	      executorService.execute( new EchoTask( String.valueOf( k ) ) );
	    }
	    System.out.println( "EchoTaskMain finished!" );
	    
	  Future<String>f=executorService.submit(new Echo("hello"));
	  String result=f.get();
	  System.out.println(result);
	  
	    IMap<String, Integer> map = hazelcastInstance.getMap( "map" );
	    map.put("a", 1);
	    map.put("b", 2);
	    map.put("c", 3);
		  Future<Integer>future=executorService.submit(new SumTask());
//		  Integer v=future.get();
		  try {
			  Integer value=future.get( 3, TimeUnit.SECONDS );
			  System.out.println(value);
		  } catch ( TimeoutException e ) {
		    future.cancel( true );            
		  }
	
	    
}
}
