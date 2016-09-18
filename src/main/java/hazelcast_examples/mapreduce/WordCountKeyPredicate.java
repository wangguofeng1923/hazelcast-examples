package hazelcast_examples.mapreduce;

import com.hazelcast.mapreduce.KeyPredicate;

public class WordCountKeyPredicate implements KeyPredicate<String> {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	  public boolean evaluate( String s ) {
	    return s != null && s.toLowerCase().contains( "hazelcast" );
	  }
	}