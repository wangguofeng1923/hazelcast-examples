package hazelcast_examples.jcache;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import javax.cache.configuration.CompleteConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.MutableConfiguration;

import com.hazelcast.config.CacheConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class JCacheDemo {
public static void main(String[] args) {
	HazelcastInstance hazelcastInstance=Hazelcast.newHazelcastInstance();
	CachingProvider cachingProvider = Caching.getCachingProvider();
	CacheManager cacheManager=cachingProvider.getCacheManager();
//	Configuration c=new CacheConfig();
	CompleteConfiguration<String, String> c =
		    new MutableConfiguration<String, String>()
		        .setTypes( String.class, String.class );
	
	Cache<String, String> cache = cacheManager.createCache( "example", c );
	cache.put( "world", "Hello World" );

	// Retrieve the value again from the cache
	String value = cache.get( "world" );
	

	// Print the value 'Hello World'
	System.out.println( value );
}
}
