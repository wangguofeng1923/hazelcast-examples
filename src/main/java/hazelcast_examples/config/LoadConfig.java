package hazelcast_examples.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class LoadConfig {
	private static final Logger logger=LoggerFactory.getLogger(LoadConfig.class);
public static void main(String[] args) {
	logger.info("load config from file!");
	String packagePath=LoadConfig.class.getPackage().getName().replace(".", "\\");
	ClasspathXmlConfig config=new ClasspathXmlConfig(LoadConfig.class.getClassLoader(),packagePath+"/hazelcast-config.xml");
	HazelcastInstance instance=Hazelcast.newHazelcastInstance(config);

}
}
