package hazelcast_examples.atomicReference;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicReference;
import com.hazelcast.core.IFunction;

public class IAtomicReferenceDemo {
	private static void appFun(HazelcastInstance hazelcastInstance){
		IAtomicReference<String> atomicReference=hazelcastInstance.getAtomicReference("AtomicReference-2");
		
		IFunction<String, String>fun=	new IFunction<String, String>() {

			@Override
			public String apply(String v) {
				
				return "hello,"+v+" !!";
			}
		};
		atomicReference.set("2");
		String result=atomicReference.apply(fun);
		System.out.println("get   value="+atomicReference.get());
		System.out.println("apply result="+result);
		
	}
	public static void main(String[] args) {
		Config config=new Config();
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
		IAtomicReference<String> atomicReference=hazelcastInstance.getAtomicReference("AtomicReference");
		atomicReference.set("hello");
		System.out.println(atomicReference.get());
		appFun(hazelcastInstance);
		hazelcastInstance.shutdown();
	}
}
