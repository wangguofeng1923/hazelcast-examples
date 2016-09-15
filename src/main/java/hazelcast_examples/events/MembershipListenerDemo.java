package hazelcast_examples.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.MemberAttributeEvent;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;

public class MembershipListenerDemo implements MembershipListener {
	private static final Logger logger=LoggerFactory.getLogger(MembershipListenerDemo.class);
	@Override
	public void memberAdded(MembershipEvent paramMembershipEvent) {
		
		logger.info("memberAdded:"+paramMembershipEvent);
	}

	@Override
	public void memberAttributeChanged(MemberAttributeEvent paramMemberAttributeEvent) {
		
		logger.info("memberAttributeChanged:"+paramMemberAttributeEvent);
	}

	@Override
	public void memberRemoved(MembershipEvent paramMembershipEvent) {
		logger.info("memberRemoved:"+paramMembershipEvent);
		
	}

}
