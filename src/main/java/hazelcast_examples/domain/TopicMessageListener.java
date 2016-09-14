package hazelcast_examples.domain;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public class TopicMessageListener implements MessageListener<TopicMessage>{
	 private final Executor messageExecutor = Executors.newSingleThreadExecutor();


	@Override
	public void onMessage(Message<TopicMessage> message) {
		TopicMessage myEvent = message.getMessageObject();
		    System.out.println( "Message received = " + myEvent.toString() );
//		    if ( myEvent.isHeavyweight() ) {
//		      messageExecutor.execute( new Runnable() {
//		          public void run() {
//		            doHeavyweightStuff( myEvent );
//		          }
//		      } );
//		    }
		
	}

}
