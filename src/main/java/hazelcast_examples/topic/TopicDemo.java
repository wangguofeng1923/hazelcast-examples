package hazelcast_examples.topic;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

import hazelcast_examples.domain.TopicMessage;
import hazelcast_examples.domain.TopicMessageListener;

public class TopicDemo {
	private static void statics( ITopic<TopicMessage> topic ){

	long publishcount=	topic.getLocalTopicStats().getPublishOperationCount();
	long receivecount=	topic.getLocalTopicStats().getReceiveOperationCount();
	System.out.println(publishcount);
	System.out.println(receivecount);
	}
	 public static void main( String[] args ) {
		 	TopicMessageListener topicMessageListener = new TopicMessageListener();
		    HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
		    ITopic<TopicMessage> topic = hazelcastInstance.getTopic( "default" );
		    topic.addMessageListener( topicMessageListener );
		    statics(topic);
		    topic.publish( new TopicMessage() );
		    statics(topic);
		   
		  }
}
