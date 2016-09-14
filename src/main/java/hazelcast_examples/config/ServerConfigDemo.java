package hazelcast_examples.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.NearCacheConfig;

public class ServerConfigDemo {
	public static void main(String[] args) {
//    	Config cfg = new XmlConfigBuilder(xmlFileName).build();
//    	Config cfg = new XmlConfigBuilder(inputStream).build();
//    	Config cfg = new ClasspathXmlConfig(xmlFileName);
//    	Config cfg = new FileSystemXmlConfig(configFilename);
//    	Config cfg = new UrlXmlConfig(url);
//    	Config cfg = new InMemoryXmlConfig(xml);
    	
        Config config = new Config();
        config.setInstanceName("DistributedMap");
        config.getNetworkConfig().setPort(5900 );
        config.getNetworkConfig().setPortAutoIncrement(true);
//        NetworkConfig network = config.getNetworkConfig();
//        JoinConfig join = network.getJoin();
//        join.getMulticastConfig().setEnabled( false );
//        join.getTcpIpConfig().addMember( "10.45.67.32").addMember( "10.45.67.100" ) .setRequiredMember("192.168.10.100" ).setEnabled( true );
//        network.getInterfaces().setEnabled( true ).addInterface("10.45.67.*" );
        
        
        MapConfig mapConfig = new MapConfig();
        mapConfig.setName( "testMap" );
        mapConfig.setBackupCount( 2 );
        mapConfig.getMaxSizeConfig().setSize( 10000 );
        mapConfig.setTimeToLiveSeconds( 300 );
        
        MapStoreConfig mapStoreConfig = new MapStoreConfig();
        mapStoreConfig.setClassName("com.hazelcast.examples.DummyStore" )
            .setEnabled(true );
        mapConfig.setMapStoreConfig( mapStoreConfig );
         

        NearCacheConfig nearCacheConfig = new NearCacheConfig();
        nearCacheConfig.setMaxSize( 1000 ).setMaxIdleSeconds( 120)
           .setTimeToLiveSeconds( 300 );
        mapConfig.setNearCacheConfig( nearCacheConfig );

        
        
        config.addMapConfig( mapConfig );
	}
}
