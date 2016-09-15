package hazelcast_examples.countDownLatch;

import java.util.concurrent.TimeUnit;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICountDownLatch;

public class Follower {
  public static void main( String[] args ) throws Exception {
    HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
    ICountDownLatch latch = hazelcastInstance.getCountDownLatch( "countDownLatch" );
    System.out.println( "Waiting" );
    boolean success = latch.await( 10, TimeUnit.SECONDS );
    System.out.println( "Complete: " + success );
  }
}