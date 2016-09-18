package hazelcast_examples.mapreduce;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class WordCountReducerFactory implements ReducerFactory<String, Long, Long> {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	  public Reducer<Long, Long> newReducer( String key ) {
	    return new WordCountReducer();
	  }

	  private class WordCountReducer extends Reducer<Long, Long> {
	    private volatile long sum = 0;

	    @Override
	    public void reduce( Long value ) {
	      sum += value.longValue();
	    }

	    @Override
	    public Long finalizeReduce() {
	      return sum;
	    }
	  }
	}