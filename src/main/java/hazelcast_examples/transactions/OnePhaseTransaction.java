package hazelcast_examples.transactions;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.TransactionalMap;
import com.hazelcast.core.TransactionalQueue;
import com.hazelcast.core.TransactionalSet;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionOptions;
import com.hazelcast.transaction.TransactionOptions.TransactionType;

public class OnePhaseTransaction {
	public static void main(String[] args) {
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
		TransactionOptions options = new TransactionOptions()
			    .setTransactionType( TransactionType.ONE_PHASE );
		TransactionContext transactionContext=hazelcastInstance.newTransactionContext(options);
		transactionContext.beginTransaction();
		
		TransactionalQueue<Object> queue = transactionContext.getQueue( "myqueue" );
		TransactionalMap<Object,Object> map = transactionContext.getMap( "mymap" );
		TransactionalSet<Object> set = transactionContext.getSet( "myset" );
		try{
			
			Object obj = queue.poll();
			  //process obj
			  map.put( "1", "value1" );
			  set.add( "value" );
			
			transactionContext.commitTransaction();
		}catch(Exception e){
			transactionContext.rollbackTransaction();
		}
	}
}
