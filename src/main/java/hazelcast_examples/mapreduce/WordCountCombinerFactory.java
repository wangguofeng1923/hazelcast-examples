package hazelcast_examples.mapreduce;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class WordCountCombinerFactory
implements CombinerFactory<String, Long, Long> {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Combiner<Long, Long> newCombiner( String key ) {
		return new WordCountCombiner();
	}
	
	private class WordCountCombiner extends Combiner<Long, Long> {
		private long sum = 0;
		
		@Override
		public void combine( Long value ) {
		  sum++;
		}
		
		@Override
		public Long finalizeChunk() {
		  return sum;
		}
		
		@Override
		public void reset() {
		  sum = 0;
		}
	}
}