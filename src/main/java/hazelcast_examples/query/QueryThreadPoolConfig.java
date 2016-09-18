package hazelcast_examples.query;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;

public class QueryThreadPoolConfig {
	private static class MyCallable implements Callable<String>,Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String call() throws Exception {
			return String.valueOf(Thread.currentThread().getId()+">"+System.currentTimeMillis());
		}}
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String str=""+/**~{*/""
		+ "<executor-service name=\"hz:query\">"
		+ "\r\n  <pool-size>10</pool-size>"
		+ "\r\n</executor-service>"
		+ "\r\n"/**}*/;
		Config config=new Config();
		config.getExecutorConfig("my-executor").setPoolSize(10);
		HazelcastInstance hazelcastInstance=Hazelcast.newHazelcastInstance(config);
		IExecutorService executorService=hazelcastInstance.getExecutorService("my-executor");
		for(int i=0;i<50;i++){
			Future<String>f=executorService.submit(new MyCallable());
			System.out.println(f.get());
		}
	
		//System.out.println(f.get());
	}
}
