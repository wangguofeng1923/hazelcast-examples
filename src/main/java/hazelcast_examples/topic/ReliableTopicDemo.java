package hazelcast_examples.topic;

import com.hazelcast.config.Config;
import com.hazelcast.config.ReliableTopicConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.topic.TopicOverloadPolicy;

import hazelcast_examples.domain.TopicMessage;
import hazelcast_examples.domain.TopicMessageListener;

public class ReliableTopicDemo {
	public static void main(String[] args) {
		Config config = new Config();
	
		ReliableTopicConfig rtConfig =	config.getReliableTopicConfig("default");
		rtConfig.setTopicOverloadPolicy( TopicOverloadPolicy.BLOCK )
		        .setReadBatchSize( 10 )
		        .setStatisticsEnabled( true );
		
		TopicMessageListener topicMessageListener = new TopicMessageListener();
		
	    HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
	    ITopic<TopicMessage> topic = hazelcastInstance.getReliableTopic( "default" );
	    topic.addMessageListener( topicMessageListener );
	    topic.publish( new TopicMessage() );
	}
}
