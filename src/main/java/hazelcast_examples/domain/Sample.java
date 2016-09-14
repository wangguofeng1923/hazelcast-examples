package hazelcast_examples.domain;

import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;

public class Sample implements ItemListener<Price>{


		@Override
		public void itemAdded(ItemEvent<Price> item) {
			  System.out.println( "Item added = " + item );
			
		}

		@Override
		public void itemRemoved(ItemEvent<Price> item) {
			 System.out.println( "Item removed = " + item );
			
		}     

}
