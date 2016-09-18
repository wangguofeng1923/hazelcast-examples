package hazelcast_examples.transactions;

import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.TransactionalMap;
import com.hazelcast.transaction.HazelcastXAResource;
import com.hazelcast.transaction.TransactionContext;

public class XATransaction {
	public static void main(String[] args) throws SystemException, NotSupportedException, IllegalStateException, RollbackException {
		UserTransactionManager tm = new UserTransactionManager();
		tm.setTransactionTimeout(60);
		tm.begin();

		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
		HazelcastXAResource xaResource = hazelcastInstance.getXAResource();

		Transaction transaction = tm.getTransaction();
		transaction.enlistResource(xaResource);
		// other resources (database, app server etc...) can be enlisted

		try {
		  TransactionContext context = xaResource.getTransactionContext();
		  TransactionalMap map = context.getMap("m");
		  map.put("key", "value");
		  // other resource operations

		  transaction.delistResource(xaResource, XAResource.TMSUCCESS);
		  tm.commit();
		} catch (Exception e) {
		  tm.rollback();
		}
	}
}
