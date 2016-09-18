package hazelcast_examples.mapreduce;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import com.hazelcast.core.ExecutionCallback;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.JobPartitionState;
import com.hazelcast.mapreduce.JobProcessInformation;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import com.hazelcast.mapreduce.Mapper;
import com.hazelcast.mapreduce.TrackableJob;

public class MapReduce {
	public static class TokenizerMapper implements Mapper<String, String, String, Long> {
		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static final Long ONE = Long.valueOf( 1L );

		  @Override
		  public void map(String key, String document, Context<String, Long> context) {
		    StringTokenizer tokenizer = new StringTokenizer( document.toLowerCase() );
		    while ( tokenizer.hasMoreTokens() ) {
		      context.emit( tokenizer.nextToken(), ONE );
		    }
		  }
		}
	private static ExecutionCallback<Map<String, Long>> buildCallback(){
		return new ExecutionCallback<Map<String, Long>>(){

			@Override
			public void onResponse(Map<String, Long> resp) {
				
			}

			@Override
			public void onFailure(Throwable t) {
				// TODO Auto-generated method stub
				
			}};
	}
public static void main(String[] args) throws InterruptedException, ExecutionException {
	String confStr=""+/**~{*/""
	+ ""
	+ "\r\n<jobtracker name=\"default\">"
	+ "\r\n  <max-thread-size>0</max-thread-size>"
	+ "\r\n  <!-- Queue size 0 means number of partitions * 2 -->"
	+ "\r\n  <queue-size>0</queue-size>"
	+ "\r\n  <retry-count>0</retry-count>"
	+ "\r\n  <chunk-size>1000</chunk-size>"
	+ "\r\n  <communicate-stats>true</communicate-stats>"
	+ "\r\n  <topology-changed-strategy>CANCEL_RUNNING_OPERATION</topology-changed-strategy>"
	+ "\r\n</jobtracker>"
	+ "\r\n"
	+ "\r\n"
	+ "\r\n"/**}*/;
	HazelcastInstance hazelcastInstance=Hazelcast.newHazelcastInstance();
	IMap<String, String> map = hazelcastInstance.getMap( "articles" );
	JobTracker jobTracker = hazelcastInstance.getJobTracker( "default" );
	KeyValueSource<String, String> source = KeyValueSource.fromMap( map );
	Job<String, String> job = jobTracker.newJob( source );
	
	ICompletableFuture<Map<String, Long>> future = job
			.keyPredicate(new WordCountKeyPredicate())
		    .mapper( new TokenizerMapper() )
		    .combiner( new WordCountCombinerFactory() )
		    .reducer( new WordCountReducerFactory() )
		    .submit();

		// Attach a callback listener
		future.andThen( buildCallback() );

		// Wait and retrieve the result
		Map<String, Long> result = future.get();
		
		
		//trac job status
		JobCompletableFuture<Map<String, Long>> f = job
			    .mapper( new TokenizerMapper() )
			    .combiner( new WordCountCombinerFactory() )
			    .reducer( new WordCountReducerFactory() )
			    .submit();
		
		String jobId = f.getJobId();
		TrackableJob trackableJob = jobTracker.getTrackableJob(jobId);

		JobProcessInformation stats = trackableJob.getJobProcessInformation();
		int processedRecords = stats.getProcessedRecords();
		System.out.println( "ProcessedRecords: " + processedRecords );

		JobPartitionState[] partitionStates = stats.getPartitionStates();
		for ( JobPartitionState partitionState : partitionStates ) {
			System.out.println( "PartitionOwner: " + partitionState.getOwner()
		          + ", Processing state: " + partitionState.getState().name() );
		}
}
}
