package hazelcast_examples.ringbuffer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.hazelcast.config.Config;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.RingbufferConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IFunction;
import com.hazelcast.ringbuffer.OverflowPolicy;
import com.hazelcast.ringbuffer.ReadResultSet;
import com.hazelcast.ringbuffer.Ringbuffer;

public class RingbufferDemo {
	private static void batchRead(Ringbuffer<String> ringbuffer ) throws InterruptedException, ExecutionException{
		//batch read
		long sequence = ringbuffer.headSequence();
		for(;;) {
		    ICompletableFuture<ReadResultSet<String>> f = ringbuffer.readManyAsync(sequence, 1, 10, null);
		    ReadResultSet<String> rs = f.get();
		    for (String s : rs) {
		        System.out.println(s);
		    }
		    sequence+=rs.readCount();
		}
		
//		f.andThen(new ExecutionCallback<ReadResultSet<String>>() {
//			   @Override
//			   public void onResponse(ReadResultSet<String> response) {
//			        for (String s : response) {
//			            System.out.println("Received:" + s);
//			        }
//			   }
//
//			   @Override
//			   public void onFailure(Throwable t) {
//			        t.printStackTrace();
//			   }
//			});
//		
	}
	private static void batchAdd(Ringbuffer<String> ringbuffer ) throws InterruptedException, ExecutionException{
		//batch add
		List<String> items = Arrays.asList("1","2","3");
		ICompletableFuture<Long> f = ringbuffer.addAllAsync(items, OverflowPolicy.OVERWRITE);
		long v=f.get();
		System.out.println(v);
	}
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		RingbufferConfig ringbufferConfig=new RingbufferConfig("rb");
		ringbufferConfig.setBackupCount(0);
		ringbufferConfig.setAsyncBackupCount(2);
		ringbufferConfig.setTimeToLiveSeconds(180);
		ringbufferConfig.setCapacity(50);
		ringbufferConfig.setInMemoryFormat(InMemoryFormat.BINARY);
		Config config=new Config();
		config.addRingBufferConfig(ringbufferConfig);
		
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
		Ringbuffer<String> ringbuffer = hazelcastInstance.getRingbuffer("rb");
		//batch add
		batchAdd(ringbuffer);
		for(int i=0;i<10;i++){
			 ringbuffer.add("someitem"+(i+1));
		}
		System.out.println("tail:"+ringbuffer.tailSequence());
		
		long s=0;
		while(true){
			long tail=ringbuffer.tailSequence();

			//add with overlow policy
		  long r = ringbuffer.addAsync("value"+(tail), OverflowPolicy.FAIL).get();
			
		  if(r==-1){
			  System.out.println("add failed:(s="+s+",r="+r+")");
			  break;
		  }else{
			  s=r+1;
		  }
		   if(s>=60){
			   break;
		   }
		}
		 System.out.println(s);
		System.out.println("==read one==");
		long sequence = ringbuffer.headSequence();
		while(true){
			 
		    String item = ringbuffer.readOne(sequence);
		    sequence++;
		   System.out.println(item);
		   if(sequence>15){
			   break;
		   }
		}
		System.out.println("==batch read==");
		batchRead(ringbuffer);
	}
}
