package hazelcast_examples.config;

import java.util.ArrayList;
import java.util.List;

import com.hazelcast.config.CacheDeserializedValues;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.HotRestartConfig;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.config.PartitioningStrategyConfig;
import com.hazelcast.config.WanReplicationRef;
import com.hazelcast.map.eviction.MapEvictionPolicy;

public class MapConfigDemo {
	public static void main(String[] args) {
		MapConfig config=new MapConfig("map-demo");
		config.setAsyncBackupCount(2);
		config.setBackupCount(2);
		config.setCacheDeserializedValues(CacheDeserializedValues.ALWAYS);
		config.setEntryListenerConfigs(new ArrayList<EntryListenerConfig>());
		config.setEvictionPolicy(EvictionPolicy.LFU);
		config.setHotRestartConfig(new HotRestartConfig());
		config.setInMemoryFormat(InMemoryFormat.BINARY);
		config.setMapAttributeConfigs(new ArrayList<MapAttributeConfig>() );
	//	config.setMapEvictionPolicy(MapEvictionPolicy<K, V>)
	//	config.setMapIndexConfigs(mapIndexConfigs)
		config.setMapStoreConfig(new MapStoreConfig());
		config.setMaxIdleSeconds(30);
		config.setMaxSizeConfig(new MaxSizeConfig());
//		config.setMergePolicy(mergePolicy)
		config.setName("map-demo");
		config.setNearCacheConfig(new NearCacheConfig());
		config.setPartitioningStrategyConfig(new PartitioningStrategyConfig());
//		config.setPartitionLostListenerConfigs(listenerConfigs)
//		config.setQueryCacheConfigs(queryCacheConfigs);
		config.setQuorumName("quorumName");
		config.setReadBackupData(true);
		config.setStatisticsEnabled(true);
		config.setTimeToLiveSeconds(30);
		config.setWanReplicationRef(new WanReplicationRef());
	}
}
